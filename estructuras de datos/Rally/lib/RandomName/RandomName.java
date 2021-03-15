import java.util.Random;
import java.io.*;

public class RandomName {

    private  String [] nombres;

    public RandomName() {
        try {
            // recuperar archivos de serializacion si es que existen
            FileInputStream nom;
            ObjectInputStream entrada = null;
            
            nom  = new FileInputStream("nombres.dat");
            

            entrada = new ObjectInputStream(nom);
            this.nombres = (String []) entrada.readObject();
            entrada.close();

        } catch (Exception e) {
            nombres =null;
            e.printStackTrace();
        }
    }

    public String nextName() {

        Random r = new Random();
        String salida = "";
        int index  = r.nextInt(this.nombres.length);

        return nombres[index];
    }
}