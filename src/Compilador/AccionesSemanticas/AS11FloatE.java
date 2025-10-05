package Compilador.AccionesSemanticas;

import Compilador.Lexer.AnalizadorLexico;
import Compilador.Lexer.TokenType;

public class AS11FloatE extends AS10Float{
    @Override
    public int ejecutar(char ch) {
        if (ch != 'F'){
            return super.ejecutar(ch);
        } else {
            AnalizadorLexico al = AnalizadorLexico.getInstance();
            al.addToBuffer(ch);
            return TokenType.sinFinalizar;
        }
    }

}
