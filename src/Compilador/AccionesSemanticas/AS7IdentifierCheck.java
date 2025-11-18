package Compilador.AccionesSemanticas;

import Compilador.ErrorHandler.ErrorManager;
import Compilador.Lexer.AnalizadorLexico;
import Compilador.Lexer.Atributo;
import Compilador.Lexer.TokenType;

public class AS7IdentifierCheck implements AccionSemantica{
    public static final int MAX_LENGTH = 20;
    @Override
    public int ejecutar(char ch) {
        AnalizadorLexico al = AnalizadorLexico.getInstance();
        String ID = al.getBufferString();

        if (ID.contains(".")){ //fix para los ID.ID
            return AccionesFactory.get(12).ejecutar(ch);
        }
        al.goBackReader(ch);
        String VAL = ID;
        if (ID.length() > MAX_LENGTH) {
            VAL = VAL.substring(0, MAX_LENGTH);
            ErrorManager.getInstance().warning("Identificador muy largo. Truncando a " + MAX_LENGTH + " characters", al.getLine());
        }

        al.ts.insertar(ID, new Atributo(VAL, Atributo.USO_IDLEX));
        al.setYylval(ID);
        return TokenType.ID;
    }
}
