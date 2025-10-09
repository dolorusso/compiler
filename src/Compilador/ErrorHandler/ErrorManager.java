package Compilador.ErrorHandler;

public class ErrorManager {


    public enum Nivel {
        ERROR, WARNING, DEBUG
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
        System.err.println("[ERROR] Línea " + linea + ": " + msg);
    }

    public void warning(String msg, int linea) {
        if (nivelActual.ordinal() >= Nivel.WARNING.ordinal())
            System.out.println("[WARNING] Línea " + linea + ": " + msg);
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
}
