
import almacen.*;
import funcionalidades.*;
import menus.*;

import java.io.*;
import java.util.Scanner;

//System.out.print("\033\143");
public class Main {

    private static Inventario almacen;
    private static ControlEjecucion estadoDelPrograma;

    public static void main(String[] args) {

        iniciarPrograma();
        menuPrincipal();

    }

    private static void menuPrincipal() {
        int op = 0;
        Scanner in = new Scanner(System.in);
        do {
            System.out.println("\u001B[43m" + "**Menu principal**" + "\u001B[0m");
            System.out.println("1) Menu de simulaciones \n2) Menu del almacen   \n3) Ajustes \n4) SALIR");
            System.out.print("opcion: \n>");
            op = Lector.stringToInt(in.nextLine());
            System.out.print("\033\143");
            opciones(op);

        } while (op != 4);
    }

    private static void opciones(int op) {
        switch (op) {
            case 1:
                menuSimulacion();
                break;
            case 2:
                menuAlmacen();
                break;
            case 3:
                ajustes();
                break;
            case 4:
                System.out.println("!Que tengas un excelente dia!");
                break;
            default:
                System.out.println("No te he entendido, por favor intenta de nuevo");
                break;
        }
    }

    private static void ajustes() {
        MenuResetear.main();
        if (MenuResetear.resetear()) {
            iniciarPrograma();
            MenuResetear.informarReseteo();
        }
    }

    private static void menuSimulacion() {
        MenuSimulacion menu_simulacion = new MenuSimulacion(almacen);
        menu_simulacion.main();
    }

    private static void menuAlmacen() {
        MenuInventario menu_del_Inventario = new MenuInventario(almacen);
        menu_del_Inventario.main();
        almacen.guardarAlmacen("files/almacen/almacen.dat");
    }

    private static void iniciarPrograma() {
        estadoDelPrograma = ControlEjecucion.obtenerInstancia();

        if (estadoDelPrograma.primeraEjecucion()) {
            primeraEjecucion();
        } else {
            diagnostico();
            almacen = Lector.leerAlmacenS("files/almacen/almacen.dat");
            if (almacen == null) {
                almacen = Lector.leerAlmacen("files/almacen/almacen.txt");
                almacen.guardarAlmacen("files/almacen/almacen.dat");
            }
        }
    }

    private static void primeraEjecucion() {

        almacen = Lector.leerAlmacen("files/almacen/almacen.txt");

        if (almacen == null) {
            GeneradorInventario nuevo = new GeneradorInventario("files/almacen/almacen.txt");
            nuevo.CrearAlmacen();
            almacen = Lector.leerAlmacen("files/almacen/almacen.txt");
            almacen.guardarAlmacen("files/almacen/almacen.dat");

        }
        estadoDelPrograma.start();
        estadoDelPrograma.informInventarioTXTCreado();
        estadoDelPrograma.informInventarioSCreado();

        estadoDelPrograma.guardarEstado();
    }

    private static void diagnostico() {

        File archivo = new File("files/almacen/almacen.txt");
        if (!archivo.exists()) {

            estadoDelPrograma.informInventarioTXTborrado();
        }
        archivo = new File("files/almacen/almacen.dat");
        if (!archivo.exists()) {
            estadoDelPrograma.informInventarioSborrado();
        }
        archivo = null;
        if (!estadoDelPrograma.estadoInventarioTXT()) {
            System.out.println("Se perdieron los datos guardados, Estamos generando nuevos");
            System.out.println(
                    "El programa genera archivos para su correcto funcionamiento, recomendamos evitar su manejo o edicion a menos que tenga conocimientos sobre lo que hace");
            GeneradorInventario nuevo = new GeneradorInventario("files/almacen/almacen.txt");
            nuevo.CrearAlmacen();
            almacen = Lector.leerAlmacen("files/almacen/almacen.txt");
            almacen.guardarAlmacen("files/almacen/almacen.dat");
            estadoDelPrograma.informInventarioTXTCreado();
            estadoDelPrograma.informInventarioSCreado();
        }

        if (!estadoDelPrograma.estadoInventarioS()) {
            almacen = Lector.leerAlmacen("files/almacen/almacen.txt");
            almacen.guardarAlmacen("files/almacen/almacen.dat");
            estadoDelPrograma.informInventarioSCreado();
        }
        estadoDelPrograma.guardarEstado();
    }

}