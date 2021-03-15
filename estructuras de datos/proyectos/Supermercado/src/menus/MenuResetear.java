package menus;

import almacen.*;
import funcionalidades.Escritor;
import funcionalidades.Lector;
import java.util.Scanner;

/**
 * Clase que permite el manejo de la opcion Ajustes del menu principal
 */
public class MenuResetear {
    private static boolean resetear;

    /**
     * Menu principal de la opcion Ajustes
     */
    public static void main() {
        resetear = false;
        int op = 0;
        Scanner in = new Scanner(System.in);
        do {
            System.out.println("\u001B[43m" + "**Ajustes del programa*" + "\u001B[0m");
            System.out.println("1) Reiniciar  programa \n2) VOLVER");
            System.out.print("opcion: \n>");
            op = Lector.stringToInt(in.nextLine());
            System.out.print("\033\143");
            opciones(op);

        } while (op != 2);
    }

    /**
     * Informa que el programa fue reseteado
     */
    public static void informarReseteo() {
        resetear = false;
    }

    /**
     * Indica si el programaa debe ser iniciado de nuevo o no
     * 
     * @return true si tiene que volverse aa iniciar, false en caso contrario
     */
    public static boolean resetear() {
        return resetear;
    }

    private static void opciones(int op) {
        switch (op) {

            case 1:
                reiniciarPrograma();
                break;
            case 2:
                System.out.println("Volviendo al menu  principal");
                break;
            default:
                System.out.println("No te he entendido, intenta de nuevo");

        }
    }

    private static void reiniciarPrograma() {
        System.out.println("\u001B[43m" + "**ATENCION:**" + "\u001B[0m");
        System.out.println(
                "Una finalizacion abrupta del programa pudo producir errores o archivos incompletos, si tiene problema para ejecutar el programa\n"
                        + "Ejecute esta opcion tomando en cuenta que todos los datos generador hasta el momento se perderan\n");
        if (continuar()) {
            resetear = true;
            System.out.println("Regresara al menu principal");
            resetearCajas();
            resetearGnuplot();
            resetearControl();
        }
        System.out.print("\033\143");

    }

    private static boolean continuar() {
        int op = 0;
        Scanner in = new Scanner(System.in);
        do {
            System.out.println("Desea continuar?\n" + "\u001B[42m" + "[1] si" + "\u001B[0m" + "\n" + "\u001B[41m"
                    + "[2] no" + "\u001B[0m");
            System.out.print("opcion: \n>");
            op = Lector.stringToInt(in.nextLine());
        } while (op != 1 && op != 2);
        return op == 1;

    }

    private static void resetearCajas() {
        for (int i = 1; i <= 15; i++) {
            Escritor.sobrescribirStringEnTxt("files/registroCajas/caja_" + i + ".txt");
        }
    }

    private static void resetearGnuplot() {
        for (int i = 1; i <= 14; i++) {
            Escritor.sobrescribirStringEnTxt("files/gnuplot_data/brute/info_" + i + ".txt");
            Escritor.sobrescribirStringEnTxt("files/gnuplot_data/media/media_" + i + "cajaRapida.txt");
        }
    }

    private static void resetearControl() {
        Escritor.sobrescribirStringEnTxt("systemFiles/control/controlEstado.dat");
    }
}