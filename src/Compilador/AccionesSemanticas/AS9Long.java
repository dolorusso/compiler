package Compilador.AccionesSemanticas;

import Compilador.Lexer.AnalizadorLexico;
import Compilador.Lexer.Atributo;
import Compilador.Lexer.TokenType;

public class AS9Long implements AccionSemantica{



    @Override
    public int ejecutar(char ch) {
        AnalizadorLexico al = AnalizadorLexico.getInstance();

        int num;
        try{
            num = Integer.parseInt("-" + al.getBufferString()); //obtengo el valor numerico

            //lo pongo negativo por que no quiero que el 255
            // vaya al catch, ya que solo lo puedo checkear en la semantica.
        }
        catch (NumberFormatException e){
            num = Integer.MIN_VALUE; //Guardo el valor mas chico y despues se le cambia el signo
            System.out.println("ERROR, constante long fuera de rango");
        }

        if (ch != 'L'){
            System.out.println("Error. Las constantes long deben terminar en L");
            al.addToBuffer('L');
            al.goBackReader(ch);
        } else {
            al.addToBuffer(ch);
        }

        //La clave va a ser el numero completo, sin truncar y con el sufijo??!?!?
        String clave = al.getBufferString();

        al.ts.insertar(clave,new Atributo(num));
        al.setYylval(clave);

        return TokenType.CTEL;
    }
}
