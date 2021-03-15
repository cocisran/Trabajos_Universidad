package tetris.tetronimos;

/**
 * Clase de los tetronimos de tipo S
 */
public class S extends JLSTZ {
    /**
     * Crea un tetronimo de tipo S en la posicion del tablero desde donde comienza a
     * caer
     */
    public S() {
        super(new Bloque[][] {
                { new Bloque(Color.VACIO, 23, 4), new Bloque(Color.VACIO, 23, 5), new Bloque(Color.VACIO, 23, 6) },
                { new Bloque(Color.VACIO, 22, 4), new Bloque(Color.VERDE, 22, 5), new Bloque(Color.VERDE, 22, 6) },
                { new Bloque(Color.VERDE, 21, 4), new Bloque(Color.VERDE, 21, 5), new Bloque(Color.VACIO, 21, 6) } });
    }

}