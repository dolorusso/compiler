package Compilador.Lexer;

import java.util.HashMap;
import java.util.Map;

public class TokenNames {

    private static final Map<Integer, String> tokenNames = new HashMap<>();

    static {
        tokenNames.put(TokenType.ID, "ID");
        tokenNames.put(TokenType.CTEL, "CTEL");
        tokenNames.put(TokenType.IF, "IF");
        tokenNames.put(TokenType.ELSE, "ELSE");
        tokenNames.put(TokenType.ENDIF, "ENDIF");
        tokenNames.put(TokenType.PRINT, "PRINT");
        tokenNames.put(TokenType.RETURN, "RETURN");
        tokenNames.put(TokenType.LAMBDA, "LAMBDA");
        tokenNames.put(TokenType.ASIGN, "ASIGNAR");
        tokenNames.put(TokenType.MENORIGUAL, "MENORIGUAL");
        tokenNames.put(TokenType.MAYORIGUAL, "MAYORIGUAL");
        tokenNames.put(TokenType.IGUALIGUAL, "IGUALIGUAL");
        tokenNames.put(TokenType.DISTINTO, "DISTINTO");
        tokenNames.put(TokenType.FLECHA, "FLECHA");
        tokenNames.put(TokenType.LONG, "LONG");
        tokenNames.put(TokenType.DO, "DO");
        tokenNames.put(TokenType.UNTIL, "UNTIL");
        tokenNames.put(TokenType.TRUNC, "TRUNC");
        tokenNames.put(TokenType.CR, "CR");
        tokenNames.put(TokenType.STRING, "STRING");
        tokenNames.put(TokenType.CTEF, "CTEF");
        tokenNames.put(TokenType.IDCOMP, "IDCOMP");
        tokenNames.put(TokenType.CADENASTR, "CADENASTR");
        tokenNames.put(TokenType.INVALID, "INVALID");
    }

    public static String getTokenName(int tokenValue) {
        if (tokenValue < 257) {
            // Si es un ASCII
            if (tokenValue >= 32 && tokenValue <= 126) {
                return Character.toString((char) tokenValue);
            } else {
                // ASCII no imprimible
                return String.format("ASCII(%d)", tokenValue);
            }
        }

        // Si es un token definido O NO LO ENCUENTRA
        return tokenNames.getOrDefault(tokenValue, "UNKNOWN(" + tokenValue + ")");
    }
}
