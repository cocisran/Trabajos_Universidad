package tetris.tetronimos;

import tetris.Tablero;

/**
 * Clase que crean objetos de tipo fila que cuenten con "gravedad"
 */
public class Fila extends Tetronimo {
    /**
     * Crea un objeto de tipo fila a partir del numero de fila en el tablero pasada
     * como parametro
     * 
     * @param x numero de fila del tablero de la cual crear el objeto
     */
    public Fila(int x) {
        super(obtenerFila(x));
    }

    @Override
    public boolean rotarUp() {
        return true;
    }

    @Override
    public boolean rotarZ() {
        return true;
    }

    /**
     * nos dice si una fila se encuentra vacia
     * 
     * @return verdero si esta vacia, falso en caso contrario
     */
    public boolean estaVacia() {
        Boolean vacia = true;
        Bloque[][] linea;
        linea = this.map();

        for (int i = 0; i < 10 && vacia; i++) {
            if (linea[0][i].obtenerColor() != Color.VACIO) {
                vacia = false;
            }
        }

        return vacia;
    }

    /**
     * Auxiliar del constructor del objeto
     * 
     * @param x fila de la cual obtener la informacion del tablero
     * @return la linea del tablero pasada como parametro en una matriz de bloques
     */
    private static Bloque[][] obtenerFila(int x) {
        Bloque[][] linea = new Bloque[1][10];
        Tablero info = Tablero.obtenerPartida();

        for (int i = 0; i < 10; i++) {
            linea[0][i] = new Bloque(info.obtenerBloque(x, i));
        }

        return linea;
    }
}