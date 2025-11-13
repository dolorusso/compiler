package Compilador.Lexer;

public class Atributo {
    public int type;
    public String strValue;
    public double numValue;
    // Variable para determinar si un parametro de funcion es Copia Resultado. Caso contrario es Copia valor.
    public boolean esCR;
    public short ref = 1;

    public static final int longType = 0;
    public static final int floatType = 1;
    public static final int stringType = 2;

    public Atributo(String strValue){
        type = stringType;
        this.strValue = strValue;
    }

    public Atributo(int type, double numValue){
        this.type = type;
        this.numValue = numValue;
    }

    public Atributo(int type, boolean esCR){
        this.type = type;
        this.esCR = esCR;
    }

    public Atributo(int type){
        this.type = type;
    }

    @Override
    public String toString() {
        return "Atributo{" + "type=" + type + ", strValue=" + strValue + ", numValue=" + numValue + ", ref=" + ref + '}';
    }
}
