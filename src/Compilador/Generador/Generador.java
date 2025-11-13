package Compilador.Generador;

import Compilador.Lexer.Atributo;
import Compilador.Lexer.TablaSimbolos;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Generador {
    private final Stack<String> scopeStack;
    private final Map<String, Atributo> pasajeParametrosAux;

    public Generador() {
        scopeStack = new Stack<>();
        pasajeParametrosAux = new HashMap<>();
    }

    public String getCurrentScope(){
        //ver si le ponemos global o error, por que en realidad desde el sintactico no te va a dejar
        return scopeStack.isEmpty() ? "global" : scopeStack.peek();
    }

    public void enterScope(String name) {
        String full;
        if (scopeStack.isEmpty())
            full = name;
        else
            full = scopeStack.peek() + "." + name;
        scopeStack.push(full);
    }

    public void exitScope() {
        if (!scopeStack.isEmpty()) {
            String left = scopeStack.pop();
        }
    }

    public String mangleName(String id) {
        return id + ":" + getCurrentScope();
    }

    public void agregarParametro(boolean esCR, String tipo, String ID){
        //checkear esto, deberiamos poner timpo como numvalue en lugar de string
        Atributo atributo = new Atributo(Integer.parseInt(tipo),esCR);
        pasajeParametrosAux.put(ID, atributo);
    }

    public void aplicarAmbito(TablaSimbolos ts){
        for (Map.Entry<String, Atributo> entry : pasajeParametrosAux.entrySet()) {
            String clave = entry.getKey();
            Atributo valor = entry.getValue();

            ts.insertar(mangleName(clave), valor);
        }
        pasajeParametrosAux.clear();
    }

    public boolean checkearAmbito(String IDCOMP){
        int pos = IDCOMP.lastIndexOf('.');
        String sinUltimo = (pos == -1) ? IDCOMP : IDCOMP.substring(0, pos);

        return sinUltimo.equals(getCurrentScope());
    }


}

