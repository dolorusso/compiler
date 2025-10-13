import Compilador.ErrorHandler.ErrorManager;
import Compilador.Lexer.AnalizadorLexico;
import Compilador.Parser.Parser;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        AnalizadorLexico al = AnalizadorLexico.getInstance("Programas/pruebaLexico.txt");
        Parser p = new Parser(ErrorManager.Nivel.DEBUG);
        p.run();


   }
}
