package menus;

import almacen.*;
import funcionalidades.Lector;
import java.util.Scanner;

/**
 * Clase que modela la opcion Menu de Inventario del menu principal
 */
public class MenuInventario {
    private Inventario deposito;

    public MenuInventario(Inventario e) {
        deposito = e;
    }

    /**
     * Menu principal del manejo de inventario
     */
    public void main() {
        int op = 0;
        Scanner in = new Scanner(System.in);
        do {
            System.out.println("\u001B[43m" + "**Sistema de administracion de inventario*" + "\u001B[0m");
            System.out.println(
                    "1) Consultar producto \n2) Modificar existencias \n3) Agregar Producto\n4)  Generar informe de productos con  pocas existencias\n5) VOLVER");
            System.out.print("opcion: \n>");
            op = Lector.stringToInt(in.nextLine());
            System.out.print("\033\143");
            this.opciones(op);

        } while (op != 5);
    }

    private void opciones(int op) {
        switch (op) {
            case 1:
                consultarProducto();
                break;
            case 2:
                modificarExistencias();
                break;
            case 3:
                agregarProducto();
                break;
            case 4:
                this.deposito.generarInforme();
                System.out.println("Informe  generado  en: files/almacen/almacenInforme.txt");
                break;
            case 5:
                System.out.println("Regresando al Menú principal");
                break;
            default:
                System.out.println("Opción invalida por favor, intente de nuevo");
                break;
        }
    }

    private void consultarProducto() {
        Scanner in = new Scanner(System.in);
        System.out.println("\nPorfavor digite los  digitos correspondientes al ID del producto");
        System.out.print(">");
        String aux = in.nextLine();
        Producto p;
        if (aux == null) {
            p = null;
        } else {
            p = this.deposito.busqueda("" + Lector.stringToInt(aux));
        }

        if (p == null) {
            System.out.println("Producto no encontrado, por favor verifique ID");
        } else {
            System.out.println("ID\t Cantidad \t Nombre del producto \t\t\t   Costo unitario \n" + p.toString());
        }
    }

    private void modificarExistencias() {
        Scanner in = new Scanner(System.in);
        System.out.println("\n Porfavor digite los  digitos correspondientes al ID del producto");
        System.out.print(">");
        String aux = in.nextLine();
        Producto p = this.deposito.busqueda(aux);
        if (p == null) {
            System.out.println("Producto no encontrado, por favor verifique ID");
        } else {
            System.out.println("ID\t Cantidad \t Nombre del producto \t\t\t   Costo unitario \n " + p.toString());
            System.out.println("Digite las unidades a agregar al inventario");
            System.out.print(">");
            int incremento;
            aux = in.nextLine();
            incremento = Lector.stringToInt(aux);

            if (incremento < 1) {
                System.out.println("Se debe agregar al menos una unidad al inventario");
            } else {
                p.aumentarExistencias(incremento);
                System.out.println("Inventario actualizao con exito");
            }
        }
    }

    private void agregarProducto() {
        String nombre;
        Double precio;
        int existencias;
        Scanner in = new Scanner(System.in);

        System.out.print("Digite el nombre del producto\n>");
        nombre = in.nextLine();
        do {
            System.out.print("Ingrese el precio unitario\n>");
            precio = Lector.stringToDouble(in.nextLine());
            if (precio <= 0) {
                System.out.println("El precio por unidad deber ser positivo");
            }
        } while (precio <= 0);
        do {
            System.out.print("Ingrese el numero de existencias en inventario\n>");
            existencias = Lector.stringToInt(in.nextLine());
            if (existencias <= 0) {
                System.out.println("Las existencias deben ser mayores a 0");
            }
        } while (existencias <= 0);
        if (this.deposito.agregarProducto(nombre, precio, existencias)) {
            System.out.println("Producto agregado con exito");
            System.out.println("ID\t Cantidad \t Nombre del producto \t\t\t   Costo unitario \n "
                    + deposito.busqueda("" + deposito.numeroDeArticulos()));
        } else
            System.out.println("El producto no pudo agregarse, porfavor intente mas tarde");

    }
}