package Compilador.Lexer;

import Compilador.ErrorHandler.ErrorManager;

import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {
    private final Map<String, Atributo> tabla = new HashMap<>();

    public void insertar(String lexema, Atributo atributo) {
        if (tabla.containsKey(lexema)) {
            ErrorManager.getInstance().debug("El lexema ya existe " + lexema + " en la tabla");
            tabla.get(lexema).ref++;
            return;
        }
        tabla.put(lexema, atributo);
        ErrorManager.getInstance().debug("Se agrego el lexema " + lexema + " a la tabla");
    }

    public Atributo obtener(String lexema) {
        return tabla.get(lexema);
    }

    public boolean existe(String lexema){
        return tabla.containsKey(lexema);
    }

    public void modificar(String lexemaAnterior, String lexemaNuevo, Atributo atributo){
        tabla.remove(lexemaAnterior);
        insertar(lexemaNuevo, atributo);
    }

    @Override
    public String toString() {
        return tabla.toString().replace("}, ", "}\n");
    }
}
