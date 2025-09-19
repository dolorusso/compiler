package Compilador.Lexer;

import java.util.HashMap;
import java.util.Map;

class TablaSimbolos {
    private Map<String, Atributo> tabla = new HashMap<>();

    public void insertar(String lexema, Atributo atributo) {
        tabla.putIfAbsent(lexema, atributo);
    }

    public Atributo obtener(String lexema) {
        return tabla.get(lexema);
    }
}
