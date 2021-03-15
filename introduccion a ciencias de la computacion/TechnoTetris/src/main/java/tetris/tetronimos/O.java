package tetris.tetronimos;

/**
 * Clase de los tetronimos de tipo O
 */
public class O extends Tetronimo {
    /**
     * Crea un tetronimo de tipo O en la posicion del tablero desde donde comienza a
     * caer
     */
    public O() {
        super(new Bloque[][] { { new Bloque(Color.AMARILLO, 21, 4), new Bloque(Color.AMARILLO, 21, 5) },
                { new Bloque(Color.AMARILLO, 20, 4), new Bloque(Color.AMARILLO, 20, 5) } });
    }

    /**
     * como el tetronimo no rota, su rotacion siempre es verdadera
     */
    @Override
    public boolean rotarUp() {
        return true;
    }

    /**
     * como el tetronimo no rota, su rotacion siempre es verdadera
     */
    @Override
    public boolean rotarZ() {
        return true;
    }

}