package Compilador.Generador;

import Compilador.ErrorHandler.ErrorManager;
import Compilador.Lexer.Atributo;
import Compilador.Lexer.TablaSimbolos;

import java.util.*;

public class Generador {
    public final List<Terceto> tercetos;
    private final Stack<String> scopeStack;
    private final Map<String, Atributo> pasajeParametrosAux;
    private final List<ParametroLlamada> llamadaFuncionAux;
    private final List<String> asignacionMultipleCTEAux;
    private final List<String> asignacionMultipleIDAux;
    public int lambdaCounter;


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
        asignacionMultipleCTEAux = new ArrayList<>();
        asignacionMultipleIDAux = new ArrayList<>();
        lambdaCounter = 0;
    }

    public List<Terceto> getTercetos(){
        return tercetos;
    }
    //----------------------------------------------------------------------------------------------------------------//
    //-------------------------------------------------FUNCIONES DE SCOPE/AMBITO--------------------------------------//
    //----------------------------------------------------------------------------------------------------------------//

    // Funcion para devolver el ambito actual.
    public String getCurrentScope(){
        try {
            return scopeStack.peek();
        } catch (EmptyStackException e) {
            ErrorManager.getInstance().error("Declaracion de programa incorrecta", -1);
            return "";
        }
    }

    // Funcion para agregar a la pila de ambitos un nuevo ambito.
    public void enterScope(String name) {
        String full;
        if (scopeStack.isEmpty())
            full = name;
        else
            full = scopeStack.peek() + "." + name;
        scopeStack.push(full);
    }

    // Funcion para desapilar un ambito de la pila.
    public void exitScope() {
        if (!scopeStack.isEmpty()) {
            scopeStack.pop();
        }
    }

    // Funcion para construir el mangleName a partir del id de la variable.
    // El mangle name sera igual a los IDCOMP.
    public String mangleName(String id) {
        return getCurrentScope() + "." + id;
    }

    // Funcion auxiliar para sacar el ultimo valor de un id compuesto y retornal el resto.
    private String quitarUltimo(String IDCOMP){
        int pos = IDCOMP.lastIndexOf('.');
        return (pos == -1) ? IDCOMP : IDCOMP.substring(0, pos);
    }

    // Funcion para verificar si que se declaren variables solo en el ambito correspondiente.
    public boolean checkearAmbito(String IDCOMP){
        // Sacamos el ultimo valor para poder comparar ambitos, sin la ultima ID.
        String sinUltimo = quitarUltimo(IDCOMP);

        return sinUltimo.equals(getCurrentScope());
    }

    //----------------------------------------------------------------------------------------------------------------//
    //-------------------------------------------------FUNCIONES DE PARAMETROS Y FUNCIONES----------------------------//
    //----------------------------------------------------------------------------------------------------------------//

    // Funcion para agregar a la estructura auxiliar pasajeParametrosAux un nuevo parametro.
    public void agregarParametro(boolean esCR, int tipo, String ID){
        Atributo atributo = new Atributo(tipo,esCR,Atributo.USO_PARAMETRO);
        atributo.declarado = true;
        pasajeParametrosAux.put(ID, atributo);
    }

    public void aplicarAmbito(TablaSimbolos ts, int tipoFuncion, String funcName){
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
        ts.insertar(funcName, funcion);


        pasajeParametrosAux.clear();
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
                return "Parametro formal " + f + " no utilizado.";
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

                tipoParametroReal = tercetos.get(i).tipo;
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

    public String getLambdaName() {
        lambdaCounter += 1;
        return "lambda#" + lambdaCounter;
    }

    //----------------------------------------------------------------------------------------------------------------//
    //-----------------------------------------------------------FUNCIONES DE VARIABLES-------------------------------//
    //----------------------------------------------------------------------------------------------------------------//

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

    // Verifica si una variable se puede leer dependiendo de su semantica
    public String puedoLeer(String IDCOMP, TablaSimbolos ts){
        Atributo id = ts.obtener(IDCOMP);
        // Checkeo dependiendo de la semantica del parametro.
        if (id.uso == Atributo.USO_PARAMETRO){
            if (id.esCR){
                return "La variable "+ IDCOMP + " no puede ser usada para leer debido a semantica CR.";
            }
            return null;
        }

        // Si es una variable, se puede leer.
        if (id.uso == Atributo.USO_VARIABLE){
            return null;
        }



        return "La variable "+ IDCOMP + " no puede ser usada para leer.";
    }

    public String puedoEscribir(String IDCOMP, TablaSimbolos ts){
        Atributo id = ts.obtener(IDCOMP);
        if (id.uso == Atributo.USO_VARIABLE || id.uso == Atributo.USO_PARAMETRO){
            return null;
        }

        return "La variable "+ IDCOMP +" no puede ser usada para escribir.";
    }

    public String puedoLlamar(String IDCOMP, TablaSimbolos ts){
        Atributo id = ts.obtener(IDCOMP);
        if (id.uso == Atributo.USO_FUNCION){
            return null;
        }

        return "La variable "+ IDCOMP +" no puede ser usada para llamada.";
    }

    // Funcion para abstraer codigo de checkeos de IDs.
    public String validarLecturaYAlcance(String id, TablaSimbolos ts) {

        String mensaje = checkearAlcance(id, ts);
        if (mensaje != null)
            return mensaje;

         mensaje = puedoLeer(id, ts);
        if (mensaje != null)
            return mensaje;

        return null; // Sin errores
    }


    //----------------------------------------------------------------------------------------------------------------//
    //-----------------------------------------------------------FUNCIONES DE TERCETOS--------------------------------//
    //----------------------------------------------------------------------------------------------------------------//

    // Agregar terceto sin tipo, por default se utiliza tipo = -1.
    public int agregarTerceto(String operador, String o1, String o2){
        Terceto terceto = new Terceto(operador, o1, o2);
        tercetos.add(terceto);
        ErrorManager.getInstance().debug("Agregando terceto: " + terceto.toString());
        return tercetos.size()-1;
    }

    // Agregar terceto con tipo.
    public int agregarTerceto(String operador, String o1, String o2, int tipo){
        Terceto terceto = new Terceto(operador, o1, o2, tipo);
        tercetos.add(terceto);
        ErrorManager.getInstance().debug("Agregando terceto: " + terceto.toString());
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

    int checkearCompatibilidad(String op, int tipo1, int tipo2){
        //if (op.equals(":=")){
            if (tipo1 == Atributo.longType && tipo2 == Atributo.longType)
                return Atributo.longType;
        //}

        if (tipo1 == Atributo.floatType && tipo2 == Atributo.floatType)
            return Atributo.floatType;

        if (tipo1 == Atributo.lambdaType && tipo2 == Atributo.lambdaType)
            return Atributo.lambdaType;

        return Atributo.invalidType;
    }


    // Funcion para generar tercetos. Supone previo llamado a checkearParametrosLlamada, por lo que
    // no se realizaran checkeos de tipo, existencia, declaracion.
    public String generarTercetosLlamado(String idFuncion, TablaSimbolos ts){
        // Obtenemos la funcion sin verificar que sea correcta ya que esto se deberia cumplir por prerequisitos.
        Atributo func = ts.obtener(idFuncion);

        // Guardamos los atributos y utilizamos el campo StringValue
        List<ParametroLlamada> parametrosCR = new ArrayList<>();

        for (ParametroLlamada pr : llamadaFuncionAux){
            String formalKey = idFuncion + "." + pr.formal;
            Atributo formalAtr = ts.obtener(formalKey);

            // Tratamiento especial para parametros CR, ya que estos requieren el copiado en salida.
            if (formalAtr.esCR){
                // Guardamos los atributos que sean CR
                parametrosCR.add(new ParametroLlamada(pr.real, formalKey));
                // No necesitamos generarle un terceto, ya que no tiene sentido darle el valor actual del real al formal.
            } else {
                agregarTerceto(":=", formalKey, pr.real); // TODO Esto no permite recursion
            }
        }

        // Generamos los tercetos para asignar a las variables de salida por semantica CR.
        int callIdx = agregarTerceto("call", idFuncion, "-", func.type);
        for (ParametroLlamada pr : parametrosCR){
            agregarTerceto(":=", pr.real, pr.formal);
        }


        llamadaFuncionAux.clear();
        return callIdx + "";
    }

    // Funcion para verificar que el argumento del trunc sea un float.
    public String validarExpresionTrunc(String expresion, TablaSimbolos ts){
        OperandoInfo op = resolverOperando(expresion, ts);
        if (op == null)
            return "Argumento no declarado.";
        if (op.tipo != Atributo.floatType)
            return "Argumento no es un float.";
        return null;
    }

    public String imprimirTercetos() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < tercetos.size(); i++) {
            result.append("[").append(i).append("] ").append(tercetos.get(i).toString());
        }
        return result.toString();
    }

    //----------------------------------------------------------------------------------------------------------------//
    //-----------------------------------------------------------FUNCIONES DE IF--------------------------------------//
    //----------------------------------------------------------------------------------------------------------------//
    // crear BF (branch if false) con destino desconocido.
    public int generarBF(int condicion) {
        // BF condicion -> destino (inicialmente "-")
        return agregarTerceto("BF", condicion + "", "-");
    }

    // crear BI (salto incondicional) con destino desconocido.
    public int generarBI() {
        // BI - -> destino (inicialmente "-")
        return agregarTerceto("BI", "-", "-");
    }

    // Backpatch, rellena el operando 2 del terceto indicado.
    public void rellenarOperando(int indiceTerceto, int nuevoOp, int indiceModificable) {

        Terceto viejo = tercetos.get(indiceTerceto);


        Terceto nuevoTer;
        if (indiceModificable == 1){
            nuevoTer = new Terceto(viejo.operador, nuevoOp + "", viejo.operando2, viejo.tipo);
        }
        else{
            nuevoTer = new Terceto(viejo.operador, viejo.operando1, nuevoOp + "", viejo.tipo);
        }

        //reemplazo el terceto viejo con el nuevo operando (a donde tiene que saltar)
        tercetos.set(indiceTerceto, nuevoTer);

        ErrorManager.getInstance().debug("Backpatch: terceto[" + indiceTerceto + "] ahora -> " + nuevoTer.toString());
    }

    //----------------------------------------------------------------------------------------------------------------//
    //-------------------------------------------FUNCIONES DE ASIGNACION MULTIPLE-------------------------------------//
    //----------------------------------------------------------------------------------------------------------------//

    public void agregarConstanteMultiple(String idConstante, TablaSimbolos ts){
        // No checkeamos que este declarada o exista ya que tenerla en el ejecutable implica que existe en la TS.
        asignacionMultipleCTEAux.add(idConstante);
    }

    public String agregarIDMultiple(String IDCOMP, TablaSimbolos ts){
        asignacionMultipleIDAux.add(IDCOMP);
        return null;
    }

    // Funcion que recorre las listas auxiliares verificando la compatibilidad de los pares.
    // (Aunque ahora no sea tan necesario por que solo se pueden declarar LONGS, seria la practica correcta).
    public String generarAsignacionMultiple(TablaSimbolos ts){
        if (asignacionMultipleCTEAux.size() > asignacionMultipleIDAux.size())
            return "Debe haber mayor o igual cantidad de IDs que Constantes en asignacion multiple.";

        int i = 0;
        while (i < asignacionMultipleIDAux.size() && i < asignacionMultipleCTEAux.size()){
            String idConstante = asignacionMultipleCTEAux.get(i);
            String idVariable = asignacionMultipleIDAux.get(i);

            int tipoResultante = checkearCompatibilidad(":=", ts.obtener(idVariable).type, ts.obtener(idConstante).type);
            if (tipoResultante == Atributo.invalidType){
                return "Tipos incompatibles en asignacion multiple. Tipo constante: "
                        + ts.obtener(idConstante).type + ", tipo variable: " + ts.obtener(idVariable).type;
            }

            agregarTerceto(":=", idVariable, idConstante);

            i = i + 1;
        }

        while (i < asignacionMultipleIDAux.size()){
            String idVariable = asignacionMultipleIDAux.get(i);
            agregarTerceto(":=", idVariable, "0L");
            ErrorManager.getInstance().warning("Asignando 0L a la variable " + idVariable + " en asignacion multiple.");
            i = i + 1;
        }

        return null;
    }

    public void limpiarAsignacionMultiple(){
        asignacionMultipleCTEAux.clear();
        asignacionMultipleIDAux.clear();
    }
}

