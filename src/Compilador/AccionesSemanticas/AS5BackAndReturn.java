package Compilador.AccionesSemanticas;

import Compilador.Lexer.AnalizadorLexico;

public class AS5BackAndReturn implements AccionSemantica {

    @Override
    public int ejecutar(char ch) {
        AnalizadorLexico al = AnalizadorLexico.getInstance();
        al.goBackReader(ch);

        //como es un solo caracter, devuelvo el primer y unico caracter del buffer
        return al.getBufferString().charAt(0);
    }

}
