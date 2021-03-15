package cliente;

import almacen.*;
import estructuras.*;
import java.util.Random;
/**
 * Clase que modela el carrito de compras del cliente
 */
public class Carrito {

    private Cola<Producto> carrito;
    private int numeroArticulos;
    
    /**
     * Constructor por omision de un carrito vacio
     */
    public Carrito() {
        this.carrito = new Cola<Producto>();
        numeroArticulos = 0 ;
    }

    /**
     * Agrega un producto al carrito
     */
    public void agregarProductoAlCarrito(Producto tomado) {
        carrito.agrega(tomado);
        numeroArticulos += tomado.getCantidad();
    }

    /**
     * Sacaa un producto del carrito
     * 
     * @return
     */
    public Producto sacarProductoDelCarrito() {
        return this.carrito.dequeue();
    }

    /**
     * Nos dice cuantos elementos tiene el carrito
     * 
     * @return numero de productos en el carrito
     */
    public int elementosEnCarro() {
        return numeroArticulos;
    }

    /**
     * Nos informa sobre si el carrito esta vacio
     * 
     * @return true si esta vacio, false si no esta vacio
     */
    public boolean carritoVacio() {
        return this.carrito.esVacio();
    }

    @Override
    public String toString() {
        String exit = "";
        for (Producto p : this.carrito) {
            exit += p.toString() + "\n";
        }
        return  exit;
    }
}