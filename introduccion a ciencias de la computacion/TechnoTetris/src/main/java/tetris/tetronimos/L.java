package tetris.tetronimos;

/**
 * Clase de los tetronimos de tipo L
 */
public class L extends JLSTZ {
    /**
     * Crea un tetronimo de tipo L en la posicion del tablero desde donde comienza a
     * caer
     */
    public L() {
        super(new Bloque[][] {
                { new Bloque(Color.VACIO, 22, 4), new Bloque(Color.VACIO, 22, 5), new Bloque(Color.NARANJA, 22, 6) },
                { new Bloque(Color.NARANJA, 21, 4), new Bloque(Color.NARANJA, 21, 5),
                        new Bloque(Color.NARANJA, 21, 6) },
                { new Bloque(Color.VACIO, 20, 4), new Bloque(Color.VACIO, 20, 5), new Bloque(Color.VACIO, 20, 6) } });
    }

}