package supermercado;

import supermercado.Caja;

/**
 * Clase que controla la ejecucion de las cajas
 */
public class Cajero extends Thread {

    private Caja caja;
    private int tiempo;
    private int tiempo_porCumplir;
    private int hora_cierre;
    private boolean plot;

    /**
     * Constructor que crea el cajero (hilo ) que maneja la caja
     * 
     * @param id_caja     nombre de la caja
     * @param caja        un objeto caja **ya inicializado**
     * @param hora_inicio hora en la que se comienza las labores en el supermecado
     * @param hora_cerrar hora en el que el supermecado cierra sus puertas al
     *                    publico
     */
    public Cajero(String id_caja, Caja caja, int hora_inicio, int hora_cerrar, boolean pa_plot) {
        super(id_caja);
        this.caja = caja;
        this.caja.abrirCaja();
        this.tiempo = hora_inicio;
        this.tiempo_porCumplir = 0;
        this.hora_cierre = hora_cerrar;
        this.plot = pa_plot;

    }

    /**
     * Metodo que mantiene la armonia entre el tiempo pasado en el hilo principal y
     * este hilo debe llamarse despues de ejecutar el metodo start() pues de lo
     * contrario el tiempo en la caja entra en un bucle temporar que no le permite
     * avanzar
     * 
     * @param reloj_Global tiempo en segundos del reloj principal
     */
    public void actualizarHilo(int reloj_Global) {
        tiempo_porCumplir += reloj_Global - this.tiempo;
        this.tiempo = reloj_Global;
    }

    /**
     * Metodo que retorna el tiempo en segundos en el cual se encuentra la caja
     * 
     * @return entero que representa en segundos la hora local de la caja
     */
    public int getTiempoCaja() {
        return this.tiempo;
    }

    /**
     * El metodo run simula al trabajador de la caja, reaaliza las tareas
     * administrativas correspondientes y guarda los registros de las operaciones
     * realizadas durante la simulacion
     */
    @Override
    public void run() {

        while (mantenerVivo()) {

            try {
                this.sleep(10);
            } catch (Exception e) {
                // fuerza actuaalizacion de mantenerVivo
            }

            if ((this.tiempo - tiempo_porCumplir) >= this.hora_cierre) {
                caja.cerrarCaja();
                while (!this.caja.estaVacia()) {

                    this.tiempo++;
                    int aux = this.tiempo - tiempo_porCumplir;
                    this.caja.actualizarEstado();
                    this.caja.atender("files/registroCajas/" + getName() + ".txt", getName(), plot);
                }
            }

            while (tiempo_porCumplir > 0) {
                int aux = this.tiempo - tiempo_porCumplir;
                this.caja.actualizarEstado();
                this.caja.atender("files/registroCajas/" + getName() + ".txt", getName(), plot);
                this.tiempo_porCumplir -= this.tiempo_porCumplir == 0 ? 0 : 1;

            }

        }
        caja.cerrarCaja();
        if (!plot) {
            caja.registrarVentasDelDia("files/registroCajas/" + getName() + ".txt");
        }

        // fin del hilo

    }

    private boolean mantenerVivo() {
        // *****aun no se cierra el super****la caja va atrasada*********aun hay
        // clientes formados
        return this.tiempo < hora_cierre || this.tiempo_porCumplir > 0 || !this.caja.estaVacia();
    }

    /**
     * Metodo que transformaa la hora del dia dada en segundos a un formato
     * [hh:mm:ss]
     * 
     * @param secs hora del dia en segundos
     * @return hora del dia [hh:mm:ss] -> [hora:minutos:segundos]
     */
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

}