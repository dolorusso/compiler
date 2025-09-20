package Compilador.AccionesSemanticas;

public class AS3SpecialReturn implements AccionSemantica{

    @Override
    public int ejecutar(char ch){
        return (int) ch; //ASCII del char
    };
}