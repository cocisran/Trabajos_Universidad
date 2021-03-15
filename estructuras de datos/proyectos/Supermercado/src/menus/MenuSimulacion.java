package menus;

import funcionalidades.*;
import java.util.Scanner;
import supermercado.Simulacion;
import almacen.Inventario;
import gnuplotProcess.*;

/**
 * Menu que permite el manejo de la opcion Menu Simulacion del menu principal
 */
public class MenuSimulacion {

    Inventario inventario;

    /**
     * Constructor que recibe el almacen con el cual trabaja el programa
     * 
     * @param almacen
     */
    public MenuSimulacion(Inventario almacen) {
        if (almacen == null) {
            throw new NullPointerException("almacen no valido, volviendo al  menu principal");
        }
        this.inventario = almacen;

    }

    /**
     * Menu principal para el manejo de la opcion Menu simulacion
     */
    public void main() {
        int op = 0;
        Scanner in = new Scanner(System.in);
        do {
            System.out.println("\u001B[43m" + "**Menu de simulaciones**" + "\u001B[0m");
            System.out.println("1) Realizar simulacion optima \n2) Analisis completo   \n3) VOLVER");
            System.out.print("opcion: \n>");
            op = Lector.stringToInt(in.nextLine());
            System.out.print("\033\143");
            this.opciones(op);

        } while (op != 3);
    }

    private void opciones(int op) {
        switch (op) {
            case 1:
                System.out.println("\u001B[43m" + "**Sobre esta simulacion:**" + "\u001B[0m");
                simulacionSimple();
                break;
            case 2:
                System.out.println("\u001B[43m" + "**Sobre esta simulacion:**" + "\u001B[0m");
                simulacionAnalisis();
                break;
            case 3:
                System.out.println("Regresando al Menú principal");
                break;
            default:
                System.out.println("Opción invalida por favor, intente de nuevo");
                break;

        }
    }

    private void simulacionSimple() {
        Simulacion ejemplo = null;
        System.out.println(infoNormal());
        if (continuar()) {
            resetearCajas();
            System.out.print("\033\143");
            try {

                ejemplo = new Simulacion(25200, 82800, this.inventario, 3, 12, true);
                ejemplo.realizarSimulacion();
                this.inventario.guardarAlmacen("files/almacen/almacen.dat");
                // agregar informe del almacen

                System.out.println("Simulacion terminada con exito, presione ENTER , para continuar");
                Scanner espera = new Scanner(System.in);
                this.inventario.generarInforme();
                System.out.println("Informe  de  inventario generado  en: files/almacen/almacenInforme.txt");
                String aux = espera.nextLine();
                System.out.print("\033\143");

            } catch (Exception e) {
                System.out.println(
                        "Algun desastre ocurrio en el supermecado, no  fue posible terminar la simulacion intenta mas tarde");
            }
        }

    }

    private void simulacionAnalisis() {
        System.out.println(infoIteradora());
        if (continuar()) {
            System.out.print("\033\143");
            resetearGnuplot();
            gnuplotData();
        }

    }

    private void gnuplotData() {
        Simulacion ejemplo = null;
        try {
            int contador = 0;
            for (int i = 0; i < 100; i++) {

                for (int j = 1; j < 15; j++) {
                    barraProgreso(100 * (contador) / (100 * 15));
                    ejemplo = new Simulacion(25200, 82800, this.inventario, j, 15 - j, false);
                    ejemplo.realizarSimulacion();
                    inventario = Lector.leerAlmacenS("files/almacen/almacen.dat");
                    System.out.print("\033\143");
                    contador++;
                }
            }
            System.out.print("\033\143");

            barraProgreso(100);

        } catch (Exception e) {
            System.out.println(
                    "Algun desastre ocurrio en el supermecado, no  fue posible terminar la simulacion intenta mas tarde");
        }
        mediaDatos();
    }

    private void mediaDatos() {
        for (int i = 1; i <= 14; i++) {
            Interprete.obtenerMediaPorHora("files/gnuplot_data/brute/info_" + i + ".txt",
                    "files/gnuplot_data/media/media_" + i + "cajaRapida" + ".txt");
        }
    }

    private void barraProgreso(int progreso) {
        System.out.println("Progreso de la generacion de datos");
        System.out.print("[");
        for (int k = 0; k <= 100; k++) {
            if (k % 2 == 0) {
                if (k <= progreso && progreso != 0) {
                    System.out.print("\u001B[42m" + " " + "\u001B[0m");
                } else {
                    System.out.print(" ");
                }
            }

        }
        System.out.print("]" + progreso + "%\n");
    }

    private boolean continuar() {
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

    private String infoNormal() {
        return "Este tipo de simulacion te permite ver en tiempo real como trabajan las cajas del supermercado.\n"
                + "El supermercado abre a las 7:00 y cierra a las 23:00 horas.\n"
                + "Durante ese tiempo se estaran generando archivos que registran las compras de los clientes los cuales se almacenan en :\n"
                + "Supermercado/files/registroCajas\n"
                + "Considera que el tiempo medio de la simulacion es de 5min - 15min";
    }

    private String infoIteradora() {
        return "Este tipo de simulacion genera datos que pueden ser analizados mediante el script ./plots.sh contenido en el directorio de trabajo Supermercado \n"
                + "Una barra de progreso te informara en todo tiempo el progreso de la simulacion, al finalizar tras ejecutar el script ./plots.sh podras generar \n"
                + "graficas que te permiten visualizar la eficacia de atencion dependendiendo del numero de cajas rapidas abiertas las cuales puedes ver en el directorio: \n"
                + "Supermercado/plots\n" + "El almacen no se ve modificado por las operaciones aqui realizadas\n"
                + "Considera que el tiempo medio de la simulacion es de 8min - 20min";
    }

    private void resetearCajas() {
        for (int i = 1; i <= 15; i++) {
            Escritor.sobrescribirStringEnTxt("files/registroCajas/caja_" + i + ".txt");
        }
    }

    private void resetearGnuplot() {
        for (int i = 1; i <= 14; i++) {
            Escritor.sobrescribirStringEnTxt("files/gnuplot_data/brute/info_" + i + ".txt");
            Escritor.sobrescribirStringEnTxt("files/gnuplot_data/media/media_" + i + "cajaRapida.txt");
        }
    }
}
