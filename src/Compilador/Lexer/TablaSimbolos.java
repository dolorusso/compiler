package Compilador.Lexer;

import Compilador.ErrorHandler.ErrorManager;

import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {
    private final Map<String, Atributo> tabla = new HashMap<>();

    public void insertar(String lexema, Atributo atributo) {
        tabla.putIfAbsent(lexema, atributo);
        ErrorManager.getInstance().debug("Se agrego el lexema " + lexema + " a la tabla");
    }

    public Atributo obtener(String lexema) {
        return tabla.get(lexema);
    }
}
