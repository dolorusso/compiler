package Compilador.Lexer;

public class Atributo {
    public int type;
    public String strValue;
    public double numValue;
    public short ref;

    public static final int longType = 0;
    public static final int floatType = 1;
    public static final int stringType = 2;

    public Atributo(String strValue){
        type = stringType;
        this.strValue = strValue;
        ref = 1; //si creo un atributo -> alguien lo va a referenciar.
    }

    public Atributo(int type, double numValue){
        this.type = type;
        this.numValue = numValue;
    }

    @Override
    public String toString() {
        return "Atributo{" + "type=" + type + ", strValue=" + strValue + ", numValue=" + numValue + '}';
    }
}
