package Compilador.Generador;

public class ParametroReal {
    public String real;
    public String formal;

    public ParametroReal(String real, String formal){
        this.real = real;
        this.formal = formal;
    }


    @Override
    public String toString(){
        return "ParametroReal{" + "real=" + real + ", formal=" + formal + '}';
    }
}
