package Compilador.AccionesSemanticas;

import Compilador.Lexer.AnalizadorLexico;
import Compilador.Lexer.TokenType;

public class AS6CompoundChar implements AccionSemantica{

    @Override
    public int ejecutar(char ch) {
        AnalizadorLexico al = AnalizadorLexico.getInstance();
        al.addToBuffer(ch);
        String val = al.getBufferString();

        cases:
        switch (val) {
            case ">=":
                return TokenType.MAYORIGUAL;
            case "<=":
                return TokenType.MENORIGUAL;
            case "=!":
                return TokenType.DISTINTO;
            case "==":
                return TokenType.IGUALIGUAL;
            case "->":
                return TokenType.FLECHA;
            case ":=":
                return TokenType.ASIGNAR;
            default:
                break;
        }

        //nunca deberia llegar aca por como funciona la maquina de estados
        throw new RuntimeException("Error en AS6CompoundChar");
    }
}
