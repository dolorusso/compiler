package Compilador.Generador;

import Compilador.Lexer.Atributo;
import Compilador.Lexer.TablaSimbolos;

import java.util.*;

public class Generador {
    public final List<Terceto> tercetos;
    private final Stack<String> scopeStack;
    private final Map<String, Atributo> pasajeParametrosAux;
    private final List<ParametroReal> llamadaFuncionAux;

    // Clase interna para abstraer la operacion de tercetos y entradas de la tabla de simbolos.
    private static class OperandoInfo {
        boolean esTerceto;
        int tipo;
        String representacion;
    }

    public Generador() {
        scopeStack = new Stack<>();
        pasajeParametrosAux = new HashMap<>();
        tercetos = new ArrayList<>();
        llamadaFuncionAux = new ArrayList<>();
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

    public String checkearAlcance(String IDCOMP, TablaSimbolos ts){
        String sinUltimo = quitarUltimo(IDCOMP);
        String ambitoActual = getCurrentScope();

        // Viendo si es un subset, verificamos que el ambito sea posible.
        boolean alcanceValido = sinUltimo.equals(ambitoActual) || ambitoActual.startsWith(sinUltimo + ".");

        // Verificamos que exista y este declarado.
        if (alcanceValido) {
            Atributo aux = ts.obtener(IDCOMP);
            if (aux == null)
                return "Identificador no encontrado";
            if (!aux.declarado)
                return "Identificador no declarado";
            return null;
        } else {
            return "Identificador fuera de alcance";
        }
    }

    // Agregar terceto sin tipo, por default se utiliza tipo = -1.
    public int agregarTerceto(String operador, String o1, String o2){
        Terceto tercet = new Terceto(operador, o1, o2);
        tercetos.add(tercet);
        return tercetos.size()-1;
    }

    // Agregar terceto con tipo.
    public int agregarTerceto(String operador, String o1, String o2, int tipo){
        Terceto tercet = new Terceto(operador, o1, o2, tipo);
        tercetos.add(tercet);
        return tercetos.size()-1;
    }


    private OperandoInfo resolverOperando(String valor, TablaSimbolos ts) {
        // Intentamos parsear como terceto. el valor es el indice de el array de tercetos
        try {
            int indice = Integer.parseInt(valor);
            Terceto t = tercetos.get(indice);
            OperandoInfo info = new OperandoInfo();
            info.esTerceto = true;
            info.tipo = t.tipo;
            info.representacion = valor;
            return info;
        } catch (Exception ignored) { }

        // Si no es un terceto, lo buscamos en la TS, pq el valor es el lexema
        Atributo atr = ts.obtener(valor);
        if (atr == null || !atr.declarado)
            return null; // no declarado

        OperandoInfo info = new OperandoInfo();
        info.esTerceto = false;
        info.tipo = atr.type;
        info.representacion = valor;
        return info;
    }

    // Genera tercetos para ambos multiplicaciones y divisiones.
    public String generarTercetoValido(String operador, String izquierda, String derecha, TablaSimbolos ts) {

        // Resolvemos ambos operandos.
        OperandoInfo izq = resolverOperando(izquierda, ts);
        if (izq == null)
            return "Lado izquierdo no declarado.";

        OperandoInfo der = resolverOperando(derecha, ts);
        if (der == null)
            return "Lado derecho no declarado.";

        // Chequeo tipos.
        int tipoResultante = checkearCompatibilidad(operador, izq.tipo, der.tipo);
        if (tipoResultante == Atributo.invalidType)
            return "Tipos incompatibles.";

        // Crear terceto con tipo resultante.
        agregarTerceto(operador, izquierda, derecha, tipoResultante);

        return null;
    }

    public int getUltimoTerceto(){
        return tercetos.size()-1;
    }

    int checkearCompatibilidad(String op, int tipo1, int tip2){
        //if (op.equals(":=")){
            if (tipo1 == Atributo.longType && tip2 == Atributo.longType)
                return Atributo.longType;
        //}

        return Atributo.invalidType;
    }

    // Verifica si una variable se puede leer dependiendo de su semantica
    public String puedoLeer(String IDCOMP, TablaSimbolos ts){
        Atributo id = ts.obtener(IDCOMP);
        // Checkeo dependiendo de la semantica del parametro.
        if (id.uso == Atributo.USO_PARAMETRO){
            if (id.esCR){
                return "La variable no puede ser usada para leer.";
            }
            return null;
        }

        // Si es una variable, se puede leer.
        if (id.uso == Atributo.USO_VARIABLE){
            return null;
        }

        // Lo demas no se puede leer.
        return "La variable no puede ser usada para leer.";
    }

    public String puedoEscribir(String IDCOMP, TablaSimbolos ts){
        Atributo id = ts.obtener(IDCOMP);
        if (id.uso == Atributo.USO_VARIABLE || id.uso == Atributo.USO_PARAMETRO){
            return null;
        }
        return "La variable no puede ser usada para escribir.";
    }

    public String puedoLlamar(String IDCOMP, TablaSimbolos ts){
        Atributo id = ts.obtener(IDCOMP);
        if (id.uso == Atributo.USO_FUNCION){
            return null;
        }
        return "La variable no puede ser usada para llamada.";
    }

    // Funcion para agregar a la estructura auxiliar llamadaFuncionAux
    // En las llamadas a funciones guardamos el par parametroReal -> parametroFormal
    // para luego vincularlo en reglas de orden superior.
    public void agregarParametroReal(String parametroReal, String parametroFormal){
        ParametroReal pr = new ParametroReal(parametroReal, parametroFormal);
        llamadaFuncionAux.add(pr);
    }

    // Funcion para vincularle el ambito correspondiente a los parametros reales, ya que en los llamados
    // no tienen el ambito pero en la tabla de simbolos los guardamos con este.
    public String vincularIDS(String idFuncion, TablaSimbolos ts){
        return
    }

    public String checkearParametrosLlamada(String idFuncion, TablaSimbolos ts){
        Map<String, Atributo> parametrosFormales = new HashMap<>();
        for (String parametroFormal : ts.obtener(idFuncion).parametros) {
            Atributo atributoPF = ts.obtener(vincularIDS(parametroFormal,ts));
            parametrosFormales.put(parametroFormal, null);
        }


    }

}

