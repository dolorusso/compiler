import Compilador.ErrorHandler.ErrorManager;
import Compilador.Lexer.AnalizadorLexico;
import Compilador.Parser.Parser;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Uso: compilar <archivo>");
            return;
        }
        String path = args[0];

        AnalizadorLexico al = AnalizadorLexico.getInstance(path);
        Parser p = new Parser(ErrorManager.Nivel.DEBUG);
        p.run();
   }
}
