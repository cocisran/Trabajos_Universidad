package tetris.tetronimos;

import tetris.tetronimos.Bloque;
import tetris.Tablero;

/**
 * Clase padre de los tetronimos de tipo JLSTZ -esta division tiene
 * justificacion en que los 5 se forman de matrices 3*3 y su metodo rotar es
 * analogo
 */
public class JLSTZ extends Tetronimo {

    public JLSTZ(Bloque[][] b) {
        super(b);
    }

    /**
     * Metodo que rota la pieza a la derecha cuando se presiona la tecla sup de las
     * flechas.
     * 
     * @return verdadero si la pieza pudo ser rotada falso en caso contrariou
     */
    @Override
    public boolean rotarUp() {
        Tablero referencia = Tablero.obtenerPartida();
        Bloque[][] map = this.map();
        Bloque[][] rot;
        boolean rotacionExitosa;

        this.borrarEstela();

        rot = new Bloque[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                rot[i][j] = new Bloque(map[2 - j][i].obtenerColor(), map[i][j].fila(), map[i][j].columna());
            }
        }
        rotacionExitosa = movimientoLegal(rot);

        if (!rotacionExitosa) {
            if (!this.moverIzq()) {
                this.moverDer();
                rotacionExitosa = false;
            }
        }

        tetronimo = rotacionExitosa ? rot : tetronimo;
        referencia.dibujarTetronimo(this);
        return rotacionExitosa;
    }

    /**
     * Metodo que rota la pieza a la izquierda cuando se presiona la tecla Z.
     * 
     * @return verdadero si la pieza pudo ser rotada falso en caso contrario
     */
    @Override
    public boolean rotarZ() {
        Tablero referencia = Tablero.obtenerPartida();
        Bloque[][] map;
        Bloque[][] rot;
        boolean rotacionExitosa;

        this.borrarEstela();

        map = this.map();
        rot = new Bloque[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                rot[i][j] = new Bloque(map[j][2 - i].obtenerColor(), map[i][j].fila(), map[i][j].columna());
            }
        }
        rotacionExitosa = movimientoLegal(rot);
        if (!rotacionExitosa) {
            if (!this.moverDer()) {
                this.moverIzq();
            }
            rotacionExitosa = false;
        }

        tetronimo = rotacionExitosa ? rot : tetronimo;
        referencia.dibujarTetronimo(this);
        return rotacionExitosa;
    }

}