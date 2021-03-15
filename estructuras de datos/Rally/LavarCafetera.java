
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Calendar;

public class LavarCafetera {

    private static class Becario {
        private String nombre;
        private int dia_Lavado;

        public Becario(String n, int dia) {
            nombre = n;
            dia_Lavado = dia >= 0 && dia < 7 ? dia : dia % 7;
        }

        public String obtenerNombre() {
            return nombre;
        }

        public int obtenerDia() {
            return dia_Lavado;
        }

    }

    public static void main(String[] args) {

        int op = 0;

        do {
            op = menu();
            switch (op) {
                case 1:
                    porDefecto();
                    break;
                case 2:
                    personalizado();
                    break;
                case 3:
                    System.out.println("Hasta pronto! ,  manten tu cafetera limpia");
                    break;
                default:
                    break;
            }
        } while (op != 3);
    }
    /**
     * Realiza el inciso a
     */
    private static void personalizado() {
        Lista<Becario> becarios = obtenerListaBecarios();
        int semanas;
        System.out.println("Por cuantas semanas?");
        semanas = leerInt();
        semanas = semanas < 0 ? -1 * semanas : semanas;

        imprimirHorario(crearRolLavado(becarios), semanas);
    }
    /**
     * Modificacion del programa
     */
    private static void porDefecto() {
        Lista<Becario> becarios = new Lista<>();
        becarios.agregarAlFinal​(new Becario("Ricardo", 2));
        becarios.agregarAlFinal​(new Becario("Alejandro", 2));
        becarios.agregarAlFinal​(new Becario("Nestaly", 2));
        becarios.agregarAlFinal​(new Becario("Alma", 2));

        imprimirHorario(crearRolLavado(becarios), 16);
    }

    public static int menu() {
        System.out.println(
                "\n\nCreador de rutinas de limpieza \n\topcion: \n1) Esta opcion es solo para ustedes Ricardo,Alejandro , Nestaly y Alma");
        System.out.println("2) Personalizado , elige los dias, tus compañeros y el orden \n3) Salir");
        return leerOpcion(3);

    }
    /**
     * Avanza un dia en el calendario
     */
    private static Calendar avazarDias(Calendar fecha) {
        fecha.roll(Calendar.DATE, true);
        if (fecha.get(Calendar.DATE) == 1) {
            fecha.roll(Calendar.MONTH, true);
            if (fecha.get(Calendar.MONTH) == Calendar.DECEMBER) {

                fecha.roll(Calendar.YEAR, true);
            }

        }
        return fecha;
    }
    /**
     * Imprime como se debe lavar la cafetera
     */
    private static void imprimirHorario(ListaCircular<ListaCircular<Becario>> rol, int semanas) {
        Iterator<ListaCircular<Becario>> cicloInterno = rol.iterator();
        Iterator<Becario> cicloExterno;
        ListaCircular dia;
        Becario becario;
        Calendar fecha = Calendar.getInstance();

        // Acomodar el iterador en el inicio
        for (int i = 1; i < rol.getTamanio(); i++) {
            dia = cicloInterno.next();
        }

        // obtengo a apartir de que dia se acomoda la lista
        do {

            dia = cicloInterno.next();

            cicloExterno = dia.iterator();

            becario = cicloExterno.next();
            // acomodar de regreso
            for (int i = 1; i < dia.getTamanio(); i++) {
                Becario aux = cicloExterno.next();
            }
        } while (becario.obtenerDia() > fecha.get(Calendar.DAY_OF_WEEK));

        // comezar con la salida

        for (int i = 0; i < semanas; i++) { // semanas del horario

            for (int j = 0; j < rol.getTamanio(); j++) {

                // acomodar iterador externo
                for (int m = 1; m < dia.getTamanio() + i; m++) {
                    Becario aux = cicloExterno.next();
                }

                becario = cicloExterno.next();
                while (fecha.get(Calendar.DAY_OF_WEEK) != becario.obtenerDia()) {
                    fecha = avazarDias(fecha);
                }

                System.out.println("El " + fecha.get(Calendar.DATE) + "/" + (fecha.get(Calendar.MONTH) + 1) + "/"
                        + fecha.get(Calendar.YEAR) + "\t  le toca limpiar la cafera a \t" + becario.obtenerNombre());
                fecha = avazarDias(fecha);
                dia = cicloInterno.next();
                cicloExterno = dia.iterator();
            }
        }

    }

    /**
     * Crea un rol de lavada a partir de un arreglo de becarios que contienen su
     * nombre y dia que lavan la cafetera
     * 
     * @param becarios
     * @return Lista circular que contiene las rotaciones para lavar la cafetera
     */

    private static ListaCircular<ListaCircular<Becario>> crearRolLavado(Lista<Becario> becarios) {

        ListaCircular<Becario> rolSemanal[] = new ListaCircular[7];
        ListaCircular<ListaCircular<Becario>> rol = new ListaCircular<>();

        for (Becario e : becarios) {
            // agrega los becarios en el dia de la semana que le corresponde
            if (rolSemanal[e.obtenerDia()] == null) {
                rolSemanal[e.obtenerDia()] = new ListaCircular<>();
            }
            rolSemanal[e.obtenerDia()].agrega(e);
        }
        for (int i = 0; i < rolSemanal.length; i++) {
            if (rolSemanal[i] != null) {
                rol.agrega(rolSemanal[i]);
            }
        }

        return rol;

    }

    /**
     * 
     * Lee por consola becarios y el dia en que limpian
     * 
     * @return Una lista con todos los becarios y el dia que limpian
     */
    private static Lista<Becario> obtenerListaBecarios() {
        Lista<Becario> becarios = new Lista<>();
        String name, continuar;
        int dia;
        boolean exit = false;

        System.out.println("A continuacion digita los becarios que quieres agregar");

        while (becarios.esVacia() || !exit) {
            System.out.println("Digita el nombre del becario");
            name = leerCadena();
            System.out.println("Que dia de la semana limpia " + name + "?");
            System.out.println("1)Domingo \n2)Lunes \n3)Martes \n4)Miercoles \n5)Jueves \n6)Viernes \n7)Sabado");
            do {
                dia = leerInt();
                if ((dia < 1 || dia > 7)) {
                    System.out.println(
                            "Hey!, las semanas en la tierra solo tienen 7 dias, parece que necesitas una taza de cafe\nintentalo de nuevo");
                }

            } while (dia < 1 || dia > 7);
            Becario aux = new Becario(name, dia);

            becarios.agregarAlFinal​(aux);
            System.out.println("Desea agregar otro? s/n");
            do {
                continuar = leerCadena();
                continuar = continuar.toLowerCase();
                if (!continuar.equals("s") && !continuar.equals("n")) {
                    System.out.println("no se que quieres que haga");
                } else if (continuar.equals("n")) {
                    exit = true;
                }
            } while (!continuar.equals("s") && !continuar.equals("n"));

        }

        return becarios;

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
        int op = -2;
        String entrada = "";
        Scanner in = new Scanner(System.in);

        System.out.print(" > ");
        entrada = in.nextLine();
        try {
            op = Integer.parseInt(entrada);

        } catch (NumberFormatException e) {

            System.out.println("**numero no valido**");
            op = leerInt();
        }
        return op;

    }

    private static String leerCadena() {
        String entrada = "";
        Scanner in = new Scanner(System.in);

        System.out.print(" > ");
        entrada = in.nextLine();

        return entrada;

    }
}