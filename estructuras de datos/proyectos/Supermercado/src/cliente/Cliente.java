package cliente;

import almacen.*;
import cliente.*;
import estructuras.*;
import java.util.Random;

public class Cliente {
    private Inventario tienda;
    private Carrito carrito;
    private double tiempoAtencion;
    private double estimadoAtencion;
    private int entro;

    /**
     * Creaa un cliente listo para comprar en la tiendaa
     */
    public Cliente(Inventario tienda, int hora_entrada) {
        tiempoAtencion = 0;
        estimadoAtencion = 0;
        carrito = new Carrito();
        this.tienda = tienda;
        this.entro = hora_entrada;
    }

    /**
     * Regresa laa hora en la que el cliente ingreso a la tienda
     * 
     * @return hora en segundos
     */
    public int horaEntrada() {
        return this.entro;
    }

    /**
     * Simula el comportamiento de compra
     */
    public void comprar() {
        do {
            comprarAux();
        } while (seguirComprando());
    }

    /**
     * Obtiene el carrito del cliente
     * 
     * @return el carrito del cliente
     */
    public Carrito obtenerCarrito() {
        return this.carrito;
    }

    /**
     * Asigna el tiempo en el que se atendera el tiempo
     * 
     * @param tiempo tiempo de asignacion
     */
    public void asignarTiempoEspera(double tiempo) {
        this.tiempoAtencion = tiempo;
        this.estimadoAtencion = tiempo;
    }

    /**
     * Disminuye un segundo el tiempo de espera del cliente
     */
    public void disminuirSec() {
        if (tiempoAtencion - 1 < 0) {
            tiempoAtencion = 0;
        } else {
            tiempoAtencion--;
        }
    }

    /**
     * Informa si un cliente sigue siendo atendido
     * 
     * @return true si aun se esta atendiendo, false en caso contrario
     */
    public boolean enAtencion() {
        return tiempoAtencion != 0;
    }

    /**
     * Retorna el tiempo que paso el cliente en la cola
     * 
     * @return
     */
    public double tiempoEn_Espera() {
        return this.estimadoAtencion;
    }

    /**
     * Informa de cuanto tiempo de atencion tiene que pasar el cliente antes de ser
     * procesado
     * 
     * @return tiempo faltante
     */
    public double tiempo_atencion() {
        return this.tiempoAtencion;
    }

    private void comprarAux() {
        Random n = new Random();
        Producto tomado = null;
        do {
            tomado = tienda.busqueda(Integer.toString(n.nextInt(tienda.numeroDeArticulos())));
        } while (tomado.getCantidad() < 15);
        int numElementos = numeroDeUnidades(tomado, 100);
        tomado = new Producto(tomado.getID(), tomado.getNombre(), tomado.getPrecio(), numElementos);
        this.carrito.agregarProductoAlCarrito(tomado);
    }

    private int numeroDeUnidades(Producto deseo, int nvDeseo) {
        int nivel_deseo = nvDeseo;
        Double reduccion_deseo = 0.0;
        int numeroUnidades = 0;
        int control_sueldo = 0;
        int salario_minimo = 123;
        while (conciencia(nivel_deseo) && deseo.getCantidad() > 0) {
            numeroUnidades++;
            deseo.disminuirExistencias(1);
            control_sueldo = (salario_minimo * 5);
            if (control_sueldo <= numeroUnidades * deseo.getPrecio()) {
                reduccion_deseo = 70.0;
            } else {
                reduccion_deseo = 70 * (numeroUnidades * deseo.getPrecio()) / control_sueldo;
            }
            nivel_deseo -= 2 + Math.floor(reduccion_deseo);
            nivel_deseo = nivel_deseo < 2 ? 2 : nivel_deseo;
        }
        return numeroUnidades;
    }

    private boolean conciencia(int nivel) {
        Random siNo = new Random();
        return siNo.nextInt(101) <= nivel;
    }

    private boolean seguirComprando() {
        int elementos = this.carrito.elementosEnCarro();
        int score = elementos;
        score = score > 98 ? 98 : score;
        return conciencia(100 - score);
    }

}