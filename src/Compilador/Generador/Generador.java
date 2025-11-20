package Compilador.Generador;

import Compilador.Lexer.Atributo;
import Compilador.Lexer.TablaSimbolos;

import java.util.*;

public class Generador {
    public final List<Terceto> tercetos;
    private final Stack<String> scopeStack;
    private final Map<String, Atributo> pasajeParametrosAux;
    private final List<ParametroLlamada> llamadaFuncionAux;

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
        ParametroLlamada pr = new ParametroLlamada(parametroReal, parametroFormal);
        llamadaFuncionAux.add(pr);
    }



    public String checkearParametrosLlamada(String idFuncion, TablaSimbolos ts){
        Atributo func = ts.obtener(idFuncion);
        if (func == null || func.uso != Atributo.USO_FUNCION)
            return "Funcion no declarada.";

        // Lista de parametros formales sin ambito.
        List<String> parametrosFormales = func.parametros;

        // Verificar que todos los parametros reales tengan un formal.
        for (ParametroLlamada pr : llamadaFuncionAux){
            if (!parametrosFormales.contains(pr.formal)){
                return "Parametro formal no declarado.";
            }
        }

        // Verificar que se utilicen todos los parametros formales.
        for (String f : parametrosFormales){
            boolean encontrado = false;
            for (ParametroLlamada pr : llamadaFuncionAux){
                if (pr.formal.equals(f)){
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado){
                return "Parametro formal no utilizado.";
            }
        }

        // Checkear tipos y CR
        for (ParametroLlamada pr : llamadaFuncionAux){
            String formalKey = idFuncion + "." + pr.formal;
            Atributo formalAtr = ts.obtener(formalKey);
            if (formalAtr == null)
                return "Parametro formal no declarado.";

            int tipoParametroReal;
            try {
                int i = Integer.parseInt(pr.real);

                // Un terceto no puede ser pasado como parametro CR.
                if (formalAtr.esCR) {
                    return "Parametro CR, se espera asignable.";
                }

                tipoParametroReal = i;
            } catch (Exception e) {
                Atributo realAtr = ts.obtener(pr.real);
                if (realAtr == null || !realAtr.declarado)
                    return "Parametro real no declarado.";
                // todo aca se deberia checkear si es una variable, funcion, etc.

                // Si es copia resultado, solo se le debe pasar un asignable. Las variables son asignables,
                // Pero tambien en llamados a funciones desde funciones los parametros.
                if (formalAtr.esCR){
                    if (!(realAtr.uso == Atributo.USO_VARIABLE || realAtr.uso == Atributo.USO_PARAMETRO ))
                        return "Parametro CR, se espera asignable.";
                }

                tipoParametroReal = realAtr.type;
            }

            // Checkeamos compatibilidad. Utilizamos asignacion ya que es lo que sucedera en los parametros.
            if (checkearCompatibilidad(":=", tipoParametroReal, formalAtr.type) == Atributo.invalidType){
                return "Tipo de parametros incompatible.";
            }

        }
        return null;
    }


    // Funcion para generar tercetos. Supone previo llamado a checkearParametrosLlamada, por lo que
    // no se realizaran checkeos de tipo, existencia, declaracion.
    public String generarTercetosLlamado(String idFuncion, TablaSimbolos ts){
        // Obtenemos la funcion sin verificar que sea correcta ya que esto se deberia cumplir por prerequisitos.
        Atributo func = ts.obtener(idFuncion);

        // Guardamos los atributos y utilizamos el campo StringValue
        List<Atributo> parametrosCR = new ArrayList<>();

        for (ParametroLlamada pr : llamadaFuncionAux){
            String formalKey = idFuncion + "." + pr.formal;
            Atributo formalAtr = ts.obtener(formalKey);

            // Tratamiento especial para parametros CR, ya que estos requieren el copiado en salida.
            if (formalAtr.esCR){
                // Guardamos los atributos,
                parametrosCR.add(formalAtr);
            }
        }


    }

}

