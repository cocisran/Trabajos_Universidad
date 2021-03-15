package tetris.tetronimos;

/**
 * Clase que crea los bloques de los cuales se forman los Tetronimos.
 */
public class Bloque {

    private Color color;
    private int fila;
    private int columna;

    /**
     * Crea un objeto de tipo bloque.
     * 
     * @param c Color del bloque.
     * @param m Fila en la que se ubica el bloque.
     * @param n Columna en la que se ubica el bloque.
     */
    public Bloque(Color c, int m, int n) {
        color = c;
        fila = m;
        columna = n;
    }
    /**
     * Crea un nuevo bloque a partir de otro bloque existente
     * 
     * @param e bloque a copiar
     */
    public Bloque(Bloque e) {
        this.color = e.color;
        this.fila = e.fila;
        this.columna = e.columna;
    }

    /**
     * Retorna la fila en la que se posiciona el bloque
     * 
     * @return fila donde se ubica el bloque
     */
    public int fila() {
        return fila;
    }

    /**
     * Retorna la columna en la que se posiciona el bloque
     * 
     * @return columna donde se ubica el bloque
     */
    public int columna() {
        return columna;
    }

    /**
     * Obtiene el color del bloque.
     * 
     * @return Elemento de la lista Color referente al color del bloque.
     */
    public Color obtenerColor() {
        return color;
    }

    /**
     * Modifica los valores de la poscion del bloque
     * 
     * @param m Nuevo valor de la fila.
     * @param n Nuevo valor de la columna.
     */
    public void cambiarPosicion(int m, int n) {
        fila = m;
        columna = n;
    }

    /**
     * Cambia el color del bloque
     * 
     * @param c nuevo color del bloque
     */
    public void cambiarColor(Color c) {
        color = c;
    }

    @Override
    public String toString() {
       /* if (color == Color.VACIO) {
            return "[ ]";
        } else {
            return " o ";
        }*/
         return "("+fila+","+columna+"):"+color+" |";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Bloque otra = (Bloque) obj;
        return this.fila == otra.fila && this.columna == otra.columna && this.color == otra.color;
    }
}