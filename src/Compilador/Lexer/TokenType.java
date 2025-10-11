package Compilador.Lexer;

import Compilador.Parser.Parser;

public class TokenType {
    public static final int sinFinalizar = -1;
    public static final int ID = Parser.ID;
    public static final int CTEL = Parser.CTEL;
    public static final int IF = Parser.IF;
    public static final int ELSE = Parser.ELSE;
    public static final int ENDIF = Parser.ENDIF;
    public static final int PRINT = Parser.PRINT;
    public static final int RETURN = Parser.RETURN;
    public static final int LAMBDA = Parser.LAMBDA;
    public static final int ASIGNAR = Parser.ASIGNAR;
    public static final int MENORIGUAL = Parser.MENORIGUAL;
    public static final int MAYORIGUAL = Parser.MAYORIGUAL;
    public static final int IGUALIGUAL = Parser.IGUALIGUAL;
    public static final int DISTINTO = Parser.DISTINTO;
    public static final int FLECHA = Parser.FLECHA;
    public static final int LONG = Parser.LONG;
    public static final int DO = Parser.DO;
    public static final int UNTIL = Parser.UNTIL;
    public static final int TRUNC = Parser.TRUNC;
    public static final int CR = Parser.CR;
    public static final int STRING = Parser.STRING;
    public static final int CTEF = Parser.CTEF;
    public static final int IDCOMP = Parser.IDCOMP;
    public static final int CADENASTR = Parser.CADENASTR;
    public static final int INVALID = Parser.INVALID;
}
