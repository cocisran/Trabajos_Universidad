package gnuplotProcess;

import java.io.*;
import java.util.Scanner;
import funcionalidades.*;

/**
 * Clase diseñada para procesar los datos obtenidos de la simulacion
 */
public class Interprete {
    /**
     * Metodo que debe ser llamada unicamente si se genero una simulacion completa
     * en el programa, obtiene la media de los datos en bruto obtenidos de la
     * simulacion
     * 
     * @param ruta
     * @param rutaSalida
     */
    public static void obtenerMediaPorHora(String ruta, String rutaSalida) {
        double datos[] = new double[17];
        int num_datos[] = new int[17];
        try {
            Scanner input = new Scanner(new File(ruta));
            while (input.hasNextLine()) {
                String entrada = input.nextLine();
                if (entrada != null && !entrada.trim().isEmpty()) {
                    String[] fuente = entrada.split(",", 0);

                    int hora = Integer.parseInt(fuente[0]) - 7;
                    if (hora >= 0) {
                        double tiempo = Double.parseDouble(fuente[1]);
                        datos[hora] += tiempo;
                        num_datos[hora]++;
                    }

                }

            }
            for (int i = 0; i < datos.length; i++) {
                datos[i] /= num_datos[i];
                Escritor.escribirStringEnTxt((i + 7) + " " + Double.toString(datos[i]), rutaSalida, true);
            }

        } catch (Exception e) {
            System.out.println("verifique que : " + ruta
                    + " no haya sido modificado, si el problema persiste corra una nueva simulacion");

        }
    }
}