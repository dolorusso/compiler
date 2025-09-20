package Compilador.AccionesSemanticas;


public class AccionesFactory {
    private static final AccionSemantica[] acciones = new AccionSemantica[12];

    static {
        acciones[0]  = new AS0NewLine();
        acciones[1]  = new AS1InitAndAdd();
        acciones[2]  = new AS2Add();
        acciones[3]  = new AS3SpecialReturn();
        acciones[4]  = new AS4StringReturn();
        acciones[5]  = new AS5BackAndReturn();
        acciones[6]  = new AS6CompoundChar();
        acciones[7]  = new AS7IdentifierCheck();
        acciones[8]  = new AS8PalabraReservada();
        acciones[9]  = new AS9Long();
        acciones[10] = new AS10Float();
        acciones[11] = new AS11FloatE();
    }

    public static AccionSemantica get(int numero) {
        if (numero < 0 || numero >= acciones.length) {
            return null;
        }
        return acciones[numero];
    }
}
