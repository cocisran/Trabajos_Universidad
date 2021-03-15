package funcionalidades;

import estructuras.ArbolAVL;
import almacen.Producto;
import almacen.Inventario;

import java.io.*;
import java.util.Scanner;

public class Lector {
    /**
     * Lee y creaa un almaacen a partir de un archivo de texto plano que contenga
     * las existencias en formato: id, nombre, $ precio , existencias
     * 
     * @param ruta ruta del archivo
     * @return Un inventario formado a partir de archivo proporcionado por parametro
     *         o null en caso de que el arbol no se construya de forma correcta
     */
    public static Inventario leerAlmacen(String ruta) {

        ArbolAVL<Producto> salida = new ArbolAVL<Producto>();

        try {
            Scanner input = new Scanner(new File(ruta));
            while (input.hasNextLine()) {
                Producto in;
                String[] fuente = input.nextLine().split(",", 0);
                for (int i = 0; i < fuente.length; i++) {
                    fuente[i] = fuente[i].trim();
                    if (i == 2) {
                        fuente[i] = fuente[i].replace("$", "");
                    }
                }
                in = new Producto(fuente[0], fuente[1], Double.parseDouble(fuente[2]), Integer.parseInt(fuente[3]));
                salida.agrega(in);
            }
            return new Inventario(salida);
        } catch (Exception e) {
            System.out.println("No ha sido encontrado ningun almacen valido, generando uno nuevo");
        }
        return null;
    }

    

    /**
     * Metodo que lee un archivo serializable a partir del cual se construye el
     * arbol
     * 
     * @param ruta ruta donde se encuentra el archivo
     * @return el inventario  del archivo proporcionado, null si no fue
     *         posible completar la operacion
     */
    public static Inventario leerAlmacenS(String ruta) {
        try {
            FileInputStream respaldo;
            ObjectInputStream entrada = null;

            respaldo = new FileInputStream(ruta);
            entrada = new ObjectInputStream(respaldo);

            @SuppressWarnings("unchecked")
            Inventario salida = (Inventario) entrada.readObject();

            entrada.close();
            return salida;

        } catch (Exception e) {
            System.out.println("Ningun archivo serializado ");
            return null;
        }
    }

    /**
     * Metodo que transforma un string en un entero positivo (manejo de menus por
     * consola)
     * 
     * @param s cadena a transformar
     * @return -1 si el entero resultante es negativo, o la cadena no corresponde a
     *         un entero. En otro caso el entero que representa la cadena de texto
     */
    public static int stringToInt(String s) {
        try {
            int aux = Integer.parseInt(s);
            return aux < 0 ? -1 : aux;
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Metodo que transforma un string en un double positivo (manejo de menus por
     * consola)
     * 
     * @param s cadena a transformar
     * @return -1 si el double resultante es negativo, o la cadena no corresponde a
     *         un double. En otro caso el double que representa la cadena de texto
     */
    public static double stringToDouble(String s) {
        try {
            double aux = Double.parseDouble(s);
            return aux < 0 ? -1 : aux;
        } catch (Exception e) {
            return -1;
        }
    }

}