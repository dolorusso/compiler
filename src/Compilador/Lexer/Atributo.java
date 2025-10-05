package Compilador.Lexer;

public class Atributo {
    public int type; //Long 0, float 1
    public String strValue;
    public double numValue;

    public static final int longType = 0;
    public static final int floatType = 1;
    public static final int stringType = 2;

    public Atributo(int type, String strValue){
        this.type = type;
        this.strValue = strValue;
    }
    public Atributo(int type){
        this.type = type;
    }
    public Atributo(String strValue){
        this.strValue = strValue;
    }
    public Atributo(double numValue){ this.numValue = numValue;}
}
