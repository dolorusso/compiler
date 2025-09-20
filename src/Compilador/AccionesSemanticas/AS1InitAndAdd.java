package Compilador.AccionesSemanticas;

import Compilador.Lexer.AnalizadorLexico;
import Compilador.Lexer.TokenType;

public class AS1InitAndAdd implements AccionSemantica{

    @Override
    public int ejecutar(char ch){
        AnalizadorLexico al = AnalizadorLexico.getInstance();
        al.emptyBuffer();
        al.addToBuffer(ch);

        return TokenType.sinFinalizar;

    };
}