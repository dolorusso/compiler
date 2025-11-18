package Compilador.Generador;

public class Terceto {
    public String operador;
    public String operando1;
    public String operando2;

    public Terceto(String operador, String operando1, String operando2) {
        this.operador = operador;
        this.operando1 = operando1;
        this.operando2 = operando2;
    }


    @Override
    public String toString() {
        return
            "terceto{" +
                "operador='" + operador + '\'' +
                ", operando1='" + operando1 + '\'' +
                ", operando2='" + operando2 + '\'' +
                '}';
    }
}