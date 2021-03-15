package almacen;

import almacen.*;
import funcionalidades.*;
import estructuras.ArbolAVL;
import java.io.*;

/**
 * Clase que modela el inventario del supermercado
 */
public class Inventario implements Serializable {

    private ArbolAVL<Producto> registro;
    private int numeroElementos;

    /**
     * Constructor por omision, crea un inventario vacio
     */
    public Inventario() {
        registro = new ArbolAVL<>();
        numeroElementos = 0;
    }

    /**
     * Constructor que recibe un arbol AVL de productos que corresponde a un
     * inventario
     * 
     * @param fuente Arbol AVL de donde a partir del cual se construye el inventario
     */
    public Inventario(ArbolAVL<Producto> fuente) {
        registro = fuente;
        numeroElementos = fuente.getTamanio();
    }

    /**
     * Metodo que nos dice el numero de articulos guardados en el inventario
     * 
     * @return cantidad de productos guardados en el almacen
     */
    public int numeroDeArticulos() {
        return this.numeroElementos;
    }

    /**
     * Metodo que busca cierto elemento dentro del inventario, retorna null, en caso
     * de que este aun no se haya registrado dentro del inventario
     * 
     * @param numero identificador del producto
     * @return
     */
    public Producto busqueda(String numero) {
        if (numero.length() <= 6) {
            Producto aux = new Producto(numero, "", 0, 0);
            aux = registro.buscar(aux);
            return aux;
        }
        return null;
    }

    /***
     * Metodo que agrega un nuevo producto al inventario
     * 
     * @param nombre      nombre del nuevo producto
     * @param precio      precio del nuevo producto
     * @param existencias numero de existencias del producto nuevo
     * @return false si no el producto que se quiere agregar no es valido y true en
     *         caso de que se agregue el producto de forma exitosa
     */
    public boolean agregarProducto(String nombre, Double precio, int existencias) {
        if (nombre.equals("") || precio == 0) {
            return false;
        }
        Producto nuevo = new Producto(this.indentificador(), nombre, precio, existencias);
        this.registro.agrega(nuevo);
        return true;
    }

    /**
     * Metodo que elimina un producto del inventario
     * 
     * @param id id del elemento a eliminar
     */
    public void eliminarProducto(String id) {
        if (id.length() <= 6) {
            Producto aux = new Producto(id, "", 0, 0);
            this.registro.elimina(aux);
        }
    }

    /**
     * Guarda el almacen en un archivo serializado
     */
    public void guardarAlmacen(String ruta) {
        try {
            FileOutputStream respaldo;
            ObjectOutputStream salida = null;

            respaldo = new FileOutputStream(ruta);

            salida = new ObjectOutputStream(respaldo);
            salida.writeObject(this);
            salida.close();

        } catch (Exception e) {
            System.out.println("NO ES POSIBLE GUARDAR EL ALMACEN, porfavor intente mas tarde");
        }
    }

    /**
     * Metodo que genera un archivo txt conun informe de cuales son los productos
     * con bajas existencias
     */
    public void generarInforme() {
        Escritor.sobrescribirStringEnTxt("files/almacen/almacenInforme.txt");
        Escritor.escribirStringEnTxt(
                "**Informe  productos con pocas existencias\n"
                        + "ID\t Cantidad \t Nombre del producto \t\t\t   Costo unitario\t",
                "files/almacen/almacenInforme.txt", true);
        for (Producto p : this.registro) {
            if (p.getCantidad() < 15) {
                Escritor.escribirStringEnTxt(p.toString(), "files/almacen/almacenInforme.txt", true);
            }
        }
    }

    /**
     * Solo debe ser llamado cuando se agrega un nuevo producto
     * 
     * @return el id en el formato de trabajo
     */
    private String indentificador() {
        numeroElementos++;
        String num = "" + numeroElementos;
        while (num.length() < 6) {
            num = "0" + num;
        }
        return num;
    }

    @Override
    public String toString() {
        String exit = "";
        for (Producto p : this.registro) {
            exit += p.toString() + "\n";
        }
        return exit;
    }
}
