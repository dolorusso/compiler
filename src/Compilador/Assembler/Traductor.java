package Compilador.Assembler;

import Compilador.ErrorHandler.ErrorManager;
import Compilador.Generador.Terceto;
import Compilador.Lexer.Atributo;
import Compilador.Lexer.TablaSimbolos;

import java.util.*;

public class Traductor {
    private StringBuilder codigoGenerado = new StringBuilder();
    private Map<String, List<Terceto>> funciones;
    private TablaSimbolos ts;
    private Map<Integer,String> typeMap;
    private int tabs;
    private String mainName;
    private ErrorManager errManager;
    public Traductor(TablaSimbolos ts, ErrorManager errManager){
        this.ts = ts;
        tabs = 0;
        this.errManager = errManager;

        typeMap = new HashMap<>();
        typeMap.put(0,"i32");
        typeMap.put(1,"f32");

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

    public void traducir(Terceto terceto){
        switch (terceto.operador) {
            case "+":
                procesarOperandos(terceto);
                if (terceto.tipo == Atributo.longType){
                    agregarCodigo("i32.add");
                } else if (terceto.tipo == Atributo.floatType){
                    agregarCodigo("f32.add");
                } else {
                    throw new RuntimeException("Error al tratar el operador +");
                }
                break;
            case "-":
                procesarOperandos(terceto);
                if (terceto.tipo == Atributo.longType){
                    agregarCodigo("i32.sub");
                } else if (terceto.tipo == Atributo.floatType){
                    agregarCodigo("f32.sub");
                } else {
                    errManager.error("Error al procesar el tipo de dato: " + terceto.tipo, -1);
                }
                break;
            case ":=":
                procesarOperando(terceto.operando2);
                agregarCodigo("global.set $" + terceto.operando1);
                break;
            case "*":
                procesarOperandos(terceto);
                if (terceto.tipo == Atributo.longType){
                    agregarCodigo("i32.mul");
                } else if (terceto.tipo == Atributo.floatType){
                    agregarCodigo("f32.mul");
                }
                break;
            case "/":
                procesarOperandos(terceto);
                if (terceto.tipo == Atributo.longType){
                    agregarCodigo("i32.div_s");
                } else if (terceto.tipo == Atributo.floatType){
                    agregarCodigo("f32.div");
                }
                break;
            case "call":
                agregarCodigo("call $" + terceto.operando1);
                break;
            case "return":
                procesarOperando(terceto.operando1);
                agregarCodigo("return");
                break;
            case "inicio_main":
                mainName = terceto.operando1;
                break;

        }
    }

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

    private void procesarOperando(String operando) {

        if (esNumero(operando)) {
            // Generado en pila, no hay que hacer nada.
            return;
        }

        // Si no es un numero, esta en la TS, por lo que hay que tratarlo.
        Atributo t = ts.obtener(operando);
        tratarOperandoTS(operando, t);
    }

    // Auxiliar para ver si el terceto tiene una entrada a la TS o un indice a otro terceto.
    private boolean esNumero(String s) {
        // Permite enteros, positivos o negativos
        return s.matches("-?\\d+");
    }


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
    //"(global $nombre (mut tipo) (valor_inicial))"
    public void generarGlobales(){
        for (Map.Entry<String, Atributo> entrada : ts.getTabla().entrySet()){
            Atributo atr = entrada.getValue();
            String clave = entrada.getKey();
            if ((atr.uso == Atributo.USO_VARIABLE || atr.uso == Atributo.USO_PARAMETRO)){
                if (atr.type == Atributo.longType){
                    String tipoAssembly = convertirTipo(atr.type);

                    agregarCodigo("(global $" + clave + " (mut " + tipoAssembly + ") ("+tipoAssembly+".const " + (int)(atr.numValue) + "))");
                }
            }
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
            tabs += 1;
            for (Terceto terceto : tercetos){
                traducir(terceto);
            }
            tabs -= 1;
            agregarCodigo(")");
        }
    }


    private void generarInicio(){
        codigoGenerado.append("(module\n");
        tabs += 1;
    }

    private void generarFin(){
        agregarCodigo("(export \"main\" (func $"+ mainName +"))");
        tabs -= 1;
        agregarCodigo(")");
    }

    // Funcion para agregar lineas al codigo.
    private void agregarCodigo(String linea){
        codigoGenerado.append("\t".repeat(tabs)).append(linea).append("\n");
    }

    private String convertirTipo(int tipo){
        return typeMap.get(tipo);
    }
}
