package Compilador.AccionesSemanticas;

import Compilador.ErrorHandler.ErrorManager;
import Compilador.Lexer.AnalizadorLexico;
import Compilador.Lexer.Atributo;
import Compilador.Lexer.TokenType;

public class AS12CompIdentifierCheck implements AccionSemantica{

    @Override
    public int ejecutar(char ch) {
        int MAX_LENGTH = AS7IdentifierCheck.MAX_LENGTH;

        AnalizadorLexico al = AnalizadorLexico.getInstance();
        al.goBackReader(ch);
        String ID = al.getBufferString();
        StringBuilder VAL = new StringBuilder();

        String[] subIds = ID.split("\\.");
        for (String subId : subIds) {
            if (subId.length() > MAX_LENGTH) {
                VAL.append(subId, 0, MAX_LENGTH);
                ErrorManager.getInstance().warning("Identificador \"" + subId + "\" muy largo. Truncando a " + MAX_LENGTH + " caracteres");
            } else {
                VAL.append(subId);
            }
            VAL.append(".");
        }
        VAL.deleteCharAt(VAL.length()-1);


        al.ts.insertar(ID, new Atributo(VAL.toString()));
        al.setYylval(ID);
        return TokenType.IDCOMP;
    }
}
