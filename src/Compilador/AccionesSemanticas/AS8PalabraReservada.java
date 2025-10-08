package Compilador.AccionesSemanticas;

import Compilador.ErrorHandler.ErrorManager;
import Compilador.Lexer.AnalizadorLexico;
import Compilador.Lexer.TokenType;


public class AS8PalabraReservada implements AccionSemantica{
    @Override
    public int ejecutar(char ch) {
        AnalizadorLexico al = AnalizadorLexico.getInstance();
        al.goBackReader(ch);
        String palabra = al.getBufferString();

        try {
            return switch (palabra) {
                case "if" -> TokenType.IF;
                case "else" -> TokenType.ELSE;
                case "endif" -> TokenType.ENDIF;
                case "print" -> TokenType.PRINT;
                case "return" -> TokenType.RETURN;
                case "lambda" -> TokenType.LAMBDA;
                case "do" -> TokenType.DO;
                case "until" -> TokenType.UNTIL;
                case "trunc" -> TokenType.TRUNC;
                case "cr" -> TokenType.CR;
                case "long" -> TokenType.LONG;
                case "string" -> TokenType.STRING;
                default -> throw new IllegalStateException("Posible palabra reservada no reconocida: " + palabra);
            };
        } catch (Exception e) {
            ErrorManager.getInstance().error(e.getMessage(), al.getLine());
            return AnalizadorLexico.estadoError;
    }
}
}
