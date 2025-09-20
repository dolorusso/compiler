package Compilador.AccionesSemanticas;

import Compilador.Lexer.AnalizadorLexico;
import Compilador.Lexer.Atributo;
import Compilador.Lexer.TokenType;

public class AS4StringReturn implements AccionSemantica{

    @Override
    public int ejecutar(char ch){
        AnalizadorLexico al = AnalizadorLexico.getInstance();

        String strVal = al.getBufferString().substring(1);
        al.addToBuffer(ch); //Agrego el ultimo &
        String strLexVal = al.getBufferString();

        al.ts.insertar(strLexVal, new Atributo(strVal));

        al.setYylval(strLexVal);


        return TokenType.STRING;
    };
}