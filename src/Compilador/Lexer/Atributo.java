package Compilador.Lexer;

public class Atributo {
    public int type; //0int, 1 float
    public String strValue;

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
}
