package Compilador.Generador;

public class ParametroLlamada {
    public String real;
    public String formal;

    public ParametroLlamada(String real, String formal){
        this.real = real;
        this.formal = formal;
    }


    @Override
    public String toString(){
        return "ParametroReal{" + "real=" + real + ", formal=" + formal + '}';
    }
}
