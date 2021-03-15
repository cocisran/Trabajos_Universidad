package supermercado;

import java.util.Random;
import estructuras.*;
import almacen.*;
import cliente.*;
import funcionalidades.Escritor;

/**
 * Clase que modela las cajas del supermercado
 */
public class Caja implements Comparable<Caja> {

    private Lista<Cliente> fila_atencion;
    private boolean esCajaRapida;
    private boolean abierta;
    // simulacion de pasar los articulos
    private double min_atencion;
    private double ventas_del_dia;
    private int contador;

    private String data_Register;

    /**
     * Crea una caja nueva con un tiempo de atencion aleatorio dentro de ciertos
     * parametros
     * 
     * @param es_rapida Si la caja es rapida (true) entonces la caja solo puede
     *                  atender y formar clientes que lleven a lo mas 20 articulos
     *                  en su carrito, si no es rapida entonces los clientes puden
     *                  formarse sin importar el numero de articulos en el carrito
     */
    public Caja(Boolean es_rapida) {
        Random aux = new Random();
        fila_atencion = new Lista<Cliente>();
        esCajaRapida = es_rapida;
        min_atencion = (aux.nextDouble() + 0.5) * (aux.nextInt(7) + 3) + 1.2;
        this.abierta = false;
        contador = 0;
        ventas_del_dia = 0;
        // esta variable guardaa todaa la salida de texto para gnuplot
        data_Register = "";// (es_rapida ? "+cajaRapida" : "+cajaNormal") + "\n";
    }

    /**
     * Cambia el estado de una caja a caja rapida
     */
    public void volverCajaRapida() {
        esCajaRapida = true;
    }

    /**
     * Vuelve la caja a una caja normal
     */
    public void volverCajaNormal() {
        esCajaRapida = false;
    }

    /**
     * Informa sobre si la caja es o no rapida
     * 
     * @return true si es caja rapida , false en el caso contrario
     */
    public synchronized boolean esCajaRapida() {
        return esCajaRapida;
    }

    /**
     * Nos informa si no hay ningun cliente formado en esta caja
     * 
     * @return false si hay clientes formados, true en caso contrario
     */
    public synchronized boolean estaVacia() {
        return fila_atencion.esVacia();
    }

    /**
     * Indica el numero de clientes en la fila de esta caja
     * 
     * @return un numero entero positivo que indica el numero de clientes en la caja
     */
    public synchronized int clientesFormados() {
        return fila_atencion.longitud();
    }

    /**
     * Abre una caja para que pueda cobrar a clientes nuevos, si este metodo no ha
     * sido llamado en ningun momento despues de la creacion de la caja, esta no
     * puede atender a ningun cliente
     */
    public void abrirCaja() {
        this.abierta = true;
    }

    /**
     * Cierra la caja a nuevos clientes. Una caja primero termina de atender a sus
     * clientes actuales antes de terminar sus operaciones
     */
    public void cerrarCaja() {
        abierta = false;
    }

    /**
     * Nos dice si una caja esta abierta o no
     * 
     * @return true si se encuentra abierta , false en caso contrario
     */
    public boolean cajaAbierta() {
        return abierta;
    }

    /**
     * Metodo que formaa un cliente dentro de la fila de la caja
     * 
     * @param cliente cliente con un carrito de compras no vacio
     * @return true si el cliente pudo formarse con exito, false si el cliente no es
     *         apto para la caja, el carro es vacio o la caja se encuentra cerrada
     */
    public boolean formarCliente(Cliente cliente) {
        if (cliente != null) {
            if (this.abierta) {
                Carrito compras = cliente.obtenerCarrito();

                if (compras != null) {

                    if ((this.esCajaRapida && compras.elementosEnCarro() <= 20) || !this.esCajaRapida) {
                        double estimado = calcularTiempoEspera(compras.elementosEnCarro());
                        if (!fila_atencion.esVacia()) {
                            if (this.fila_atencion.rabo() != null) {
                                estimado += this.fila_atencion.rabo().tiempo_atencion();
                            }

                        }

                        cliente.asignarTiempoEspera(estimado);
                        this.fila_atencion.agregarAlFinal(cliente);
                        return true;
                    }
                }

            }
        }

        return false;
    }

    /**
     * Disminuye en un segundo el tiempo de espera de todos los clientes formados,
     * este metodo debe de llamarse en cada incremento del contador global de la
     * simulacion
     */
    public void actualizarEstado() {
        for (Cliente c : this.fila_atencion) {
            c.disminuirSec();
        }
    }

    /**
     * Metodo que atiende un cliente , si el tiempo de espera de atencion a
     * finalizado entonces generaa informacion referente a la compra y el control
     * del funcionamiento del supermercado
     * 
     * @param ruta_registro ruta donde se guardan los tickets de la caja
     * @param hora_atencion hora de atencion del cliente
     * @param nombre_caja   nombre identificador de la caja que brindo la atencion
     */
    public void atender(String ruta_registro, String nombre_caja, boolean plot) {
        if (!this.fila_atencion.esVacia()) {
            Cliente actual = this.fila_atencion.cabeza();
            String hora_atencion = plot ? Integer.toString(actual.horaEntrada() / 3600)
                    : intToHour(actual.horaEntrada());
            if (!actual.enAtencion()) {
                contador++;
                actual = fila_atencion.pop();
                if (plot) {

                    data_Register += hora_atencion + ", " + actual.tiempoEn_Espera() / 60 + "\n";
                } else {
                    String exit = "ticket " + contador + "\t\t hora de atencion  " + hora_atencion + "\t" + nombre_caja
                            + "\n" + this.generarTicket(actual.obtenerCarrito()) + "\n\n\n";
                    Escritor.escribirStringEnTxt(exit, ruta_registro, true);
                }
            }
        }

    }

    /**
     * Metodo que guarda en la ruta especificda los datos de todos los clientes
     * atendidos hastaa el momemeno en una relacion hora entrada-tiempo espera
     * 
     * @param ruta ruta donde se desea guarda el registro de la informacion
     */
    public void saveData(String ruta) {
        Escritor.escribirStringEnTxt(data_Register, ruta, true);
    }

    /**
     * Contador de los clientes atendidos por la caja
     * 
     * @return numero de clientes que ha atendido la caja
     */
    public int clientesAtendidos() {
        return this.contador;
    }

    /**
     * Registra en el archivo personal de la caja <ruta_registro> los datos sobre
     * las ventas del dia
     * 
     * @param ruta_registro ruta del archivo personal de la caja
     */
    public void registrarVentasDelDia(String ruta_registro) {
        String exit = lineaChar(100, '+') + "\n" + "Ventas totales de la caja: \n\t" + this.ventas_del_dia
                + "\nCLientes atendidos:\n\t" + this.contador;
        Escritor.escribirStringEnTxt(exit, ruta_registro, true);
    }

    private String intToHour(int secs) {
        String secAUX = "";
        String aux;
        aux = "" + secs / 3600;
        aux = aux.length() == 1 ? "0" + aux : aux;
        secAUX += "[" + aux + ":";
        secs = secs % 3600;
        aux = "" + secs / 60;
        aux = aux.length() == 1 ? "0" + aux : aux;
        secAUX += aux + ":";
        aux = "" + secs % 60;
        aux = aux.length() == 1 ? "0" + aux : aux;
        secAUX += aux + "]";
        return secAUX;
    }

    /**
     * Metodo que genera la informacion a guardar sobre el cliente
     * 
     * @param carrito carrito del cliente
     * @return un ticket
     */
    private String generarTicket(Carrito carrito) {

        Producto aux;
        double totalProducto = 0.0, subtotal, IVA, totalCompra = 0.0;
        String cad = "\t Supermercado" + "\n" + "\t Ticket de compra" + "\n";
        cad += lineaChar(100, '-') + "\n";
        cad += "ID\t Cantidad \t Nombre del producto \t\t\t   Costo unitario\t" + " Total" + "\n\n";

        while (!carrito.carritoVacio()) {
            aux = carrito.sacarProductoDelCarrito();
            totalProducto = aux.getPrecio() * aux.getCantidad();
            totalCompra += totalProducto;
            cad += aux.toString() + " " + "$" + totalProducto + "\n";
        }

        cad += lineaChar(100, '_') + "\n";
        IVA = totalCompra * 0.16;
        subtotal = totalCompra - IVA;

        cad += " \n\t\t Subtotal:\t$" + subtotal + "\n" + "\t\t IVA:\t\t$" + IVA + "\n" + "\t\t Total:\t\t$"
                + totalCompra + "\n";
        cad += lineaChar(100, '_') + "\n";
        cad += "\t ¡Gracias por su preferencia, vuelva pronto!";
        this.ventas_del_dia += totalCompra;
        return cad;

    }

    private String lineaChar(int n, char c) {
        String exit = "";
        for (int i = 0; i < n; i++) {
            exit += c;
        }
        return exit;
    }

    @Override
    public int compareTo(Caja o) {
        return this.clientesFormados() - o.clientesFormados();
    }

    @Override
    public String toString() {
        String exit = "";
        int i = 1;
        for (Cliente c : this.fila_atencion) {
            exit += "cliente: " + i + "  tiempo en espera: " + c.tiempoEn_Espera() + " sec   "
                    + c.tiempoEn_Espera() / 60 + " min aprox" + "\n";
            i++;
        }
        return exit;
    }

    /**
     * Metodo que asigna un tiempo de espera en base a los articulos comprados
     * 
     * @param n numero de articulos comprados
     * @return el tiempo de espera total de acuerdo al numero de articulos en el
     *         carrito
     */
    private double calcularTiempoEspera(int n) {
        Double tiempo = 0.0;
        Random aux = new Random();
        for (int i = 0; i < n; i++) {
            tiempo += (aux.nextDouble() + .25) * (aux.nextInt(5) + min_atencion);
        }
        return tiempo;
    }

}