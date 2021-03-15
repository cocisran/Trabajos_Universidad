import java.io.IOException;
import java.util.Scanner;
/**
 * @author Del Moral Morales Francisco Emmanuel
 * Clase diseñada para enseñarle a Meena quien manda B)
 */
public class Main {

    public static void main(String[] args) {
        boolean exit = false;
        Scanner lector = new Scanner(System.in);
        int caso = 1;

        do {

            int numeroDeCanicas = lector.nextInt();
            int numeroDePreguntas = lector.nextInt();

            if (numeroDeCanicas == 0 && numeroDePreguntas == 0) {
                exit = true;
            }

            if (!exit) {
                int[] canicas = null;

                if (numeroDeCanicas > 0) {
                    canicas = new int[numeroDeCanicas];
                    for (int i = 0; i < numeroDeCanicas; i++) {
                        canicas[i] = lector.nextInt(); // leemos las canicas
                    }
                    quickSort(canicas); // ordenamos canicas
                }
                System.out.println("CASE# " + caso + ":");
                caso++;
                for (int j = 0; j < numeroDePreguntas; j++) { // hacemos preguntas
                    int pregunta = lector.nextInt();
                    int indice = buscar(canicas, pregunta);

                    if (indice != -1)
                        System.out.println(pregunta + " found at " + (indice + 1));
                    else
                        System.out.println(pregunta + " not found");
                }
            }

        } while (!exit);
    }

    /**
     * Realiza la busqueda de un elemento en el arreglo pasado por parametro
     * 
     * @param arreglo  arreglo de enteros donde buscar *El arreglo debe estar
     *                 ordenado de forma ascendente
     * @param busqueda elemento a buscar
     * @return indice donde se encuentra el elemento o -1 si no se encuentra
     */
    public static int buscar(int[] arreglo, int busqueda) {
        int indice = -1;
        int inicio = 0;
        int fin = arreglo.length - 1;

        while ((inicio <= fin)) {

            int medio = (inicio + fin) / 2;

            if (arreglo[medio] == busqueda) {

                indice = medio;

            }

            if (arreglo[medio] < busqueda) {

                inicio = medio + 1;

            } else {

                fin = medio - 1;
            }
        }
        return indice;
    }

    /**
     * Metodo que ordena de forma ascendente un arreglo usando quickSort
     * 
     * @param arreglo
     */
    public static void quickSort(int[] arreglo) {
        ordenar(arreglo, 0, arreglo.length - 1);
    }

    /**
     * Metodo que de verdad ordena el arreglo pasado por parametro
     * 
     * @param arreglo      arreglo a ordenar
     * @param indiceInicio pivote de inicio
     * @param indiceFinal  pivote de termino
     */
    private static void ordenar(int[] arreglo, int indiceInicio, int indiceFinal) {

        if (indiceInicio < indiceFinal) {
            int i = indiceInicio + 1;
            int j = indiceFinal;

            while (i < j) {
                if (arreglo[i] > arreglo[indiceInicio] && arreglo[j] <= arreglo[indiceInicio]) {
                    intercambiar(arreglo, i, j);
                    i++;
                    j--;
                } else if (arreglo[i] <= arreglo[indiceInicio]) {
                    i++;
                } else {
                    j--;
                }
            }
            if (arreglo[i] > arreglo[indiceInicio]) {
                i = i - 1;
            }
            intercambiar(arreglo, indiceInicio, i);
            ordenar(arreglo, indiceInicio, i - 1);
            ordenar(arreglo, i + 1, indiceFinal);
        }

    }

    /**
     * Metodo que intercamnbia dos numeros dentro del arreglo pasado como parametro
     * 
     * @param arreglo arreglo donde se intercambiaran los valores
     * @param a       indice del elemento 1
     * @param b       indice del elemento 2
     */
    private static void intercambiar(int[] arreglo, int a, int b) {
        int aux = arreglo[a];
        arreglo[a] = arreglo[b];
        arreglo[b] = aux;
    }

}