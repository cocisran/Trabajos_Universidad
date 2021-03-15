package tetris.tetronimos;

import tetris.Tablero;

/**
 * Clase abstracta padre de todos los tetronimos y la clase fila
 */
public abstract class Tetronimo {

    protected Bloque[][] tetronimo;

    /**
     * Crea un objeto de tipo Tetronimo
     * 
     * @param tetro Matriz que representa la forma que posee el tetronimo.
     */
    public Tetronimo(Bloque[][] tetro) {
        tetronimo = tetro;

    }

    /**
     * Devuelve la matriz de bloques que representa la forma y posicion del
     * tetronimo
     * 
     * @return Matriz de los bloques que conforman al tetronimo
     */
    public Bloque[][] map() {
        return tetronimo;
    }

    /**
     * Metodo que rota la pieza a la derecha cuando se presiona la tecla sup de las
     * flechas.
     */
    public abstract boolean rotarUp();

    /**
     * Metodo que rota la pieza a la izquierda cuando se presiona la tecla Z.
     */
    public abstract boolean rotarZ();

    /**
     * Metodo que resta -1 a la fila de la posicion actual de todos los bloques que
     * conforman el tetronimo.
     * 
     * @return verdadero si se pudo hacer caer el tetronimo, falso en caso contraio.
     */
    public boolean caer() {
        Tablero referencia = Tablero.obtenerPartida();
        boolean cae = false;
        this.borrarEstela();

        if (this.movimientoLegal(-1, 0)) {
            for (int i = 0; i < tetronimo.length; i++) {
                for (int j = 0; j < tetronimo[i].length; j++) {

                    tetronimo[i][j].cambiarPosicion(tetronimo[i][j].fila() - 1, tetronimo[i][j].columna());
                }
            }
            cae = true;
        }
        referencia.dibujarTetronimo(this);
        return cae;
    }

    /**
     * Metodo que resta -1 a la columna de la posicion actual de todos los bloques
     * que conforman el tetronimo.
     */
    public boolean moverIzq() {
        boolean rot = false;
        Tablero referencia = Tablero.obtenerPartida();

        this.borrarEstela();

        if (this.movimientoLegal(0, -1)) {
            for (int i = 0; i < tetronimo.length; i++) {
                for (int j = 0; j < tetronimo[i].length; j++) {

                    tetronimo[i][j].cambiarPosicion(tetronimo[i][j].fila(), tetronimo[i][j].columna() - 1);
                }
            }
            rot = true;
        }
        referencia.dibujarTetronimo(this);
        return rot;
    }

    /**
     * Metodo que suma 1 a la columna de la posicion actual de todos los bloques que
     * conforman el tetronimo.
     */
    public boolean moverDer() {
        boolean rot = false;
        Tablero referencia = Tablero.obtenerPartida();

        this.borrarEstela();

        if (this.movimientoLegal(0, 1)) {
            for (int i = 0; i < tetronimo.length; i++) {
                for (int j = 0; j < tetronimo[i].length; j++) {

                    tetronimo[i][j].cambiarPosicion(tetronimo[i][j].fila(), tetronimo[i][j].columna() + 1);
                }
                rot = true;
            }
        }
        referencia.dibujarTetronimo(this);
        return rot;
    }

    /**
     * Metodo que nos dice si dado cierto incremento a la posicion de todos los
     * bloques del tetronimo, resulta en una nueva posicion valida
     * 
     * @param x incremento en filas
     * @param y incremento en columna
     * @return verdadero si el movimiento es valido, falso en caso contrario
     */
    protected boolean movimientoLegal(int x, int y) {
        boolean legal = true;
        Tablero referencia = Tablero.obtenerPartida();

        for (int i = 0; i < tetronimo.length && legal; i++) {
            for (int j = 0; j < tetronimo[i].length && legal; j++) {
                if (tetronimo[i][j].obtenerColor() != Color.VACIO) {

                    if (tetronimo[i][j].fila() + x < 0)
                        legal = false;
                    if (tetronimo[i][j].fila() + x > 30)
                        legal = false;
                    if (tetronimo[i][j].columna() + y < 0)
                        legal = false;
                    if (tetronimo[i][j].columna() + y > 9)
                        legal = false;
                    if (legal && !referencia.estaVacio(tetronimo[i][j].fila() + x, tetronimo[i][j].columna() + y)) {
                        legal = false;
                    }

                }

            }
        }
        return legal;
    }

    /**
     * Metodo que pasado un mapa de bloques nos dice si pueden o no ser colocados en
     * el tablero
     * 
     * @param rot mapa de bloques a comprobar
     * @return verdero si pueden ser colocados , falso en caso contrario
     */
    protected static boolean movimientoLegal(Bloque[][] rot) {
        boolean legal = true;
        Tablero referencia = Tablero.obtenerPartida();

        for (int i = 0; i < rot.length && legal; i++) {
            for (int j = 0; j < rot[i].length && legal; j++) {
                if (rot[i][j].obtenerColor() != Color.VACIO) {
                    if (rot[i][j].fila() < 0)
                        legal = false;
                    if (rot[i][j].fila() > 40)
                        legal = false;
                    if (rot[i][j].columna() < 0)
                        legal = false;
                    if (rot[i][j].columna() > 9)
                        legal = false;
                    if (legal && !referencia.estaVacio(rot[i][j].fila(), rot[i][j].columna()))
                        legal = false;
                }

            }
        }

        return legal;
    }

    /**
     * Genera un hijo de tetronimo del tipo pasado como parametro
     * 
     * @param tipo tetronimo a generar
     * @return tetronimo del tipo solicitado
     */

    public static Tetronimo dispensadora(String tipo) {
        switch (tipo) {
        case "I":
            return new I();
        case "J":
            return new J();
        case "L":
            return new L();
        case "O":
            return new O();
        case "S":
            return new S();
        case "T":
            return new T();
        case "Z":
            return new Z();
        default:
            return null;
        }
    }

    /**
     * Borra la "mancha" que va generando el tetronimo al caer
     */
    protected void borrarEstela() {
        Tablero partida;
        partida = Tablero.obtenerPartida();
        partida.borraTetronimo(this);
    }

    /**
     * Obtiene la clase del tetronimo
     * 
     * @return clase del tetronimo
     */
    public String obtenerClase() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        String exit = "";

        for (int i = 0; i < tetronimo.length; i++) {
            for (int j = 0; j < tetronimo[i].length; j++) {

                if (tetronimo[i][j] != null) {
                    exit += tetronimo[i][j].toString();
                }

            }
            exit += "\n";
        }
        return exit;
    }

    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Tetronimo otra = (Tetronimo) obj;
        boolean igual = true;
        for (int i = 0; i < tetronimo.length && igual; i++) {
            for (int j = 0; j < tetronimo.length && igual; j++) {
                if (!tetronimo[i][j].equals(otra.tetronimo[i][j])) {
                    igual = false;
                }
            }
        }
        return igual;
    }
}