package Compilador.Lexer;

import Compilador.AccionesSemanticas.*;
import java.io.*;
import java.util.*;

public class MatrizLoader {

    public static int[][] cargarTransiciones(String path) throws IOException {
        List<int[]> filas = new ArrayList<>();
        int colsCount = -1;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", -1);

                if (colsCount == -1) {
                    colsCount = values.length;
                }

                int[] fila = new int[colsCount];
                for (int i = 0; i < colsCount; i++) {
                    if (i < values.length && !values[i].trim().isEmpty())
                        fila[i] = Integer.parseInt(values[i].trim());
                }
                filas.add(fila);
            }
        }
        return filas.toArray(new int[0][colsCount]);
    }

    public static AccionSemantica[][] cargarAcciones(String path) throws IOException {
        List<AccionSemantica[]> filas = new ArrayList<>();
        int colsCount = -1;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", -1);

                if (colsCount == -1) {
                    colsCount = values.length;
                }

                AccionSemantica[] fila = new AccionSemantica[colsCount];
                for (int i = 0; i < colsCount; i++) {
                    if (i < values.length && !values[i].trim().isEmpty()) {
                        int numero = Integer.parseInt(values[i].trim());
                        fila[i] = AccionesFactory.get(numero);
                    } else {
                        fila[i] = null;
                    }
                }
                filas.add(fila);
            }
        }
        return filas.toArray(new AccionSemantica[0][colsCount]);
    }
}
