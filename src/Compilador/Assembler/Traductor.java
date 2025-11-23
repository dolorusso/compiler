package Compilador.Assembler;

import Compilador.ErrorHandler.ErrorManager;
import Compilador.Generador.Terceto;
import Compilador.Lexer.Atributo;
import Compilador.Lexer.TablaSimbolos;

import java.util.*;

public class Traductor {
    private final StringBuilder codigoGenerado = new StringBuilder();
    private Map<String, List<Terceto>> funciones;
    private final TablaSimbolos ts;
    private final Map<Integer,String> typeMap;
    private int tabs;
    private String mainName;
    private final ErrorManager errManager;
    private final Map<String, OpWasm> tablaOps = new HashMap<>();

    static class OpWasm {
        boolean usaAmbosOperandos;
        String instrI32;
        String instrF32;

        OpWasm(boolean usa, String i32, String f32) {
            this.usaAmbosOperandos = usa;
            this.instrI32 = i32;
            this.instrF32 = f32;
        }
    }

    public Traductor(TablaSimbolos ts, ErrorManager errManager){
        this.ts = ts;
        tabs = 0;
        this.errManager = errManager;

        typeMap = new HashMap<>();
        typeMap.put(0,"i32");
        typeMap.put(1,"f32");

        initTablaOps();
    }



    private void initTablaOps() {
        // Operaciones
        tablaOps.put("+", new OpWasm(true, "i32.add", "f32.add"));
        tablaOps.put("-", new OpWasm(true, "i32.sub", "f32.sub"));
        tablaOps.put("*", new OpWasm(true, "i32.mul", "f32.mul"));
        tablaOps.put("/", new OpWasm(true, "i32.div_s", "f32.div"));

        // Comparaciones
        tablaOps.put(">", new OpWasm(true, "i32.gt_s", "f32.gt"));
        tablaOps.put("<", new OpWasm(true, "i32.lt_s", "f32.lt"));
        tablaOps.put(">=", new OpWasm(true, "i32.ge_s", "f32.ge"));
        tablaOps.put("<=", new OpWasm(true, "i32.le_s", "f32.le"));
        tablaOps.put("==", new OpWasm(true, "i32.eq", "f32.eq"));
        tablaOps.put("!=", new OpWasm(true, "i32.ne", "f32.ne"));
    }


    // Funcion con el flujo principal del traductor.
    // Se le dan todos los tercetos y genera un archivo con el codigo de salida.
    public void traducir(List<Terceto> tercetos){
        separarFunciones(tercetos);
        generarInicio();
        generarGlobales();
        generarFunciones();
        generarFin();
        System.out.println(codigoGenerado.toString());
    }

    // Funcion para traducir un terceto. Trata los operandos de ser necesario y genera la instruccion WASM
    // que se necesite segun el operador y, en algunos casos, el tipo de la operacion.
    public void traducir(Terceto t) {

        // Operadores especiales (que no entran en la tabla)
        switch (t.operador) {
            case ":=":
                procesarOperando(t.operando2);
                agregarCodigo("global.set $" + t.operando1);
                return;

            case "call":
                agregarCodigo("call $" + t.operando1);
                return;

            case "return":
                procesarOperando(t.operando1);
                agregarCodigo("return");
                return;

            case "inicio_main":
                mainName = t.operando1;
                return;

            case "drop":
                agregarCodigo("drop");
                return;

            case "trunc":
                procesarOperando(t.operando1);
                agregarCodigo("f32.convert_i32_s");
                return;

            case "print":
                procesarPrint(t.operando1);
                return;
        }

        // OPERADORES DE LA TABLA
        OpWasm op = tablaOps.get(t.operador);
        if (op != null) {

            if (op.usaAmbosOperandos)
                procesarOperandos(t);
            else
                procesarOperando(t.operando1);

            if (t.tipo == Atributo.longType)
                agregarCodigo(op.instrI32);

            else if (t.tipo == Atributo.floatType)
                agregarCodigo(op.instrF32);

            else
                errManager.error("Error al procesar el tipo de dato: " + t.tipo, -1);

            return;
        }

        //errManager.error("OPERADOR DE TERCETO NO RECONOCIDO " + t, -1);
    }

    private void procesarPrint(String operando1) {
        if (esNumero(operando1)){
            agregarCodigo("call $print_num");
        } else {
            Atributo atr = ts.obtener(operando1);
            if (atr.type == Atributo.stringType){
                agregarCodigo("i32.const " + (int)atr.numValue);
                agregarCodigo("call $print_str");
            } else if (atr.type == Atributo.longType){
                agregarCodigo("call $print_num");
            // todo no se bien si se usa lo mismo o si sepueden imprimir
            } else if (atr.type == Atributo.floatType){
                agregarCodigo("call $print_num");
            } else {
                errManager.error("Error al procesar el tipo de dato: " + atr.type, -1);
            }
        }

    }


    // Funcion para tratar los operandos dependiendo del tipo y uso en la tabla de simbolos.
    public void tratarOperandoTS(String lexema, Atributo atr){
        if (atr.uso == Atributo.USO_PARAMETRO | atr.uso == Atributo.USO_VARIABLE){
            agregarCodigo("global.get $" + lexema);
        } else if (atr.uso == Atributo.USO_CONSTANTE || atr.uso == Atributo.USO_AUXILIAR){
            if (atr.type == Atributo.longType){
                agregarCodigo("i32.const " + (int)(atr.numValue));
            } else if (atr.type == Atributo.floatType){
                agregarCodigo("f32.const " + atr.numValue);
            } else {
                errManager.error("Error al procesar el tipo de dato: " + atr.type, -1);
            }
        }
    }

    // Auxiliar para ver si el terceto tiene una entrada a la TS o un indice a otro terceto.
    private boolean esNumero(String s) {
        // Cualquier digito. Permite enteros, positivos o negativos.
        return s.matches("-?\\d+");
    }

    // Funcion para verificar si se esta frente a un terceto o una entrada de la TS.
    // En caso de ser una entrada de la TS, se realiza el tratamiento necesario.
    private void procesarOperando(String operando) {

        if (esNumero(operando)) {
            // Generado en pila, no hay que hacer nada.
            return;
        }

        // Si no es un numero, esta en la TS, por lo que hay que tratarlo.
        Atributo t = ts.obtener(operando);
        tratarOperandoTS(operando, t);
    }

    // Funcion para acortar llamados en operaciones de 2 operandos.
    public void procesarOperandos(Terceto operacion){
        procesarOperando(operacion.operando1);
        procesarOperando(operacion.operando2);
    }

    // Separa las funciones de todos los tercetos.
    public void separarFunciones(List<Terceto> tercetos) {
        funciones = new HashMap<>();

        Stack<String> pilaNombreFuncion = new Stack<>(); // Apilamos
        for (Terceto terceto : tercetos){
            if (terceto.operador.startsWith("inicio_")){
                pilaNombreFuncion.push(terceto.operando1);
                funciones.put(terceto.operando1, new ArrayList<>());
                funciones.get(terceto.operando1).add(terceto);
            } else if (terceto.operador.startsWith("fin_")){
                funciones.get(pilaNombreFuncion.peek()).add(terceto);
                pilaNombreFuncion.pop();
            } else {
                funciones.get(pilaNombreFuncion.peek()).add(terceto);
            }
        }
    }

    // Funcion para generar todas las variables que se utilizaran durante el programa.
    public void generarGlobales(){
        // Para realizar calculos de espacio en strings y generar sus instrucciones.
        ArrayList <Atributo> strings = new ArrayList<>();
        int espacioStrings = 0;

        for (Map.Entry<String, Atributo> entrada : ts.getTabla().entrySet()){
            Atributo atr = entrada.getValue();
            String clave = entrada.getKey();
            if ((atr.uso == Atributo.USO_VARIABLE || atr.uso == Atributo.USO_PARAMETRO)){
                if (atr.type == Atributo.longType){
                    String tipoAssembly = convertirTipo(atr.type);
                    agregarCodigo("(global $" + clave + " (mut " + tipoAssembly + ") ("+tipoAssembly+".const " + (int)(atr.numValue) + "))");
                }
            } else if (atr.uso == Atributo.USO_CONSTANTE && atr.type == Atributo.stringType) {
                strings.add(atr);
                espacioStrings += atr.strValue.length() + 1;
            }
        }

        int indiceStr = 0;

        // Calculamos la cantidad de paginas que hay que pedir en memoria para almacenar todos los strings.
        // Se calcula como el techo de la cantidad de bytes en strings sobre el tamanio de pagina.
        // ceil(a / b) == (a + b - 1) / b
        int pageSize = 65536;
        int pages = (espacioStrings + pageSize - 1) / pageSize;
        if (espacioStrings > 0){
            agregarCodigo("(memory (export \"mem\") " + pages + ")");
        }

        for (Atributo str : strings){
            // Agregamos el codigo junto con un null char para saber el final.
            agregarCodigo("(data (i32.const " + indiceStr + ") \"" + str.strValue + "\\00\")");
            // Guardamos el indice para poder referenciarlo luego.
            str.numValue = indiceStr;
            // Sumamos la longitud para manejar el arreglo de memoria. +1 por el null char.
            indiceStr += str.strValue.length() + 1;
        }
    }

    // Funcion para generar el codigo de las funciones de manera correcta, con todas las instrucciones
    // en sus respectivos bloques.
    public void generarFunciones(){
        for (Map.Entry<String, List<Terceto>> entrada : funciones.entrySet()){
            String nombreFuncion = entrada.getKey();
            List<Terceto> tercetos = entrada.getValue();
            String tipoFuncion = convertirTipo(tercetos.get(0).tipo);
            if (tipoFuncion != null){
                agregarCodigo("(func $" + nombreFuncion + " (result " + tipoFuncion + ")");
            } else {
                agregarCodigo("(func $" + nombreFuncion);
            }
            tabs += 1;
            for (Terceto terceto : tercetos){
                traducir(terceto);
            }
            tabs -= 1;
            agregarCodigo(")");
        }
    }

    // Funcion para generar el inicio de WASM, este debe estar siempre presente.
    private void generarInicio(){
        agregarCodigo("(module");
        tabs += 1;
        agregarCodigo("(import \"console\" \"print_str\" (func $print_str (param i32)))\n");
        agregarCodigo("(import \"console\" \"$print_num\" (func $print_num (param i32)))\n");
    }

    // Funcion para cerrar exportar la funcion principal y cerrar el modulo.
    private void generarFin(){
        agregarCodigo("(export \"main\" (func $"+ mainName +"))");
        tabs -= 1;
        agregarCodigo(")");
    }

    // Funcion para agregar lineas al codigo.
    private void agregarCodigo(String linea){
        codigoGenerado.append("\t".repeat(tabs)).append(linea).append("\n");
    }

    // Funcion para convertir el tipo de Atributo a uno de WASM.
    private String convertirTipo(int tipo){
        return typeMap.get(tipo);
    }

    private boolean generaOverflow(String tipo, String operando){
        return false;
    }
}
