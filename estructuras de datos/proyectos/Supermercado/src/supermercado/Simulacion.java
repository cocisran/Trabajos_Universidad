package supermercado;

import supermercado.*;
import almacen.*;
import cliente.*;
import estructuras.*;
import funcionalidades.*;
import java.util.Random;

/**
 * Clase usada para realizar la simulacion del supermercado
 */
public class Simulacion {

    private Inventario almacen;
    private Caja[] cajas;
    private Cajero[] cajeros;
    private int cajasRapidas;
    private int cajasNormales;
    private int numero_cajas;

    private int hora_Salida;
    private int hora_Entrada;
    private int sec_control;

    private int clientes_atendidos;
    private int proximo_cliente;
    private int eventos, caja_cerrada;

    private boolean tipo;

    /**
     * Constructor base de una simulacion sin cajas rapidas
     * 
     * @param horaDeEntrada   hora en la que abre el supermercado
     * @param horaDeSalida    hora en la que se cierra el supermercado
     * @param inventario      inventario sobre el cual se realiza la simulacion
     * @param numeroCajas     numero de cajas que tiene el supermercado
     * @param tipo_simulacion true para una simulacion para usuario false para una
     *                        simulacion orientada a generar datos de analisis
     * @throws DatosInvalidosParaSimulacio si la hora de entrada > hora salida,
     *                                     inventario nulo, o numero menor a 1 de
     *                                     cajas
     */
    public Simulacion(int horaDeEntrada, int horaDeSalida, Inventario inventario, int numeroCajas,
            boolean tipo_simulacion) throws DatosInvalidosParaSimulacio {
        datosValidos(horaDeEntrada, horaDeSalida, inventario, numeroCajas);
        this.tipo = tipo_simulacion;
        this.numero_cajas = numeroCajas;
        this.cajasNormales = numeroCajas;
        this.cajasRapidas = 0;
        this.almacen = inventario;
        this.cajas = new Caja[numeroCajas];
        this.cajeros = new Cajero[numeroCajas];

        this.hora_Entrada = horaDeEntrada;
        this.hora_Salida = horaDeSalida;
        this.sec_control = horaDeEntrada;

        this.clientes_atendidos = 0;
        this.proximo_cliente = 120;
        this.eventos = 0;
        this.caja_cerrada = 0;
    }

    /**
     * 
     * @param horaDeEntrada     hora en la que abre el supermercado
     * @param horaDeSalida      hora en la que se cierra el supermercado
     * @param inventario        inventario sobre el cual se realiza la simulacion
     * @param num_cajaRapidas   numero de cajas rapidas con las cuales se abre el
     *                          supermercado
     * @param num_cajasNormales numero de cajas normales con las cuales
     * @param tipo_simulacion   true para una simulacion para usuario false para una
     *                          simulacion orientada a generar datos de analisis
     * @throws DatosInvalidosParaSimulacio si la hora de entrada > hora salida,
     *                                     inventario nulo, o numero menor a 1 de
     *                                     cajas
     */
    public Simulacion(int horaDeEntrada, int horaDeSalida, Inventario inventario, int num_cajaRapidas,
            int num_cajasNormales, boolean tipo_simulacion) throws DatosInvalidosParaSimulacio {

        if (num_cajaRapidas < 0) {
            throw new DatosInvalidosParaSimulacio("El  numero de cajas rapidas debe ser mayor o igual a cero");
        }
        if (num_cajasNormales < 1) {
            throw new DatosInvalidosParaSimulacio("El  numero de cajas normales debe ser al menos 1");
        }
        this.numero_cajas = num_cajaRapidas + num_cajasNormales;
        datosValidos(horaDeEntrada, horaDeSalida, inventario, this.numero_cajas);

        this.tipo = tipo_simulacion;
        this.cajasNormales = num_cajasNormales;
        this.cajasRapidas = num_cajaRapidas;

        this.almacen = inventario;
        this.cajas = new Caja[this.numero_cajas];
        this.cajeros = new Cajero[this.numero_cajas];

        this.hora_Entrada = horaDeEntrada;
        this.hora_Salida = horaDeSalida;
        this.sec_control = horaDeEntrada;

        this.clientes_atendidos = 0;
        this.proximo_cliente = 120;
        this.eventos = 0;
        this.caja_cerrada = 0;
    }

    /**
     * Llamar este metodo inicia la simulacion del supermercado
     */
    public void realizarSimulacion() {
        crearCajas();
        while (this.sec_control < this.hora_Salida) {

            try {
                generacionClientes();

                this.sec_control++;
                manejoEventos();
                actualizarHilos();
                if (this.tipo) {
                    estadoSimulacion(); // imprime por consola el tipo de simulacion
                    try {
                        Thread.sleep(5);
                    } catch (Exception o) {

                    }
                }

            } catch (Exception NullPointerException) {
                // System.out.println("muco texto");
                // estadoSimulacion();
            }

        }
        esperarHilos();
        if (tipo) {
            estadoSimulacion();
            int i = cajaMasClientes();
            System.out.println("La caja normal que atendio mas clientes fue: caja " + (i + 1) + "  con  "
                    + cajas[i].clientesAtendidos() + " clientes atendidos");
        } else {
            saveDataforGnuplot();
        }

    }

    /**
     * Guarda los datos sobre el tiempo de atencion relacionado con la hora de
     * entrada
     */
    private void saveDataforGnuplot() {
        for (int i = 0; i < cajas.length; i++) {
            cajas[i].saveData("files/gnuplot_data/brute/info_" + this.cajasRapidas + ".txt");
        }
    }

    /**
     * Controla el cierre aleatorio de las cajas
     */
    private void manejoEventos() {

        if (eventos == 0) {
            Random suerte = new Random();
            this.eventos = suerte.nextInt(1800) + 1800;
            this.cajas[caja_cerrada].abrirCaja();
            this.caja_cerrada = suerte.nextInt(this.numero_cajas);
            this.cajas[caja_cerrada].cerrarCaja();
        } else {
            eventos--;
        }

    }

    /**
     * Imprime por consola el estado de la simulacion
     */
    private void estadoSimulacion() {
        System.out.print("\033\143");
        System.out.println("Hora " + "\u001B[40m\t" + intToHour(sec_control) + "\u001B[0m" + "\t Cliente recibidos: "
                + this.clientes_atendidos + "\n");
        System.out.println("Caja   \t\tTipo  \tEstado\t   Clientes en fila \tAtendidos  hoy\n");
        for (int i = 0; i < cajas.length; i++) {
            System.out.println(estadoCaja(cajas[i], i + 1));
        }

    }

    private String estadoCaja(Caja caja, int n) {
        String RESET = "\u001B[0m";
        return "Caja " + n + ":  \t" + "\u001B[46m" + "\u001B[30m" + (caja.esCajaRapida() ? "RAPIDA" : "NORMAL") + RESET
                + "\t" + (caja.cajaAbierta() ? "\u001B[42m" + "ABIERTA" : "\u001B[41m" + "CERRADA") + RESET + "\t\t"
                + (caja.estaVacia() ? "\u001B[42m" : "\u001B[45m") + "[ " + caja.clientesFormados() + " ]" + RESET
                + " \t\t\t" + caja.clientesAtendidos();
    }

    private static String intToHour(int secs) {
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

    private synchronized void crearCajas() {
        for (int i = 0; i < cajasNormales; i++) {
            cajas[i] = new Caja(false);
        }
        for (int i = cajasNormales; i < this.numero_cajas; i++) {
            cajas[i] = new Caja(true);
        }
        for (int i = 0; i < cajeros.length; i++) {
            cajeros[i] = new Cajero("caja_" + (i + 1), cajas[i], hora_Entrada, hora_Salida, !tipo);
            cajeros[i].start();
        }

    }

    private int cajaMasClientes() {
        int max = 0;
        for (int i = 1; i < cajasNormales; i++) {
            if (cajas[i].clientesAtendidos() > cajas[max].clientesAtendidos())
                max = i;
        }
        return max;
    }

    private void generacionClientes() {
        Random numeroClientes = new Random();
        if (this.proximo_cliente == 0) {
            int entran = numeroClientes.nextInt(3) + 1;
            Cliente nuevo;
            for (int i = 0; i < entran; i++) {
                // orden de generar otro cliente
                nuevo = new Cliente(this.almacen, this.sec_control);
                if (nuevo != null) {
                    this.proximo_cliente = maquinaConcurrencia();
                    nuevo.comprar();
                    // formar el cliente
                    formarCliente(nuevo);
                    // clientes atendidos hoy
                    this.clientes_atendidos++;
                }

            }

        } else {
            this.proximo_cliente -= 1;
            if (this.proximo_cliente < 0) {
                this.proximo_cliente = 0;
            }
        }

    }

    private void formarCliente(Cliente cliente) {
        // formar al cliente en la caja que corresponde

        int numero_Articulos = cliente.obtenerCarrito().elementosEnCarro();
        int formar = 0;
        Pila<Caja> distribucion = new Pila<Caja>();
        for (int i = 1; i < this.numero_cajas; i++) {

            if ((cajas[i].esCajaRapida() && numero_Articulos <= 20) || (!cajas[i].esCajaRapida())) {
                if (cajas[i].cajaAbierta() && (cajas[formar].clientesFormados() > cajas[i].clientesFormados())) {
                    formar = i;
                }
            }
        }

        for (int i = 0; i < this.numero_cajas; i++) {
            if ((cajas[i].esCajaRapida() && numero_Articulos <= 20) || (!cajas[i].esCajaRapida())) {
                if (cajas[i].cajaAbierta() && (cajas[formar].clientesFormados() == cajas[i].clientesFormados())) {
                    distribucion.push(cajas[i]);
                }
            }
        }

        Random sale = new Random();
        formar = sale.nextInt(distribucion.getTamanio());
        for (int i = 0; i < formar; i++) {
            distribucion.pop();
        }

        distribucion.pop().formarCliente(cliente);

    }

    private void actualizarHilos() {
        for (int i = 0; i < cajeros.length; i++) {
            cajeros[i].actualizarHilo(this.sec_control);
        }
    }

    private void esperarHilos() {
        for (int i = 0; i < cajeros.length; i++) {
            try {
                cajeros[i].join();
            } catch (Exception e) {
                System.out.println("un cajero se enfermo y no pudo atender a sus clientes");
            }

        }
    }

    /**
     * Simula la afluencia de los clientes durante el tiempo que permanece abierto
     * 
     * @return segundos restantes para que se genere el proximo cliente
     */
    private synchronized int maquinaConcurrencia() {
        Random n = new Random();
        switch (sec_control / 3600) {
            // afluencia muy baja o casi nula
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return 1800 + n.nextInt(1801);
            case 7:
                return 150 + n.nextInt(151);
            case 8:
                return 100 + n.nextInt(101);
            case 9:
                return 80 + n.nextInt(81);
            case 10:
                return 70 + n.nextInt(71);
            case 11:
                return 60 + n.nextInt(61);
            case 12:
                return 50 + n.nextInt(51);
            case 13:
                return 40 + n.nextInt(41);
            case 14:
                return 30 + n.nextInt(31);
            case 15:
                return n.nextInt(21);
            case 16:
            case 17:
                return 30 + n.nextInt(31);
            case 18:
                return 40 + n.nextInt(41);
            case 19:
                return 50 + n.nextInt(51);
            case 20:
                return 100 + n.nextInt(101);
            case 21:
                return 110 + n.nextInt(101);
            case 22:
                return 120 + n.nextInt(151);
            case 23:
                return 240 + n.nextInt(451);
            default:
                return 60;
        }

    }

    private void datosValidos(int horaDeEntrada, int horaDeSalida, Inventario inventario, int numeroCajas)
            throws DatosInvalidosParaSimulacio {
        if (horaDeEntrada < 0 || horaDeEntrada >= 86400)
            throw new DatosInvalidosParaSimulacio("hora de entrada debe ser un valor entero entre 0 y 86 4000");
        if (horaDeSalida <= horaDeEntrada || horaDeSalida > 86400)
            throw new DatosInvalidosParaSimulacio(
                    "hora de salida deber ser un valor entero entero mayor a la hora de entrada y menor o igual a 86 4000 ");
        if (numeroCajas < 1)
            throw new DatosInvalidosParaSimulacio("El  numero de cajas debe ser al menos 1");
        if (inventario == null)
            throw new DatosInvalidosParaSimulacio("El almacen proporcionado no es valido");
    }

    /**
     * Excepcion que se lanza cuando los datos pasados al constructor fueron
     * invalidos
     */
    public class DatosInvalidosParaSimulacio extends Exception {
        public DatosInvalidosParaSimulacio(String error) {
            super(error);
        }
    }
}