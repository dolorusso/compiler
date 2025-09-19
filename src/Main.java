import Compilador.Lexer.AnalizadorLexico;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        AnalizadorLexico al = new AnalizadorLexico("Path");
        al.yylex();
    }
}