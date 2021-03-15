package tetris.tetronimos;

/**
 * Clase de los tetronimos de tipo T
 */
public class T extends JLSTZ {
    /**
     * Crea un tetronimo de tipo T en la posicion del tablero desde donde comienza a
     * caer
     */
    public T() {
        super(new Bloque[][] {
                { new Bloque(Color.VACIO, 22, 4), new Bloque(Color.MAGENTA, 22, 5), new Bloque(Color.VACIO, 22, 6) },
                { new Bloque(Color.MAGENTA, 21, 4), new Bloque(Color.MAGENTA, 21, 5),
                        new Bloque(Color.MAGENTA, 21, 6) },
                { new Bloque(Color.VACIO, 20, 4), new Bloque(Color.VACIO, 20, 5), new Bloque(Color.VACIO, 20, 6) } });
    }

}