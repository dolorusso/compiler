package Compilador.AccionesSemanticas;

import Compilador.Lexer.AnalizadorLexico;
import Compilador.Lexer.Atributo;
import Compilador.Lexer.TokenType;

public class AS7IdentifierCheck implements AccionSemantica{
    public static final int MAX_LENGTH = 20;
    @Override
    public int ejecutar(char ch) {
        AnalizadorLexico al = AnalizadorLexico.getInstance();
        al.goBackReader(ch);
        String ID = al.getBufferString();
        String VAL = ID;
        if (ID.length() > MAX_LENGTH) {
            VAL = VAL.substring(0, MAX_LENGTH);
            System.out.println("WARNING! Identificador muy largo. Truncando a " + MAX_LENGTH + " characters");
        }

        al.ts.insertar(ID, new Atributo(VAL));
        al.setYylval(ID);
        return TokenType.ID;
    }
}
