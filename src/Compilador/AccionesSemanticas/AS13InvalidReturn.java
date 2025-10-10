package Compilador.AccionesSemanticas;

import Compilador.Lexer.TokenType;

public class AS13InvalidReturn implements AccionSemantica{
    @Override
    public int ejecutar(char ch) {
        return TokenType.INVALID;
    }
}
