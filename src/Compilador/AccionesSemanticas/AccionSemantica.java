package Compilador.AccionesSemanticas;
import Compilador.Lexer.AnalizadorLexico;

public interface AccionSemantica{
    int ejecutar(char ch);
}