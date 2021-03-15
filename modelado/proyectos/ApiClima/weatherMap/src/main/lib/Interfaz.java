package main.lib;

import java.util.AbstractMap;
import java.util.HashMap;

/**
 * Clase usada para modelar la salida por consola de la informacion
 */
public class Interfaz {

    /**
     * Metodo que recibe el cache para procesar y presentar la informacion en
     * consola
     * 
     * @param cache el cache con los datos de las ciudades
     * @return String que contiene un formato de salida por consola
     */
    public static String salidaConsola(HashMap<String, Lugar> cache) {

        String fondoI = "\u001B[46m";
        String fondoN = "\u001B[42m";
        String rst = "\u001B[0m";
        String encabezado = "       Pais    Ciudad\t\t\tEstado del clima \t\tTemperatura\tSensacion Termica" + rst
                + "\n";
        String seccionNacional = "***** DESTINOS     NACIONALES *****" + rst + "\n";
        String seccionInternacional = "***** DESTINOS INTERNACIONALES *****" + rst + "\n";
        StringBuffer salida = new StringBuffer(fondoI + seccionInternacional + fondoI + encabezado);
        StringBuffer nacionales = new StringBuffer("");

        int cN = 1;
        int cI = 1;

        for (Lugar e : cache.values()) {
            if (e.esNacional()) {
                nacionales.append(fondoN + "*" + numerar(cN) + ")" + rst + e.toString() + "\n");
                cN++;
            } else {
                salida.append(fondoI + "*" + numerar(cI) + ")" + rst + e.toString() + "\n");
                cI++;
            }

        }
        salida.insert(0, fondoN + seccionNacional + fondoN + encabezado + nacionales.toString());
        return salida.toString();
    }

    /**
     * Metodo que genera una barra de progreso
     * 
     * @param titulo   titulo de la barra
     * @param pie      pie de la barra
     * @param progreso porcentaje de progreso (si es mayor a cien la barra siempre
     *                 se mostrara llena)
     * @return Un String que representa una barra de progreso con el porcentaje de
     *         avance pasada por parametro
     */
    public static String barraProgreso(String titulo, String pie, int progreso) {
        String salida = "";
        salida += titulo + " \n";
        salida += "[";
        for (int k = 0; k <= 100; k++) {
            if (k % 2 == 0) {
                if (k <= progreso && progreso != 0) {
                    salida += "\u001B[42m" + " " + "\u001B[0m";
                } else {
                    salida += " ";
                }
            }

        }
        salida += "]" + progreso + "%\n" + pie + "\n";
        return salida;
    }

    /**
     * Metodo auxiliar para numerar las entradas
     * 
     * @param c numero
     * @return numero en formato string
     */
    private static String numerar(int c) {
        int n = 5;
        String a = "";
        a += c;
        int j = n - a.length();

        for (int i = 0; i < j; i++) {
            a = " " + a;
        }
        return a;
    }
}