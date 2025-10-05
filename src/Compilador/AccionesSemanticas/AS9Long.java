package Compilador.AccionesSemanticas;

import Compilador.Lexer.AnalizadorLexico;
import Compilador.Lexer.Atributo;
import Compilador.Lexer.TokenType;

public class AS9Long implements AccionSemantica{

    @Override
    public int ejecutar(char ch) {
        AnalizadorLexico al = AnalizadorLexico.getInstance();
        double num;
        try{
            num = Double.parseDouble(al.getBufferString());
        }
        catch (NumberFormatException e){
            // No deberia pasar por aca, ya que manejamos con los estados.
            System.out.println("Error. Constante long no valida. Insertando 0");
            num = 0.0;
        }

        if (ch != 'L'){
            System.out.println("Error. Las constantes long deben terminar en L");
            al.addToBuffer('L');
            al.goBackReader(ch);
        } else {
            al.addToBuffer(ch);
        }

        //La clave va a ser el numero completo, sin truncar y con el sufijo
        String clave = al.getBufferString();

        al.ts.insertar(clave,new Atributo(Atributo.longType, num));
        al.setYylval(clave);

        return TokenType.CTEL;
    }
}
