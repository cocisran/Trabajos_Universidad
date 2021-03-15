package almacen;

import java.io.Serializable;
/**
 * Clase que modela los productos vendidos en el supermecado
 */
public class Producto implements Comparable<Producto>, Serializable {

    // Atributos de clase producto
    private int cantidad;

    private String nombre, identificador;
    private double precio;

    /**
     * Constructor por omisión de un producto.
     */
    public Producto() {

        identificador = "0";
        nombre = " ";
        cantidad = 0;
        precio = 0.00;
    }

    /**
     * Constructor por parametros de un producto.
     * 
     * @param id     identificador del producto
     * @param nombre nombre del producto
     * @param costo  precio del producto
     * @param cant   cantidad de producto
     * 
     */
    public Producto(String id, String nombre, double costo, int cant) {

        this.identificador = id;
        this.nombre = nombre;
        this.cantidad = cant;
        this.precio = costo;

    }

    /**
     * Obtener la cantidad de un producto
     * 
     * @return cantidad
     */
    public int getCantidad() {
        return this.cantidad;
    }

    /**
     * aumenta las existencias del producto
     * 
     * @param n nueva existencias
     */
    public void aumentarExistencias(int n) {
        if (n > 0) {
            this.cantidad += n;
        }
    }

    /**
     * Disminuyes las existencias de un producto
     * 
     * @param n numero de existencias tomadas del inventario
     */
    public void disminuirExistencias(int n) {
        if (n > 0) {
            if (this.cantidad - n > 0) {
                this.cantidad -= n;
            }
        }
    }

    /***
     * Nombre del producto
     * 
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Identificador del producto
     * 
     * @return identificador
     */
    public String getID() {
        return new String(this.identificador);
    }

    /**
     * Precio del producto
     * 
     * @return precio $
     */
    public Double getPrecio() {
        return this.precio;
    }

    /**
     * Asignar la cantidad de un producto
     * 
     * @param cant la cantidad del producto
     */
    public void setCantidad(int cant) {
        this.cantidad = cant;
    }

    /**
     * Compara el identificador de un producto con otro.
     * 
     * @param o producto con el que se compara
     * @return r
     */
    @Override
    public int compareTo(Producto o) {
        Producto aux = (Producto) o;
        int r = 0, a1, a2;
        a1 = Integer.parseInt(this.identificador);
        a2 = Integer.parseInt(aux.identificador);

        if (a1 < a2) {
            r = -1;
        } else if (a1 > a2) {
            r = 1;
        } else {
            r = 0;
        }
        return r;
    }

    @Override
    public String toString() {
        String precioS = Double.toString(this.precio);
        precioS = exitFormat(precioS, (15 - precioS.length()));
        String cantidadS = Integer.toString(this.cantidad);
        cantidadS = exitFormat(cantidadS, (7 - cantidadS.length()));
        String nombreS = exitFormat(this.nombre, (50 - this.nombre.length()));
        return identificador + "\t " + cantidadS + "     " + nombreS + "$" + precioS;
    }

    private String exitFormat(String fuente, int espacios) {
        if (espacios > 0) {
            for (int i = 0; i < espacios; i++) {
                fuente += " ";
            }
        }
        return fuente;
    }

}