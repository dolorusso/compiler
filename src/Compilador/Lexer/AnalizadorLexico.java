package Compilador.Lexer;
import Compilador.AccionesSemanticas.*;
import Compilador.ErrorHandler.ErrorManager;
import Compilador.Parser.ParserVal;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class AnalizadorLexico {
    private int[][] transiciones;
    private AccionSemantica[][] acciones;
    private ParserVal yylvalActual;
    private int lineNumber = 1;
    private MyReader reader;
    private StringBuilder buffer = new StringBuilder();
    public TablaSimbolos ts = new TablaSimbolos();

    public static final int estadoError = 30;
    
    public void addLine(){
        this.lineNumber +=1;
    }
    public int getLine(){ return this.lineNumber;}

    public void addToBuffer(char ch){
        buffer.append(ch);
    }

    public String getBufferString(){
        return buffer.toString();   //pasa lo q hay en el buffer a un string
    }

    public void emptyBuffer(){
        buffer.setLength(0);    // vacía el contenido
    }

    public void goBackReader(int c){
        reader.unread(c); //le paso el codigo del char y lo mete de vuelta al reader
    }

    private static AnalizadorLexico instance;

    private List<AccionSemantica> accionesSemanticas;

    private ErrorManager errManager;

    public AnalizadorLexico(String filename) throws IOException {
        this.reader = new PushbackMyReader(filename);
        this.errManager = ErrorManager.getInstance();
        this.transiciones = MatrizLoader.cargarTransiciones(
                "src/Compilador/Lexer/Matrices/matriz_transiciones.csv",
                estadoError
        );
        imprimirMatriz( this.transiciones);
        this.acciones = MatrizLoader.cargarAcciones(
                "src/Compilador/Lexer/Matrices/matriz_acciones_semanticas.csv"
        );
    }

    public static AnalizadorLexico getInstance(String filename) throws IOException {
        AnalizadorLexico al = instance;
        if (al == null){
            instance = new AnalizadorLexico(filename);
            al = instance;
            return al;
        }
        return al;
    }

    public static AnalizadorLexico getInstance() {
        AnalizadorLexico al = instance;
        if (al != null ){
            return al;
        } else {
            throw new IllegalStateException("No se ha inicializado el analizador lexico");
        }
    }

    
    public int getColumna(char c) {
        switch (c) {
            case '@': return 0;
            case '&': return 1;
            case '=': return 2;
            case ':': return 3;
            case '<': return 4;
            case '>': return 5;
            case '!': return 6;
            case '(': return 7;
            case ')': return 7;
            case '*': return 7;
            case '/': return 7;
            case '{': return 7;
            case '}': return 7;
            case '_': return 7;
            case ';': return 7;
            case ',': return 7;
            case '+': return 8;
            case '-': return 9;
            case '.': return 10;
            case '%': return 11;
            case '\n': return 12;  // salto linea
            case ' ': return 13;
            case '\t': return 13;
            case '\r': return 13;
            case (char)-1: return 13;
            default:
                if (Character.isDigit(c)) return 14;      // d digito
                if (Character.isUpperCase(c)) return 15;  // Lmayus
                if (Character.isLowerCase(c)) return 16;  // Lminus
                return 30; //ponemos el estado de error mas lejos

        }
    }

    public void imprimirMatriz(int[][] matriz) {
        for (int[] fila : matriz) {
            for (int val : fila) {
                errManager.debug(val + "\t"); // tab para que quede alineado
            }
            System.out.println();
        }
        errManager.debug("Hay " + matriz.length + " filas y " + matriz[0].length + " columnas");
    }


    public int yylex(){
        int estadoActual = 0; //estado inicial

        //Mientras no estoy en estado de error o fin de texto, ciclo
        while (estadoActual != estadoError ){
            //leo un caracter del archivo fuente
            int c = reader.read();
            if (c == MyReader.EOF && buffer.isEmpty()){ // caracter de fin.
                return 0;
            }
            char ch = (char) c;

            //busco el proximo estado y su accion semantica asociada
            int columna = getColumna(ch);
            AccionSemantica as = this.acciones[estadoActual][columna];
            if (as != null){
                int resultadoAS = as.ejecutar(ch);
                if (resultadoAS != TokenType.sinFinalizar) {
                    buffer.setLength(0);
                    return resultadoAS;
                }
            }
            estadoActual = this.transiciones[estadoActual][columna];


        }
        // aca va tratamiento de error 
        return -1; //ver aca error o que onda
    }


    public void setYylval(String strLexVal) {
        this.yylvalActual = new ParserVal(strLexVal);
    }

    public ParserVal getYylval() {
        return yylvalActual;
    }
}