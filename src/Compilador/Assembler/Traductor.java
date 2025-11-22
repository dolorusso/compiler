package Compilador.Assembler;

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
    public Traductor(TablaSimbolos ts){
        this.ts = ts;
        tabs = 0;
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
                if (terceto.tipo == Atributo.longType){
                    agregarCodigo("i32.add");
                }
            case "inicio_main":
                mainName = terceto.operando1;
        }
    }

    public void generarOperacion(Terceto operacion){

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
            agregarCodigo("(func $" + nombreFuncion);
            tabs += 1;
            for (Terceto terceto : tercetos){
                traducir(terceto);
            }
            tabs -= 1;
            agregarCodigo(")");
        }
    }
    public void emitGet(String operando){
        try{
            int indiceTerceto = Integer.parseInt(operando);
            //todo agregarlinea
        } catch (Exception e) {
            Atributo atr = ts.obtener(operando);
            if (atr != null && atr.uso == Atributo.USO_CONSTANTE){
                if (atr.type == Atributo.longType){
                    codigoGenerado.append("    i32.const ").append(atr.numValue).append("\n");
                } else {
                    codigoGenerado.append("    f32.const ").append(atr.strValue).append("\n");
                }
            }
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
