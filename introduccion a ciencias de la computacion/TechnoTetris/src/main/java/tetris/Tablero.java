package tetris;

import tetris.tetronimos.*;
import tetris.juego.*;

/**
 * Clase que crea un nuevo tablero de juego
 */
public class Tablero {
    private static final Tablero partida = new Tablero();

    private Bloque[][] tablero = new Bloque[30][10];
    private Bolsa fila;

    private Tablero() {
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 10; j++) {
                tablero[i][j] = new Bloque(Color.VACIO, i, j);
            }
        }
        fila = new Bolsa();
    }

    /**
     * Regresa el tablero a su estado original y renueva la cola de generacion de
     * los tetronimos
     */
    public void nuevoTablero() {
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 10; j++) {
                tablero[i][j] = new Bloque(Color.VACIO, i, j);
            }
        }
        fila = new Bolsa();
    }

    /**
     * Informa sobre el estado de las casillas del tablero
     * 
     * @param m fila de busqueda
     * @param n columna de busqueda
     * @return Verdadero si la casilla se encuentra vacia, Falso en caso contrario
     */
    public boolean estaVacio(int m, int n) {
        return (m >= 0 && m < 30 && n >= 0 && n < 10) ? tablero[m][n].obtenerColor() == Color.VACIO : true;
    }

    /**
     * Informa sobre el estado en el que se encuentra el tablero (instancia)
     * 
     * @return Instancia del tablero de juego
     */
    public static Tablero obtenerPartida() {
        return partida;
    }

    /**
     * Recupera la bolsa de generacion de los tetronimos.
     * 
     * @return lista con los proximos elementos a ser generados en el juego
     */
    public Bolsa obtenerBolsa() {
        return fila;
    }

    /**
     * Actualiza la bolsa actual de generacion por la bolsa pasada por parametro
     * 
     * @param b Nueva bolsa de generacion del tablero
     */

    public void actualizarBolsa(Bolsa b) {
        fila = b;
    }

    /**
     * Devuelve el bloque en las coordenadas pasadas como parametro
     * 
     * @param m coordenada de la fila de busqueda
     * @param n coordenada de la columna de busqueda
     * @return Bloque en las coordenadas [m][n] y una excepcion en caso de las
     *         coordenadas no indiquen un lugar valido en el tablero
     */
    public Bloque obtenerBloque(int m, int n) {
        return tablero[m][n];
    }

    /***
     * Dibuja el tetronimo pasado como argumento en el tablero
     * 
     * @param tetro tetronimo a dibujar en el tablero
     */
    public void dibujarTetronimo(Tetronimo tetro) {
        Bloque[][] dibujo;
        dibujo = tetro.map();
        for (int i = 0; i < dibujo.length; i++) {
            for (int j = 0; j < dibujo[i].length; j++) {

                if (dibujo[i][j].obtenerColor() != Color.VACIO) {
                    tablero[dibujo[i][j].fila()][dibujo[i][j].columna()].cambiarColor(dibujo[i][j].obtenerColor());
                }
            }
        }
    }

    /***
     * Borra el tetronimo pasado como argumento del tablero
     * 
     * @param tetro tetronimo a borrar
     */
    public void borraTetronimo(Tetronimo tetro) { // borrar metodo
        Bloque[][] dibujo;
        dibujo = tetro.map();
        for (int i = 0; i < dibujo.length; i++) {
            for (int j = 0; j < dibujo[i].length; j++) {

                if (dibujo[i][j].obtenerColor() != Color.VACIO && dibujo[i][j].fila() >= 0 && dibujo[i][j].fila() < 30
                        && dibujo[i][j].columna() >= 0 && dibujo[i][j].columna() < 10)
                    tablero[dibujo[i][j].fila()][dibujo[i][j].columna()].cambiarColor(Color.VACIO);

            }
        }
    }

    /***
     * Limpia las filas que estan completamente vacias
     * 
     * @return numero de filas que fueron limpiadas
     */
    public int limpiarFilas() {
        int filasLimpiadas = 0;

        for (int i = 0; i < 25; i++) {
            if (this.limpiar(i)) {
                for (int j = 0; j < 10; j++) {
                    tablero[i][j].cambiarColor(Color.VACIO);

                }
                filasLimpiadas++;
            }
        }

        return filasLimpiadas;
    }

    /**
     * Hace caer las filas del tablero de forma que no queden filas vacias entre
     * filas con bloques
     */
    public void gravedad() {
        Fila fila;
        boolean cae;

        for (int i = 1; i < 25; i++) {

            fila = new Fila(i);
            if (!fila.estaVacia()) {
                do {
                    cae = fila.caer();
                } while (cae);
            }
        }
    }

    /**
     * Informa si una fila se encuentra llena
     * 
     * @param fila numero de fila a comprobar
     * @return verdadero si la fila esta llena falso en caso contrario
     */
    private boolean limpiar(int fila) {
        boolean borrar = true;

        for (int i = 0; i < 10 && borrar; i++) {
            if (tablero[fila][i].obtenerColor() == Color.VACIO) {
                borrar = false;
            }
        }

        return borrar;
    }

    @Override
    public String toString() {
        String exit = "";

        for (int i = 29; i >= 0; i--) {
            exit += "\t\t\t\t\t\t";
            exit += i >= 10 ? i + " " : i + "  ";
            for (int j = 0; j < 10; j++) {
                exit += tablero[i][j].toString();
            }
            exit += "\n";
        }
        return exit;
    }

}
