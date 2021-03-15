package funcionalidades;

import java.io.*;

public class Escritor {
    /**
     * Metodo que escribe al inicio o al final de un archivo de texto plano
     * 
     * @param cadena        cadena de texto a agregar al archivo
     * @param ruta_registro ruta donde se encuentra o se quiere generar el archivo
     * @param inicio_final  true para escritura al final del archivo, false para
     *                      escribir en el inicio
     */
    public static void escribirStringEnTxt(String cadena, String ruta_registro, boolean inicio_final) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(ruta_registro, inicio_final);
            pw = new PrintWriter(fichero);

            pw.println(cadena);

        } catch (Exception e) {
            System.out.println("No se ha podido  guardar los  registros de la caja");
        } finally {
            try {
                if (null != fichero)
                    fichero.close();
            } catch (Exception e2) {
                System.out.println("Ha ocurrido un error inesperado porfavor espere");
            }
        }
    }

   /**
    * Se borra el archivo paasado por parametro
    * @param ruta_registro
    */
    public static void sobrescribirStringEnTxt( String ruta_registro) {
        File fichero = null;
        try {
            fichero = new File(ruta_registro);
            if(fichero.exists()){
                fichero.delete();
            }

        } catch (Exception e) {
            System.out.println("No se han podido borrar  los archivos");
        } 
    }
}