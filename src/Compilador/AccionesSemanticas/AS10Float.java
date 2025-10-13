package Compilador.AccionesSemanticas;

import Compilador.ErrorHandler.ErrorManager;
import Compilador.Lexer.AnalizadorLexico;
import Compilador.Lexer.Atributo;
import Compilador.Lexer.TokenType;
import Compilador.Parser.ParserVal;


public class AS10Float implements AccionSemantica {
    public static final double FLOAT_POSITIVE_MAX = 3.40282347E38d;
    public static final double FLOAT_POSITIVE_MIN = 1.17549435E-38d;
    public static final double FLOAT_NEGATIVE_MAX = -1.17549435E-38d;
    public static final double FLOAT_NEGATIVE_MIN = -3.40282347E38d;
    @Override
    public int ejecutar(char ch) {
        AnalizadorLexico al = AnalizadorLexico.getInstance();
        al.goBackReader(ch);
        double val;
        String str = al.getBufferString();
        str = str.replace("F", "E");
        al.setYylval(str);
        try {
            val = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            // No deberia pasar por aca, ya que manejamos con los estados.
            ErrorManager.getInstance().error("Constante float invalida. Insertando 0", al.getLine());
            al.ts.insertar(str, new Atributo(Atributo.floatType,0.0));
            return TokenType.CTEF;
        }

        if (val > FLOAT_POSITIVE_MAX) {
            ErrorManager.getInstance().warning("Constante fuera de rango, truncado a " + FLOAT_POSITIVE_MAX, al.getLine());
            al.ts.insertar(str, new Atributo(Atributo.floatType, FLOAT_POSITIVE_MAX));
        } else if (val < FLOAT_POSITIVE_MIN && val > 0.0) {
            ErrorManager.getInstance().warning("Constante fuera de rango,  truncado a 0", al.getLine());
            al.ts.insertar(str, new Atributo(Atributo.floatType, 0.0));
        } else if (val < 0.0 && val > FLOAT_NEGATIVE_MAX) {
            ErrorManager.getInstance().warning("Constante fuera de rango, truncado a 0", al.getLine());
            al.ts.insertar(str, new Atributo(Atributo.floatType, 0.0));
        } else if (val < FLOAT_NEGATIVE_MIN) {
            ErrorManager.getInstance().warning("Constante fuera de rango, truncado a " + FLOAT_NEGATIVE_MIN, al.getLine());
            al.ts.insertar(str, new Atributo(Atributo.floatType, FLOAT_NEGATIVE_MIN));
        } else {
            al.ts.insertar(str, new Atributo(Atributo.floatType, val));
        }


        return TokenType.CTEF;
    }

}
