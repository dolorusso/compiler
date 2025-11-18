package Compilador.AccionesSemanticas;

import Compilador.ErrorHandler.ErrorManager;
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
            ErrorManager.getInstance().warning("Constante long no valida. Insertando 0", al.getLine());
            num = 0.0;
        }

        if (ch != 'L'){
            ErrorManager.getInstance().warning("Las constantes long deben terminar en L",al.getLine());
            al.addToBuffer('L');
            al.goBackReader(ch);
        } else {
            al.addToBuffer(ch);
        }

        //La clave va a ser el numero completo, sin truncar y con el sufijo
        String clave = al.getBufferString();

        Atributo atributo = new Atributo(Atributo.longType, num, Atributo.USO_CONSTANTE);

        al.ts.insertar(clave,atributo);
        al.setYylval(clave);

        return TokenType.CTEL;
    }
}
