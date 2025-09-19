package Compilador.Lexer;
import Compilador.AccionesSemanticas.*;

import java.io.*;


public class AnalizadorLexico {
    private int[][] transiciones;
    private AccionSemantica[][] acciones;

    private int lineNumber = 1;
    private MyReader reader;
    private StringBuilder buffer = new StringBuilder();
    public TablaSimbolos ts = new TablaSimbolos();

    public static final int estadoError = 30;
    
    public void addLine(){
        this.lineNumber +=1;
    }

    public void addToToken(char ch){
        buffer.append(ch);
    }

    public String getTokenString(){
        return buffer.toString();   //pasa lo q hay en el buffer a un string
    }

    public void emptyToken(){
        buffer.setLength(0);    // vacía el contenido
    }

    private static AnalizadorLexico instance;

    public AnalizadorLexico(String filename) throws IOException {
        this.reader = new PushbackMyReader(filename);
        this.transiciones = new int[30][16];
        this.acciones = new AccionSemantica[30][16];
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
        if (instance != null ){
            return instance;
        }
        return null;
    }

    public void leerMatrizTransiciones() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("Matrices/matriz_transiciones.csv"))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null && row < transiciones.length) {
                String[] values = line.split(",");
                for (int col = 0; col < transiciones[row].length; col++) {
                    transiciones[row][col] = Integer.parseInt(values[col].trim());
                }
                row++;
            }
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
            case '+': return 8;
            case '-': return 9;
            case '_': return 10;
            case '.': return 11;
            case '%': return 12;
            case '\n': return 13;  // salto linea
            case ' ': return 14;
            case '\t': return 14;

            default:
                if (Character.isDigit(c)) return 15;      // d digito
                if (Character.isUpperCase(c)) return 16;  // Lmayus
                if (Character.isLowerCase(c)) return 17;  // Lminus

                return 30; //ponemos el estado de error mas lejos

        }
    }

    public int yylex(){
        int estadoActual = 0; //estado inicial

        //Mientras no estoy en estado de error o fin de texto, ciclo
        while (estadoActual != estadoError ){
            //leo un caracter del archivo fuente
            int c = reader.read();
            if (c == MyReader.EOF){ // caracter de fin.
                return 0; // no se si esto, habria q ver el token en construccion capaz
            }
            char ch = (char) c;

            //busco el proximo estado y su accion semantica asociada
            int columna = getColumna(ch);
            estadoActual = this.transiciones[estadoActual][columna];
            AccionSemantica as = this.acciones[estadoActual][columna];

            if (as != null){
                int resultadoAS = as.ejecutar(ch);
                if (resultadoAS != TokenType.sinFinalizar)
                    return resultadoAS;
            }
            
        }
        // aca va tratamiento de error 
        return -1; //ver aca error o que onda
    }
    
    
    
}