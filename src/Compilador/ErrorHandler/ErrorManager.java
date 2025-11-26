package Compilador.ErrorHandler;

public class ErrorManager {
    public boolean hayError;

    public enum Nivel {
        ERROR, WARNING, DEBUG, ENTREGA
    }

    private static final ErrorManager instance = new ErrorManager();
    private Nivel nivelActual = Nivel.ERROR; // Por defecto, solo errores

    private ErrorManager() {}

    public static ErrorManager getInstance() {
        return instance;
    }

    public void setNivel(Nivel nivel) {
        this.nivelActual = nivel;
    }

    // Métodos principales:
    public void error(String msg, int linea) {
        hayError = true;
        System.err.println("[ERROR] Linea " + linea + ": " + msg);
    }

    public void warning(String msg, int linea) {
        if (nivelActual.ordinal() >= Nivel.WARNING.ordinal())
            System.out.println("[WARNING] Linea " + linea + ": " + msg);
    }

    public void warning(String msg) {
        if (nivelActual.ordinal() >= Nivel.WARNING.ordinal())
            System.out.println("[WARNING] " + msg);
    }

    public void debug(String msg) {
        if (nivelActual.ordinal() >= Nivel.DEBUG.ordinal())
            System.out.println("[DEBUG] " + msg);
    }
    public void debug(String msg, int linea) {
        if (nivelActual.ordinal() >= Nivel.DEBUG.ordinal())
            System.out.println("[DEBUG] Linea "+ linea +": " + msg);
    }
    public void entrega(String msg) {
        System.out.println("\n\n[RESULTADO] " + msg);
    }

}
