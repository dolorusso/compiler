import Compilador.Lexer.AnalizadorLexico;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        AnalizadorLexico al = AnalizadorLexico.getInstance("Programas/pruebaLexico.txt");
//        int x;
//        do {
//            x = al.yylex();
//            System.out.println("Reconoce token " + x);
//        } while (x > 0);
        System.out.println(Double.parseDouble(".E10"));
    }
}