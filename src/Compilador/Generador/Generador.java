package Compilador.Generador;

import Compilador.Lexer.Atributo;
import Compilador.Lexer.TablaSimbolos;

import java.util.*;

public class Generador {
    public final List<Terceto> tercetos;
    private final Stack<String> scopeStack;
    private final Map<String, Atributo> pasajeParametrosAux;

    public Generador() {
        scopeStack = new Stack<>();
        pasajeParametrosAux = new HashMap<>();
        tercetos = new ArrayList<>();
    }

    public int agregarTerceto(String o1, String o2, String operador){
        Terceto tercet = new Terceto(operador, o1, o2);
        tercetos.add(tercet);
        return tercetos.size()-1;
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
        return getCurrentScope() + "." + id;
    }

    public void agregarParametro(boolean esCR, int tipo, String ID){
        Atributo atributo = new Atributo(tipo,esCR,Atributo.USO_PARAMETRO);
        atributo.declarado = true;
        pasajeParametrosAux.put(ID, atributo);
    }

    public void aplicarAmbito(TablaSimbolos ts, int tipoFuncion){
        Atributo funcion = new Atributo(tipoFuncion, Atributo.USO_FUNCION);
        ArrayList<String> parametros = new ArrayList<>();
        for (Map.Entry<String, Atributo> entry : pasajeParametrosAux.entrySet()) {
            String clave = entry.getKey();
            Atributo valor = entry.getValue();
            // Al parametro no se le agrega el mangle name por que no nos interesa el ambito,
            // es para verificar en llamados a funciones que el parametro existe.
            parametros.add(clave);
            // Guardamos el mangled para poder buscarlo en la tabla de simbolos de manera univoca.
            String mangledName = mangleName(clave);
            ts.insertar(mangledName, valor);
        }
        funcion.parametros = parametros;
        ts.insertar(getCurrentScope(), funcion);

        pasajeParametrosAux.clear();
    }

    // Funcion auxiliar para sacar el ultimo valor de un id compuesto y retornal el resto.
    private String quitarUltimo(String IDCOMP){
        int pos = IDCOMP.lastIndexOf('.');
        return (pos == -1) ? IDCOMP : IDCOMP.substring(0, pos);
    }

    public boolean checkearAmbito(String IDCOMP){
        // Sacamos el ultimo valor para poder comparar ambitos, sin la ultima ID.
        String sinUltimo = quitarUltimo(IDCOMP);

        return sinUltimo.equals(getCurrentScope());
    }

    public boolean estaDeclarada(String IDCOMP, TablaSimbolos ts){
        Atributo atributo = ts.obtener(IDCOMP);
        if  (atributo == null)
            return false;
        return atributo.declarado;
    }

    public boolean checkearAlcance(String IDCOMP, TablaSimbolos ts){
        String sinUltimo = quitarUltimo(IDCOMP);
        String ambitoActual = getCurrentScope();

        // Viendo si es un subset, verificamos que el ambito sea posible.
        boolean alcanceValido = sinUltimo.equals(ambitoActual) || ambitoActual.startsWith(sinUltimo + ".");

        // Verificamos que exista y este declarado.
        if (alcanceValido) {
            Atributo aux = ts.obtener(IDCOMP);
            if (aux == null)
                return false;
            return aux.declarado;
        } else {
            return false;
        }
    }


}

