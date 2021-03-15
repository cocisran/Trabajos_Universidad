package tetris.juego;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.LinkedList;

import tetris.juego.Puntuacion;

/**
 * Crea un tablero con todas las puntuaciones obtenidas por los jugadores
 * 
 */
public class TableroPuntuaciones implements Serializable {

    private LinkedList<Puntuacion> historial;

    /**
     * Crea un objeto de tipo TableroPuntuaciones a partir del archivo local, si el
     * archivo no existe lo crea
     */
    public TableroPuntuaciones() {
        TableroPuntuaciones aux;

        try (ObjectInputStream lectura = new ObjectInputStream(new FileInputStream("historial"))) {

            aux = (TableroPuntuaciones) lectura.readObject();
            this.historial = aux.historial;
            lectura.close();
        } catch (IOException | ClassNotFoundException e) {
            historial = new LinkedList<>();

            try (ObjectOutputStream escritura = new ObjectOutputStream(new FileOutputStream("historial"))) {
                escritura.writeObject(this);
                escritura.close();
            } catch (IOException f) {
                System.out.println("Error: no se pudo generar el archivo");
            }
        }
    }

    /**
     * Crea un objeto de tipo de TableroPuntuaciones apartir de una lista ligada de
     * puntuaciones
     * 
     * @param e Lista de puntuaciones con la cual crear el tablero
     */
    public TableroPuntuaciones(LinkedList<Puntuacion> e) {
        historial = e;
    }

    /**
     * Crea un tablero de puntuaciones a partir de otro tablero de puntuaciones
     * 
     * @param e tablero de puntuaciones del cual se obtendra el nuevo tablero
     */
    public TableroPuntuaciones(TableroPuntuaciones e) {
        this.historial = e.historial;
    }

    /**
     * Devuelve una lista con las 10 mejores puntuaciones o las puntuaciones
     * ordenadas de mayor a menor si son menos de 10
     * 
     * @return una lista ordenada de puntuaciones
     */
    public TableroPuntuaciones mejoresDiez() {
        Puntuacion agregar;
        int cont = 1;
        LinkedList<Puntuacion> top10 = new LinkedList<>();
        agregar = this.mejorPuntuacion();
        while (agregar != null && cont < 10) {
            top10.add(agregar);
            cont++;
            agregar = this.mejorPuntuacion();
        }
        return new TableroPuntuaciones(top10);
    }

    /**
     * Devuelve la mejor puntuacion obtenida hasta el momento
     * 
     * @return la mejor puntuacion obtenida del tablero y null si no hay
     *         puntuaciones
     */
    public Puntuacion mejorPuntuacion() {
        Puntuacion maxima = historial.peek();
        for (Puntuacion puntuacion : historial) {
            if (maxima.obtenerPuntuacion() < puntuacion.obtenerPuntuacion()) {
                maxima = puntuacion;
            }
        }
        if (historial.indexOf(maxima) != -1) {
            return historial.remove(historial.indexOf(maxima));
        } else {
            return null;
        }
    }

    /**
     * Guarda la puntuacion mandada como argumento en el archivo que almacena el
     * objeto
     * 
     * @param nueva puntuacion a agregar al tableroPuntuaciones
     */
    public void guardarPuntuacion(Puntuacion nueva) {
        historial.add(nueva);

        try (ObjectOutputStream escritura = new ObjectOutputStream(new FileOutputStream("historial"))) {
            escritura.writeObject(this);
            escritura.close();
        } catch (IOException e) {
            System.out.println("Error: no se pudo guardar la puntuaacion");
        }
    }

    @Override
    public String toString() {
        String exit = "";
        for (Puntuacion puntuacion : historial) {
            exit += "  " + puntuacion.toString() + "\n";
        }
        return exit;
    }

}