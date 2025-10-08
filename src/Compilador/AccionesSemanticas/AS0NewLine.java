package Compilador.AccionesSemanticas;

import Compilador.Lexer.AnalizadorLexico;
import Compilador.Lexer.TokenType;

public class AS0NewLine implements AccionSemantica{
    @Override
    public int ejecutar(char ch) {
        AnalizadorLexico al = AnalizadorLexico.getInstance();
        al.addLine();
       return TokenType.sinFinalizar;
    }
}
