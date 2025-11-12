package Compilador.Generador;

import java.util.Stack;

public class Generador {
    private final Stack<String> scopeStack;

    public Generador() {
        scopeStack = new Stack<>();
    }

    public String getCurrentScope(){
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

}
