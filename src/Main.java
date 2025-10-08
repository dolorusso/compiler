import Compilador.ErrorHandler.ErrorManager;
import Compilador.Lexer.AnalizadorLexico;
import Compilador.Parser.Parser;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        AnalizadorLexico al = AnalizadorLexico.getInstance("Programas/falta_parentesis_condicion.txt");
        Parser p = new Parser(ErrorManager.Nivel.DEBUG);
        p.run();
        //    int x;
        //
        //    do {
        //       x = al.yylex();
        //        System.out.println("Reconoce token " + x);
        //   } while (x != 0);
   }
}
