package main.app;

import main.lib.*;

import java.util.AbstractMap;
import java.util.HashMap;
import java.io.*;

public class Main {

    private static String rutaGuardado = "files/systemFiles/cacheData.dat";

    public static void main(String[] args) {

        HashMap<String, Lugar> cache = leerCache();
        if (Control.actualizarCache()) {
            
            if (cache == null) {
                cache = new HashMap<String, Lugar>();
                Lector.scaner1(cache);
                Lector.scaner2(cache);
            }
            actualizarCache(cache);
        }
        System.out.print("\033\143");
        System.out.println(Interfaz.salidaConsola(cache));
    }

    /**
     * Metodo encargado de actualizar el cache
     * 
     * @param cache cache del programa
     */
    private static void actualizarCache(HashMap<String, Lugar> cache) {
        int maxPeticionesXminuto = 59;
        int tiempo_espera = 1000 * 62;
        int ciudades_actualizadas = 0;
        int cacheSize = cache.size();
        String cabeza = "";
        String pieBarra = "Ultima consulta:\n\u001B[44m Pais    Ciudad    \t\t\tEstado del clima \t\tTemperatura\tSensacion Termica\u001B[0m\n";

        for (Lugar e : cache.values()) {
            Conexion.actualizarCiudad(e);

            cabeza = "Realizando peticiones a openWeather";
            ciudades_actualizadas++;

            System.out.print("\033\143");
            System.out.println(
                    Interfaz.barraProgreso(cabeza, pieBarra + e.toString(), ciudades_actualizadas * 100 / cacheSize));

            if (Conexion.num_conexiones() % maxPeticionesXminuto == 0) {
                cabeza = "Esperando al servidor";
                System.out.print("\033\143");
                System.out.println(Interfaz.barraProgreso(cabeza, pieBarra + e.toString(),
                        ciudades_actualizadas * 100 / cacheSize));
                try {
                    Thread.sleep(tiempo_espera);
                } catch (Exception f) {
                    //
                }
            }

        }
        escribirCache(cache);

    }

    /**
     * Metodo que intenta recuperar el cache almacenado
     * 
     * @return el cache volcado en el archivo
     */
    private static HashMap<String, Lugar> leerCache() {
        try {
            ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(rutaGuardado));
            @SuppressWarnings("unchecked")
            HashMap<String, Lugar> cache = (HashMap<String, Lugar>) entrada.readObject();
            entrada.close();
            return cache;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * Almacena el cache en un archivo local
     * 
     * @param cache cache de programa
     */
    private static void escribirCache(HashMap<String, Lugar> cache) {

        try {
            ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(rutaGuardado));
            salida.writeObject(cache);
            salida.close();
            Control.setHoraActualizacion();
        } catch (Exception e) {
            System.out.println("El cache no fue actualizado");
        }

    }

}