package Compilador.AccionesSemanticas;

import Compilador.Lexer.AnalizadorLexico;
import Compilador.Lexer.TokenType;

public class AS10Float implements AccionSemantica {
    @Override
    public int ejecutar(char ch) {// TODO !!!ESTO ESTA todo MAL Floats pueden tener Exponente
        AnalizadorLexico al = AnalizadorLexico.getInstance();
        al.goBackReader(ch);

        float val;
        try {
            val = Float.parseFloat(al.getBufferString());
        } catch (NumberFormatException e) {
            val = Float.MIN_VALUE; //Para mantener coherencia con LONG
            System.out.println("ERROR, constante float fuera de rango");
        }




        return TokenType.CTEF;
    }
}
