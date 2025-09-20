package Compilador.AccionesSemanticas;

import Compilador.Lexer.AnalizadorLexico;
import Compilador.Lexer.TokenType;

public class AS2Add implements AccionSemantica{

    @Override
    public int ejecutar(char ch){
        AnalizadorLexico al = AnalizadorLexico.getInstance();
        al.addToBuffer(ch);
        return TokenType.sinFinalizar;
    };
}