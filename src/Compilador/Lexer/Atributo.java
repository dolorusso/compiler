package Compilador.Lexer;

import java.util.ArrayList;
import java.util.List;

public class Atributo {
    public int type;
    public String strValue;
    public double numValue;
    // Variable para determinar si un parametro de funcion es Copia Resultado. Caso contrario es Copia valor.
    public boolean esCR;
    public boolean declarado = false;
    public String uso;
    //Para las funciones, una lista de parametros. Cada campo es la entrada a la tabla del parametro.
    public List<String> parametros = null;
    public boolean tieneReturn = false;

    public static final int invalidType = -1;
    public static final int longType = 0;
    public static final int floatType = 1;
    public static final int stringType = 2;

    public static final String USO_CONSTANTE = "constante";
    public static final String USO_VARIABLE = "variable";
    public static final String USO_PARAMETRO = "parametro";
    public static final String USO_FUNCION = "funcion";
    public static final String USO_IDLEX = "id lexico";


    public Atributo(String strValue, String uso){
        type = stringType;
        this.strValue = strValue;
        this.uso = uso;
    }

    public Atributo(int type, double numValue, String uso){
        this.type = type;
        this.numValue = numValue;
        this.uso = uso;
        this.declarado = true;
    }

    public Atributo(int type, boolean esCR, String uso){
        this.type = type;
        this.esCR = esCR;
        this.uso = uso;
    }

    public Atributo(int type, String uso){
        this.type = type;
        //si a algo se le asigna un tipo, es por que esta declarado
        this.declarado = true;
        this.uso = uso;
    }

    @Override
    public String toString() {
        if (parametros == null)
            return
                "Atributo{" +
                    "type=" + type +
                    ", strValue=" + strValue +
                    ", numValue=" + numValue +
                    ", declarado=" + declarado  +
                    ", esCR=" + esCR +
                    ", uso=" + uso  +
                '}';
        return
                "Atributo{" +
                "type=" + type +
                ", declarado=" + declarado  +
                ", uso=" + uso  +
                ", parametros=" + parametros +
                '}';
    }


}
