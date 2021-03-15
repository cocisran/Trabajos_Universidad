package funcionalidades;

import java.io.*;

/**
 * Objeto que lleva el control de la ejecucion del programa principal
 */
public class ControlEjecucion implements Serializable {

    private static final ControlEjecucion INSTANCIA = new ControlEjecucion();

    private boolean primeraEjecucion;
    private boolean arboltxt;
    private boolean arbolS;

    private ControlEjecucion() {
        primeraEjecucion = true;
        arboltxt = false;
        arbolS = false;
    }

    /**
     * Informa si es la primera vez que se inicia el programa
     * 
     * @return true si es la primera ejecucion false en caso contrario
     */
    public boolean primeraEjecucion() {
        return primeraEjecucion;
    }

    /**
     * Indica que el programa ya se inicio con exito al menos una vez
     */
    public void start() {
        primeraEjecucion = false;
    }

    /**
     * Indica el estado en el que se encuentra el archivo txt que guarda el almacen
     * 
     * @return true creado / false no creado o borrado
     */
    public boolean estadoInventarioTXT() {
        return arboltxt;
    }

    /**
     * Indica el estado en el que se encuentra el archivo serializado que guarda el
     * almacen
     * 
     * @return true creado / false no creado o borrado
     */
    public boolean estadoInventarioS() {
        return arbolS;
    }

    /**
     * Informa que el archivo txt que guarda el almacen fue eliminado
     */
    public void informInventarioTXTborrado() {
        arboltxt = false;
    }

    /**
     * Informa que el archivo serializado que guarda el almacen fue eliminado
     */
    public void informInventarioSborrado() {
        arbolS = false;
    }

    /**
     * Informa que el archivo txt que guarda el almacen fue creado
     */
    public void informInventarioTXTCreado() {
        arboltxt = true;
    }

    /**
     * Informa que el archivo serializado que guarda el almacen fue creado
     */
    public void informInventarioSCreado() {
        arbolS = true;
    }

    /**
     * Regresa un objeto de tipo <ControlEjecucion> el cual contiene ya se el estado
     * guardado o un objeto nuevo en caso contrario
     * 
     * @return un objeto de tipo <ControlEjecucion> con el estado del programa
     */
    public static ControlEjecucion obtenerInstancia() {
        try {
            FileInputStream respaldo;
            ObjectInputStream entrada = null;

            respaldo = new FileInputStream("systemFiles/control/controlEstado.dat");
            entrada = new ObjectInputStream(respaldo);

            @SuppressWarnings("unchecked")
            ControlEjecucion salida = (ControlEjecucion) entrada.readObject();

            entrada.close();
            return salida;

        } catch (Exception e) {
            return INSTANCIA;
        }
    }

    /**
     * Guarda la instancia del objeto en systemFiles/control/controlEstado.dat
     */
    public void guardarEstado() {
        try {
            FileOutputStream respaldo;
            ObjectOutputStream salida = null;

            respaldo = new FileOutputStream("systemFiles/control/controlEstado.dat");

            salida = new ObjectOutputStream(respaldo);
            salida.writeObject(this);
            salida.close();

        } catch (Exception e) {
            System.out.println("NO ES POSIBLE ACTUALIZAR EL REGISTRO DE CONTROL");
            e.printStackTrace();
        }
    }

}