package Compilador.AccionesSemanticas;

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
            System.out.println("ERROR, constante float invalida. Insertando 0");
            al.ts.insertar(str, new Atributo(Float.MAX_VALUE));
            return TokenType.CTEF;
        }

        if (val > Float.MAX_VALUE) {
            System.err.println("Overflow truncado a " + Float.MAX_VALUE);
            al.ts.insertar(str, new Atributo(Float.MAX_VALUE));
        } else if (val < Float.MIN_VALUE && val != 0.0) {
            System.err.println("Underflow truncado a 0");
            al.ts.insertar(str, new Atributo(0.0));
        }

        return TokenType.CTEF;
    }

}
