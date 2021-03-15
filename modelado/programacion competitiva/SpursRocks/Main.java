import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Del Moral Morales Francisco Emmanuel
 * 420003162
 */
public class Main {

    public static void main(String[] args) {
        try {
            BufferedWriter log = new BufferedWriter(new OutputStreamWriter(System.out));
            BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
            boolean exit = false;
            int numero_equipos, numero_de_juegos, instancia = 1;
            StringTokenizer linea;

            do {
                linea = new StringTokenizer(lector.readLine()); // se lee cuantos equipos participan
                numero_equipos = Integer.parseInt(linea.nextToken());
                exit = numero_equipos == 0;

                if (!exit) {
                    if (instancia != 1)
                        log.write("\n");

                    numero_de_juegos = (int) ((Math.pow(numero_equipos, 2) - numero_equipos) / 2); // cuantos partidos
                                                                                                   // hubo

                    Equipo[] equipos = new Equipo[numero_equipos];
                    for (int n = 0; n < equipos.length; n++) {
                        equipos[n] = new Equipo(n + 1);
                    }
                    int equipo1, equipo2, score1, score2;

                    for (int i = 0; i < numero_de_juegos; i++) { // obtener info de cada partido
                        linea = new StringTokenizer(lector.readLine());
                        equipo1 = Integer.parseInt(linea.nextToken());
                        score1 = Integer.parseInt(linea.nextToken());
                        equipo2 = Integer.parseInt(linea.nextToken());
                        score2 = Integer.parseInt(linea.nextToken());

                        equipos[equipo1 - 1].enfrentamiento(equipos[equipo2 - 1], score1, score2);
                    }
                    log.write("Instancia " + (instancia++) + "\n");
                    log.write(obtenerRanking(equipos) + "\n");
                }

            } while (!exit);
            log.flush();
        } catch (Exception e) {
            System.out.println("Un error en la lectura de datos a ocurrido: \n");
            e.printStackTrace();
        }
    }

    public static class Equipo implements Comparable<Equipo> {
        private int numeroEquipo, score, canastasAnotadas, canastasEnContra;

        /**
         * Inicializa un equipo con un numero identificador
         * 
         * @param n
         */
        public Equipo(int n) {
            numeroEquipo = n;
        }

        /**
         * Metodo que informa el numero de equipo correspondiente a este objeto
         * 
         * @return numero de equipo
         */
        public int numeroEquipo() {
            return numeroEquipo;
        }

        /**
         * Metodo que enfrenta dos equipos y asigna las puntuaciones correspondientes +2
         * pt ganador +1 perdedor
         * 
         * @param rival        equipo al que se enfrento
         * @param puntosFavor  puntos anotados
         * @param puntosContra puntos que anoto el rival
         */
        public void enfrentamiento(Equipo rival, int puntosFavor, int puntosContra) {
            this.canastasAnotadas += puntosFavor;
            this.canastasEnContra += puntosContra;

            rival.canastasAnotadas += puntosContra;
            rival.canastasEnContra += puntosFavor;

            if (puntosFavor > puntosContra)
                actualizarScore(this, rival);
            else
                actualizarScore(rival, this);

        }

        @Override
        public int compareTo(Equipo o) {

            if (this.score != o.score) {
                return this.score > o.score ? 1 : -1;
            }
            double thisCanastaPromedio = this.canastaPromedio();
            double oCanastaPromedio = o.canastaPromedio();

            if (thisCanastaPromedio != oCanastaPromedio) {
                return thisCanastaPromedio > oCanastaPromedio ? 1 : -1;
            }
            if (this.canastasAnotadas != o.canastasAnotadas) {
                return this.score > o.score ? 1 : -1;
            }

            return this.numeroEquipo < o.numeroEquipo ? 1 : -1;
        }
        /**
         * Metodo que obtiene el valor de la canasta promedio del equipo, calculado como el numero de canastas anotadas entre las canastas en contra
         * @return valor de la canasta promedio
         */
        public double canastaPromedio() {
            return Double.valueOf(canastasAnotadas)
                    / Double.valueOf(this.canastasEnContra == 0 ? 1 : this.canastasEnContra);
        }

        private void actualizarScore(Equipo g, Equipo p) {
            g.score += 2;
            p.score += 1;
        }

    }

    /**
     * Metodo que obtiene el ranking de equipos
     * 
     * @param arreglo grupo con los equipos que participaron y sus puntuaciones
     */
    public static String obtenerRanking(Equipo[] arreglo) {
        ordenar(arreglo, 0, arreglo.length - 1);
        StringBuilder salida = new StringBuilder(Integer.toString(arreglo[0].numeroEquipo()));
        for (int n = 1; n < arreglo.length; n++) {
            salida.append(" " + arreglo[n].numeroEquipo());
        }
        return salida.toString();
    }

    /**
     * Metodo que de verdad ordena el arreglo pasado por parametro usando quicksort
     * 
     * @param arreglo      arreglo a ordenar
     * @param indiceInicio pivote de inicio
     * @param indiceFinal  pivote de termino
     */
    private static void ordenar(Equipo[] arreglo, int indiceInicio, int indiceFinal) {

        if (indiceInicio < indiceFinal) {
            int i = indiceInicio + 1;
            int j = indiceFinal;

            while (i < j) {
                if (arreglo[i].compareTo(arreglo[indiceInicio]) < 0
                        && arreglo[j].compareTo(arreglo[indiceInicio]) >= 0) {
                    intercambiar(arreglo, i, j);
                    i++;
                    j--;
                } else if (arreglo[i].compareTo(arreglo[indiceInicio]) >= 0) {
                    i++;
                } else {
                    j--;
                }
            }
            if (arreglo[i].compareTo(arreglo[indiceInicio]) < 0) {
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
    private static void intercambiar(Equipo[] arreglo, int a, int b) {
        Equipo aux = arreglo[a];
        arreglo[a] = arreglo[b];
        arreglo[b] = aux;
    }

}