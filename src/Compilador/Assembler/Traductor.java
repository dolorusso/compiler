package Compilador.Assembler;

import Compilador.ErrorHandler.ErrorManager;
import Compilador.Generador.Terceto;
import Compilador.Lexer.Atributo;
import Compilador.Lexer.TablaSimbolos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
    private boolean checkOverflow = false;
    private int indiceErrorOverflow;
    private int indiceErrorPerdidaInformacion;
    private Stack<Integer> auxUnidades = new Stack<>();
    private int contadorUnidaes = 0;
    private boolean checkTrunc;

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

    // funcion para generar un archivo a partir de codigoGenerado.
    public void generarArchivo(){
        //  codigo para
        try {
            FileWriter fw = new FileWriter("src/Compilador/Assembler/output.wat");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(codigoGenerado.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //guardo como se hacen las operaciones en i32  f32
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
        generarArchivo();
    }

    // Funcion para traducir un terceto. Trata los operandos de ser necesario y genera la instruccion WASM
    // que se necesite segun el operador y, en algunos casos, el tipo de la operacion.
    public void traducir(Terceto t) {
        OpWasm op = tablaOps.get(t.operador);

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
                generarCheckTrunc(t);
                return;

            case "print":
                procesarPrint(t.operando1);
                return;

            case "*":
                generarCheckOverflow(t);
                recuperarAuxiliar(t.tipo, true, true);
                // sin return, queremos que ejecute la multiplicacion ademas de esto.
                if (t.tipo == Atributo.longType)
                    agregarCodigo(op.instrI32);

                else if (t.tipo == Atributo.floatType)
                    agregarCodigo(op.instrF32);
                return ;

            case "BF":
                if (Integer.parseInt(t.operando1) > Integer.parseInt(t.operando2)){
                    agregarCodigo("i32.eqz");
                    agregarCodigo("br_if $do_" + auxUnidades.peek());
                    auxUnidades.pop();
                    tabs--;
                    agregarCodigo(")");
                } else {
                    agregarCodigo("i32.eqz");   //invierte 1->0 o 0->1
                    agregarCodigo("br_if $else_" + auxUnidades.peek());
                }
                return;
            // el resultado de la condicion esta en la pila

            case "BI":
                agregarCodigo("br $endif_" + auxUnidades.peek());
                auxUnidades.pop();
                agregarCodigo(")");
                return;
            case "endif":
                tabs--;
                agregarCodigo(")");
                if (t.operando1.equals("if")){
                    tabs--;
                    agregarCodigo("br $endif_" + auxUnidades.peek());
                    agregarCodigo(")");
                    auxUnidades.pop();

                }
                return;
            case "if_inicio":
                agregarCodigo("(block $endif_" + contadorUnidaes);
                tabs++;
                agregarCodigo("(block $else_" + contadorUnidaes);
                tabs++;
                auxUnidades.push(contadorUnidaes);
                contadorUnidaes++;

                return;
            case "do_inicio":
                agregarCodigo("(loop $do_" + contadorUnidaes);
                tabs++;
                auxUnidades.push(contadorUnidaes);
                contadorUnidaes++;
                return;
        }

        // OPERADORES DE LA TABLA

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

    private void generarCheckPerdidaInformacion(Terceto t) {
        // No checkeamos el tipo. Trunc es siempre resultado entero.
        recuperarAuxiliar(t.tipo, true, false);

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
                agregarCodigo("global.get $" + operando1);
                agregarCodigo("call $print_num");
            } else if (atr.type == Atributo.floatType){
                agregarCodigo("global.get $" + operando1);
                agregarCodigo("call $print_num");
            } else {
                errManager.error("Error al procesar el tipo de dato: " + atr.type, -1);
            }
        }

    }


    // Funcion para tratar los operandos dependiendo del tipo y uso en la tabla de simbolos y ponerlos en el tope de la pila
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
    // Detecta si hay operaciones de multiplicacion.
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
                if (terceto.operador.equals("*"))
                    this.checkOverflow = true;
                if (terceto.operador.equals("trunc"))
                    this.checkTrunc = true;
                funciones.get(pilaNombreFuncion.peek()).add(terceto);
            }
        }
    }

    // Funcion para generar todas las variables que se utilizaran durante el programa.
    public void generarGlobales(){
        // Para realizar calculos de espacio en strings y generar sus instrucciones.
        ArrayList <Atributo> strings = new ArrayList<>();

        int espacioStrings = 0;

        // Creamos variables globales para todas las entradas de la tabla de simbolos que las necesiten.
        // Esto sera necesario para Variables y Parametros.
        // Tambien se reserva espacio para los strings y se almacenan.
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

        // Creamos variables auxiliares. Como maximo utilizaremos ...
        agregarCodigo("(global $_aux1i (mut i32) (i32.const 0))");
        agregarCodigo("(global $_aux2i (mut i32) (i32.const 0))");
        agregarCodigo("(global $_auxiRes (mut i64) (i64.const 0))");
        agregarCodigo("(global $_aux1f (mut f32) (f32.const 0))");
        agregarCodigo("(global $_aux2f (mut f32) (f32.const 0))");

        int indiceStr = 0;

        // Calculamos la cantidad de paginas que hay que pedir en memoria para almacenar todos los strings.
        // Se calcula como el techo de la cantidad de bytes en strings sobre el tamanio de pagina.
        // ceil(a / b) == (a + b - 1) / b
        int pageSize = 65536;
        int pages = (espacioStrings + pageSize - 1) / pageSize;
        if (espacioStrings > 0){
            agregarCodigo("(memory (export \"mem\") " + pages + ")");
        } else {
            agregarCodigo("(memory (export \"mem\") 1)");
        }

        for (Atributo str : strings){
            // Agregamos el codigo junto con un null char para saber el final.
            agregarCodigo("(data (i32.const " + indiceStr + ") \"" + str.strValue + "\\00\")");
            // Guardamos el indice para poder referenciarlo luego.
            str.numValue = indiceStr;
            // Sumamos la longitud para manejar el arreglo de memoria. +1 por el null char.
            indiceStr += str.strValue.length() + 1;
        }

        // Agregamos al final, si es necesario, un mensaje de error para Overflow
        if (checkOverflow){
            String errorOverflow = "[Runtime Error] Overflow al multiplicar.";
            agregarCodigo("(data (i32.const " + indiceStr + ") \"" + errorOverflow + "\\00\")");
            this.indiceErrorOverflow = indiceStr;
            indiceStr += errorOverflow.length() + 1;
        }

        // Agregamos al final, si es necesario, un mensaje de error para perdida de informacion en conversion
        if (checkTrunc){
            String errorTrunc = "[Runtime Error] Truncamiento genera perdida de informacion.";
            agregarCodigo("(data (i32.const " + indiceStr + ") \"" + errorTrunc + "\\00\")");
            this.indiceErrorPerdidaInformacion = indiceStr;
            indiceStr += errorTrunc.length() + 1;
        }
    }

    private class BlockInfo implements Comparable<BlockInfo> {
        public int inicioBloque;
        public int finBloque;
        public boolean esLoop;

        public BlockInfo(int inicio, int fin, boolean loop) {
            inicioBloque = inicio;
            finBloque = fin;
            esLoop = loop;
        }

        @Override
        public int compareTo(BlockInfo other) {
            return Integer.compare(this.inicioBloque, other.inicioBloque);
        }

        @Override
        public String toString() {
            return("Bloque: " + inicioBloque + " - " + finBloque + " - " + esLoop);
        }
    }

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
            tabs += 1; // dentro de la funcion

            for (Terceto terceto : tercetos){
                traducir(terceto);
            }

            // cerrar la funcion
            tabs -= 1;
            agregarCodigo(")");
        }

        if (checkOverflow){
            generarFuncionOverflow();
        }

        if (checkTrunc){
            generarFuncionPerdidaInformacion();
        }
    }



    // Funcion para generar el inicio de WASM, este debe estar siempre presente.
    private void generarInicio(){
        agregarCodigo("(module");
        tabs += 1;
        agregarCodigo("(import \"console\" \"print_str\" (func $print_str (param i32)))");
        agregarCodigo("(import \"console\" \"print_num\" (func $print_num (param i32)))");
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

    private void guardarAux(int tipo, String nombreAux){
        if (tipo == Atributo.longType)
            agregarCodigo("global.set $_aux" + nombreAux +"i" );
        if (tipo == Atributo.floatType)
            agregarCodigo("global.set $_aux" + nombreAux +"f" );
    }

    // Funcion para guardar 2 operandos en las variables globales para utilizarlas en otras operaciones.
    private void guardarOperandos(Terceto terceto){
        if (! esNumero(terceto.operando2)){
            tratarOperandoTS(terceto.operando2, ts.obtener(terceto.operando2));
        }
        guardarAux(terceto.tipo, "2");

        if (! esNumero(terceto.operando1)){
            tratarOperandoTS(terceto.operando1, ts.obtener(terceto.operando1));
        }
        guardarAux(terceto.tipo, "1");


    }

    private void recuperarAuxiliar(int tipo, boolean aux1, boolean aux2){
        if (tipo == Atributo.longType) {
            if (aux1)
                agregarCodigo("global.get $" + "_aux1i");
            if (aux2)
                agregarCodigo("global.get $" + "_aux2i");
        } else if (tipo == Atributo.floatType) {
            if (aux1)
                agregarCodigo("global.get $" + "_aux1f");
            if (aux2)
                agregarCodigo("global.get $" + "_aux2f");
        }
    }
    private void generarFuncionOverflow(){
        // Función que trapea si $_auxiRes no entra en el rango de i32
        agregarCodigo("(func $integer-overflow-checker");
        tabs++;

        // Bloque principal
        agregarCodigo("(block $fin");        // destino normal (sin overflow)
        tabs++;

        // Bloque exclusivo para overflow
        agregarCodigo("(block $overflow");   // si salto aca → trap
        tabs++;

        // ----- check: auxiRes > MAX_INT -----
        agregarCodigo("global.get $_auxiRes");
        agregarCodigo("i64.const " + Integer.MAX_VALUE);
        agregarCodigo("i64.gt_s");         // 1 si auxiRes > max
        agregarCodigo("br_if $overflow");  // salta si hay overflow

        // ----- check: auxiRes < MIN_INT -----
        agregarCodigo("global.get $_auxiRes");
        agregarCodigo("i64.const " + Integer.toString(Integer.MIN_VALUE));
        agregarCodigo("i64.lt_s");         // 1 si auxiRes < min
        agregarCodigo("br_if $overflow");  // salta si hay overflow

        // Si llego aca → no hubo overflow:
        agregarCodigo("br $fin");

        // ----- bloque overflow -----
        tabs--;
        agregarCodigo(")"); // end block $overflow
        tabs++;
        agregarCodigo("i32.const " + indiceErrorOverflow);
        agregarCodigo("call print_str");
        agregarCodigo("unreachable"); // trap definitivo

        // ----- fin -----
        tabs--;
        agregarCodigo(")"); // end block $fin

        tabs--;
        agregarCodigo(")");
    }

    private void generarCheckOverflow(Terceto terceto){
        guardarOperandos(terceto);
        // Pasamos los 2 operandos a i64.
        recuperarAuxiliar(terceto.tipo, true, false);
        agregarCodigo("i64.extend_i32_s");

        recuperarAuxiliar(terceto.tipo, false, true);
        agregarCodigo("i64.extend_i32_s");

        // Realizamos la operacion en i64, esto asegura que no habra overflow. La guardamos para utilizarla.
        agregarCodigo("i64.mul");
        agregarCodigo("global.set $" + "_auxiRes");

        agregarCodigo("call $integer-overflow-checker");
    }

    // Funcion que genera funcion para los checkeos para saber si hubo perdida de informacion en trunc.
    // se supone previamente que se tiene $_auxi1 con el truncado y $_auxf1 con el original.
    private void generarFuncionPerdidaInformacion(){
        agregarCodigo("(func $trunc-checker");
        tabs++;
        agregarCodigo("(block $endif_truncar");
        tabs++;
        agregarCodigo("(block $else_truncar");
        tabs++;
        // Se pone en la pila el entero truncado
        recuperarAuxiliar(Atributo.longType, true,false);
        // Se convierte en entero a float para comparar con el resultado original.
        agregarCodigo("f32.convert_i32_s");
        recuperarAuxiliar(Atributo.floatType, true,false);
        agregarCodigo("f32.eq");
        agregarCodigo("br_if $else_truncar");
        agregarCodigo("i32.const " + indiceErrorPerdidaInformacion);
        agregarCodigo("call $print_str");
        agregarCodigo("unreachable");
        tabs--;
        agregarCodigo(")"); // cierre else_truncar
        tabs--;
        agregarCodigo("br $endif_truncar");
        agregarCodigo(")"); // cierre endif_truncar
        tabs--;
        agregarCodigo(")"); // cierre funcion

    }

    private void generarCheckTrunc(Terceto terceto){
        procesarOperando(terceto.operando1);
        // Siempre se procesara un float dentro del trunc, se almacena.
        guardarAux(Atributo.floatType,"1");

        // Se recupera ese float y se realiza la conversion.
        recuperarAuxiliar(Atributo.floatType, true, false);
        agregarCodigo("i32.trunc_f32_s");
        // t.tipo siempre es int para trunc.
        guardarAux(terceto.tipo,"1");
        // Luego de esta operacion, se tiene almacenado en los auxiliares
        // Aux1f: un float con lo que se debe truncar
        // Aux1i: un int con el numero truncado.

        agregarCodigo("call $" + "trunc-checker");
        recuperarAuxiliar(terceto.tipo, true, false);
    }

}
