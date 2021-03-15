package tetris.tetronimos;

/**
 * Clase de los tetronimos de tipo Z
 */
public class Z extends JLSTZ {
    /**
     * Crea un tetronimo de tipo Z en la posicion del tablero desde donde comienza a
     * caer
     */
    public Z() {
        super(new Bloque[][] {
                { new Bloque(Color.VACIO, 23, 4), new Bloque(Color.VACIO, 23, 5), new Bloque(Color.VACIO, 23, 6) },
                { new Bloque(Color.ROJO, 22, 4), new Bloque(Color.ROJO, 22, 5), new Bloque(Color.VACIO, 22, 6) },
                { new Bloque(Color.VACIO, 21, 4), new Bloque(Color.ROJO, 21, 5), new Bloque(Color.ROJO, 21, 6) } });
    }

}