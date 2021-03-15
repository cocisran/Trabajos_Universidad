
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class Josephus {

    public static void main(String[] args) {
        ListaCircular<Soldado> tropa = new ListaCircular<>();
        int op = -1, n = 0, s = 0;
        do {
            op = menu();
            switch (op) {
                case 1:
                    n = 40;
                    s = 3;
                    break;
                case 2:
                    System.out.println("Digita el numero de soldados ");
                    n = leerInt();
                    n = n < 0 ? -1 * n : n;
                    s = new Random().nextInt(n);
                    System.out.println("En esta simulacion hay : " + n + " soldados");
                    break;
                case 3:
                    System.out.println("Recuerda ver donde te sientas, Adios! :D");
                    break;
                default:
                    break;
            }
            if (op == 1 || op == 2) {
                tropa = formarseEnCirculo(n);
                Soldado sobreviviente = purga(tropa, s);
                System.out.println("\nCuando hay " + n + " soldados en este juego macabro, y se saltan de " + s + " en "
                        + s + " esperemos te hayas sentando en el lugar " + sobreviviente.obtenerLugar()
                        + ", que afortunado fue " + sobreviviente.obternerNombre() + " en esta ocasión ");
            }
        } while (op != 3);

    }

    /**
     * Imprime el menu principal
     */
    private static int menu() {
        System.out.println(
                "\n\n\nJosephus Simulator  \n\topcion: \n1) TRADCIONAL el clasico modo inspirado en una anecdota historica");
        System.out.println("2) Arcade , tu elige cuantos soldados tiene tu ejercito \n3) Salir");
        return leerOpcion(3);

    }

    /**
     * Crea un circulo con las tropas en la cueva
     * 
     * @param numeroTropas numero de soldados en la cueva
     * @return lista circulas con los soldados en la cueva
     */
    private static ListaCircular<Soldado> formarseEnCirculo(int numeroTropas) {
        ListaCircular<Soldado> tropa = new ListaCircular<>();
        RandomName nombre = new RandomName();
        tropa.agrega(new Soldado(numeroTropas, nombre.nextName()));
        for (int i = 1; i < numeroTropas; i++) {
            tropa.agrega(new Soldado(i, nombre.nextName()));
        }
        return tropa;
    }

    /**
     * Clase que realiza el horrible acto de la ejecucion de los soldados, en los saltos dados  
     */    
    private static Soldado purga(ListaCircular<Soldado> tropa, int salto) {
        Iterator<Soldado> pato = tropa.iterator();
        Soldado turno;
        int i = 1;

        System.out.println("\n\n\t*****Registro de orden de muertes***\n\nLugar \t Nombre");
        while (tropa.getTamanio() > 1) {
            turno = pato.next();
            if (i % (salto + 1) == 0) {
                System.out.println(turno.posicion + ") \t" + turno.obternerNombre());
                pato.remove();
                i++;
            }
            i++;
        }

        return pato.next();

    }

    /**
     * Clase que modelo los soldados de la cueva
     */
    private static class Soldado {
        private int posicion;
        private String nombre;

        public Soldado(int lugar, String name) {
            posicion = lugar;
            nombre = name;
        }

        public int obtenerLugar() {
            return posicion;
        }

        public String obternerNombre() {
            return nombre;
        }

        public String toString() {
            return "" + posicion;
        }

    }

    private static int leerOpcion(int sup) {
        int op = -2;
        String entrada = "";
        Scanner in = new Scanner(System.in);

        System.out.print("opción > ");
        entrada = in.nextLine();
        try {
            op = Integer.parseInt(entrada);
            if (op < 1 || op > sup) {
                System.out.println("**La opcion no es valida**");
                op = -2;
            }
        } catch (NumberFormatException e) {
            System.out.println("**La opcion no es valida**");
            op = leerOpcion(sup);
        }
        return op;

    }

    private static int leerInt() {
        int op = 0;
        String entrada = "";
        Scanner in = new Scanner(System.in);

        System.out.print("valor > ");
        entrada = in.nextLine();
        try {
            op = Integer.parseInt(entrada.replaceAll(" ", ""));

        } catch (NumberFormatException e) {

            System.out.println("**numero no valido**");
            op = leerInt();
        }
        return op;

    }

}
