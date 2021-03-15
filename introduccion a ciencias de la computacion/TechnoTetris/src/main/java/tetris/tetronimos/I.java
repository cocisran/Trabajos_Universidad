package tetris.tetronimos;

import tetris.Tablero;

/**
 * Clase de los tetronimos de tipo I
 */
public class I extends Tetronimo {

    public I() {
        super(crear());
    }

    /**
     * Auxiliar del constructor
     * 
     * @return matriz de bloques que conforman el tetronimo
     */
    private static Bloque[][] crear() {
        Bloque[][] aux = new Bloque[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == 2)
                    aux[i][j] = new Bloque(Color.CYAN, 23 - i, 3 + j);
                else
                    aux[i][j] = new Bloque(Color.VACIO, 23 - i, 3 + j);
            }
        }
        return aux;
    }

    /**
     * Metodo que rota la pieza a la derecha cuando se presiona la tecla sup de las
     * flechas.
     * 
     * @return verdadero si la pieza pudo ser rotada falso en caso contrariou
     */
    @Override
    public boolean rotarUp() {
        Bloque[][] map = this.map();
        Bloque[][] rot;
        Tablero referencia = Tablero.obtenerPartida();

        boolean rotacionExitosa;

        this.borrarEstela();

        rot = new Bloque[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                rot[i][j] = new Bloque(map[3 - j][i].obtenerColor(), map[i][j].fila(), map[i][j].columna());
            }
        }
        rotacionExitosa = movimientoLegal(rot);
        if (!rotacionExitosa) {
            if (!this.moverIzq()) {
                if (map[0][3].columna() <= 10) {
                    this.moverDer();
                }
                rotacionExitosa = false;
            } else {
                if (map[0][0].columna() <= 10) {
                    this.moverIzq();

                }
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
        Bloque[][] map = this.map();
        Bloque[][] rot;
        Tablero referencia = Tablero.obtenerPartida();

        boolean rotacionExitosa;

        this.borrarEstela();

        rot = new Bloque[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                rot[i][j] = new Bloque(map[j][3 - i].obtenerColor(), map[i][j].fila(), map[i][j].columna());
            }
        }
        rotacionExitosa = movimientoLegal(rot);
        if (!rotacionExitosa) {
            if (!this.moverIzq()) {
                if (map[0][0].columna() < 0) {
                    this.moverDer();
                }
                rotacionExitosa = false;
            } else {
                if (map[0][0].columna() < 0) {
                    this.moverIzq();
                }
                rotacionExitosa = false;
            }

        }

        tetronimo = rotacionExitosa ? rot : tetronimo;
        referencia.dibujarTetronimo(this);
        return rotacionExitosa;
    }

}