package tetris.juego;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import tetris.tetronimos.*;

/**
 * Crea un objeto que contiene una lista con con los 7 tetronimos ordenados
 * aleatoriamente
 */
public class Bolsa {

    private LinkedList<Tetronimo> bolsa;

    /**
     * Crea un objeto bolsa que contiene los 7 tetronimos del juego en orden
     * aleatorio
     */
    public Bolsa() {

        bolsa = crear();
    }

    /**
     * Retira el primer elemento dentro de la bolsa
     * 
     * @return tetronimo a generar
     */
    public Tetronimo sacar() {
        return bolsa.poll();
    }

    /**
     * Muestra el siguiente tetronimo que caera, si la bolsa se termino genera una
     * nueva
     * 
     * @return siguiente tetronimo a caer
     */
    public Tetronimo siguiente() {

        try {
            return bolsa.getFirst();
        } catch (Exception e) {
            this.rellenar();
            return bolsa.getFirst();
        }
    }

    /**
     * Rellena la bolsa con todos los tetronimos en orden aleatorio
     */
    public void rellenar() {
        bolsa.addAll(crear());
    }

    private static LinkedList<Tetronimo> crear() {
        int indice;
        Random eleccion = new Random();
        LinkedList<Tetronimo> nuevaBolsa = new LinkedList<Tetronimo>();
        LinkedList<Tetronimo> fuente = new LinkedList<Tetronimo>(
                Arrays.asList(new I(), new J(), new L(), new O(), new S(), new T(), new Z()));
        int elementosFuente;

        do {
            elementosFuente = fuente.size();
            indice = eleccion.nextInt(10 * elementosFuente) % elementosFuente;
            nuevaBolsa.add(fuente.get(indice));
            fuente.remove(indice);
        } while (elementosFuente > 1);

        return nuevaBolsa;
    }
}
