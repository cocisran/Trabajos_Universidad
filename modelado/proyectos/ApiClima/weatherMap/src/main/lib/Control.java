package main.lib;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.io.*;

/**
 * Clase auxiliar para llevar el registro en tiempo de las peticiones
 */
public class Control {

    private static String rutaGuardado = "files/systemFiles/cacheTime.dat";

    /**
     * Metodo que nos dice si ha pasado el tiempo suficiente para que sea necesario
     * actualizar el cache
     * 
     * @return true si ha pasaado mas de una hora o si es la primera ejecucion false
     *         en caso contrario
     */
    public static boolean actualizarCache() {

        try {
            ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(rutaGuardado));
            @SuppressWarnings("unchecked")
            LocalDateTime horaAct = (LocalDateTime) entrada.readObject();
            entrada.close();
            return pasoUnaHora(horaAct);
        } catch (Exception e) {
            setHoraActualizacion();
            return true;
        }

    }

    /**
     * Almacena la hora en la que se termino de actualizar el cache
     * 
     * @return true si se guardo con exito, false en caso contrario
     */
    public static boolean setHoraActualizacion() {
        try {
            ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(rutaGuardado));
            salida.writeObject(LocalDateTime.now());
            salida.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Metodo auxiliar que informa si ha pasado una hora entre la hora actual y la
     * pasada por parametro
     * 
     * @param h fecha de referencia
     * @return true si ha pasado mas de una hora, false en caso contrario
     */
    private static boolean pasoUnaHora(LocalDateTime h) {
        LocalDateTime hora = LocalDateTime.now();
        return h.until(hora, ChronoUnit.MINUTES) > 60;
    }

}