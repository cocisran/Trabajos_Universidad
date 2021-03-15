import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Del Moral Morales Francisco Emmanuel
 */
public class Main {

    private static int n, m;
    private static int[][] matriz;

    public static void main(String[] args) {
        try {
            BufferedWriter log = new BufferedWriter(new OutputStreamWriter(System.out));
            BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
            boolean exit = false;

            StringTokenizer fuente;
            do {
                fuente = new StringTokenizer(lector.readLine());
                n = Integer.parseInt(fuente.nextToken());
                m = Integer.parseInt(fuente.nextToken());
                int costoMataplagas = Integer.parseInt(fuente.nextToken()); // primera linea
                exit = (n + m + costoMataplagas == 0);

                if (!exit) {
                    int q = Integer.parseInt(lector.readLine());
                    matriz = new int[n][m]; // inicia matriz
                    for (int i = 0; i < q; i++) {

                        fuente = new StringTokenizer(lector.readLine());
                        if (fuente.nextToken().equals("A")) {
                            int n = Integer.parseInt(fuente.nextToken());
                            int x = Integer.parseInt(fuente.nextToken());
                            int y = Integer.parseInt(fuente.nextToken());
                            actualizar(x, y, n);
                        } else {
                            // hora de sacar la cartera
                            int x = Integer.parseInt(fuente.nextToken());
                            int y = Integer.parseInt(fuente.nextToken());
                            int xf = Integer.parseInt(fuente.nextToken());
                            int yf = Integer.parseInt(fuente.nextToken());
                            int comprar = sumaArea(x, y, xf, yf) * costoMataplagas;
                            log.write(comprar + "\n");
                        }
                    }
                    log.write("\n");
                    log.flush();
                }
            } while (!exit);
        } catch (Exception e) {
            System.out.println("Un error en la lectura de datos a ocurrido: \n");
            e.printStackTrace();
        }

    }

    // matriz metodos
    /*
     * Se uso unaa matriz basada en un Arbol Binario indexado
     */

    /**
     * Suma acumulada del area de una seccion desde el origen hata la coordenada
     * dada
     * 
     * @param xi valor en x
     * @param yi valor en y
     * @return valor equivalente a la suma acumulada del area delimitada
     */
    public static int sumaSeccion(int xi, int yi) {
        int acumulador = 0;

        for (int j = yi + 1; j > 0; j -= (j & -j)) {
            for (int k = xi + 1; k > 0; k -= (k & -k)) {
                acumulador += matriz[k - 1][j - 1];
            }
        }

        return acumulador;
    }

    /**
     * Obtiene el valor acumulado de un area delimitada entre dos puntos
     * 
     * @param x1 valor en x del punto 1
     * @param y1 valor en y del punto 1
     * @param x2 valor en x del punto 2
     * @param y2 valor en y del punto 2
     * @return valor equivalente a la suma acumulada del area delimitada
     */
    public static int sumaArea(int x1, int y1, int x2, int y2) {
        int aux = 0;
        if (x1 > x2) {
            aux = x1;
            x1 = x2;
            x2 = aux;
        }
        // de esta forma siempre x1 < x2 y y1 < y2
        if (y1 > y2) {
            aux = y1;
            y1 = y2;
            y2 = aux;
        }

        return sumaSeccion(x2, y2) - sumaSeccion(x2, y1 - 1) - sumaSeccion(x1 - 1, y2) + sumaSeccion(x1 - 1, y1 - 1);
    }
    /**
     * Actualiza el valor de una casilla en la matriz
     * @param x valor en x de la casilla
     * @param y valor en y de la casilla
     * @param valor valor a actualizar
     */
    public static void actualizar(int x, int y, int valor) {

        for (int j = y + 1; j < m + 1; j += (j & -j)) {
            for (int k = x + 1; k < n + 1; k += (k & -k)) {
                matriz[k - 1][j - 1] += valor;
            }
        }
    }

}