package tetris.juego;

import java.io.Serializable;

/**
 * Crea un objeto serializable de que cotiene un nombre y un cantidad de puntos
 */
public class Puntuacion implements Serializable {

    private long score;
    private String jugador;

    /**
     * Crea un objeto score sin nombre asignado y con cero puntos
     *
     * @param points Puntos a guardar
     * @param name   nombre del jugador
     */
    public Puntuacion(long points, String name) {
        score = points;
        jugador = name;
    }

    /**
     * Indica la puntuacion actual
     * 
     * @return la puntuacion alcanzada
     */
    public long obtenerPuntuacion() {
        return score;
    }

    /**
     * Nos dice que jugador obtuvo la puntuacion y la puntuacion
     * 
     * @return nombre del jugador que obtuvo la puntuacion y la puntuacion
     */

    @Override
    public String toString() {
        return jugador + "   " + score;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Puntuacion otra = (Puntuacion) obj;
        return this.score == otra.score && this.jugador == otra.jugador;
    }

}