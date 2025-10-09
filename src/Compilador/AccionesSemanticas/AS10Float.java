package Compilador.AccionesSemanticas;

import Compilador.ErrorHandler.ErrorManager;
import Compilador.Lexer.AnalizadorLexico;
import Compilador.Lexer.Atributo;
import Compilador.Lexer.TokenType;

public class AS10Float implements AccionSemantica {
    @Override
    public int ejecutar(char ch) {
        AnalizadorLexico al = AnalizadorLexico.getInstance();
        al.goBackReader(ch);
        double val;
        String str = al.getBufferString();
        str = str.replace("F", "E");
        try {
            val = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            // No deberia pasar por aca, ya que manejamos con los estados.
            ErrorManager.getInstance().error("Constante float invalida. Insertando 0", al.getLine());
            al.ts.insertar(str, new Atributo(Atributo.floatType,0.0));
            return TokenType.CTEF;
        }

        if (val > Float.MAX_VALUE) {
            ErrorManager.getInstance().warning("Overflow truncado a " + Float.MAX_VALUE, al.getLine());
            al.ts.insertar(str, new Atributo(Atributo.floatType, Float.MAX_VALUE));
        } else if (val < Float.MIN_VALUE && val != 0.0) {
            ErrorManager.getInstance().warning("Underflow truncado a 0", al.getLine());
            al.ts.insertar(str, new Atributo(Atributo.floatType, 0.0));
        }

        return TokenType.CTEF;
    }

}
