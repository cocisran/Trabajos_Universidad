package tetris.tetronimos;

/**
 * Clase de los tetronimos de tipo J
 */
public class J extends JLSTZ {
    /**
     * Crea un tetronimo de tipo J en la posicion del tablero desde donde comienza a
     * caer
     */
    public J() {
        super(new Bloque[][] {
                { new Bloque(Color.AZUL, 22, 4), new Bloque(Color.VACIO, 22, 5), new Bloque(Color.VACIO, 22, 6) },
                { new Bloque(Color.AZUL, 21, 4), new Bloque(Color.AZUL, 21, 5), new Bloque(Color.AZUL, 21, 6) },
                { new Bloque(Color.VACIO, 20, 4), new Bloque(Color.VACIO, 20, 5), new Bloque(Color.VACIO, 20, 6) } });
    }

}