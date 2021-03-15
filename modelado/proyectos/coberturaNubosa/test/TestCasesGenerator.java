import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.util.Random;

/**
 * Clase que genera un caso de prueba controlado para el programa principals
 */
public class TestCasesGenerator {
    private static int width = 4368;
    private static int height = 2912;
    private static int centroX = 2184;
    private static int centroY = 1456;
    private static int radio = 1324;

    public static void main(String[] args) {
        int pixelesNube = 0;
        int areaInteres = (int) Math.ceil(Math.PI * Math.pow(radio, 2));
        BufferedImage imagen_salida = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        pixelesNube = pintarNubes(imagen_salida, 100, 500);
        pintarCielo(imagen_salida);
        String ruta = args.length == 1 ? args[0] : "ejemplo";
        escribirImagen(imagen_salida, ruta);
        Double cci = (double) pixelesNube / (double) areaInteres;
        System.out.println("Pixeles de nube: " + pixelesNube);
        System.out.println("Area total: " + areaInteres);
        System.out.println("CCI: " + cci);

    }

    /**
     * Metodo para pintar n nubes dentro del objeto BufferedImage
     * 
     * @param img     Objeto BufferedImage sobre el cual se quieren pintar las nubes
     * @param n       numero de nubes
     * @param maxSize radio maximo que puede tener la nube individualmente
     * @return total de pixeles de nube añadidos al objeto BufferedImage
     */
    public static int pintarNubes(BufferedImage img, int n, int maxSize) {
        int totalPixelesNube = 0;
        for (int i = 0; i < n; i++) {
            totalPixelesNube += pintarNube(img, (int) Math.floor(Math.random() * (double) width),
                    (int) Math.floor(Math.random() * (double) height), new Random().nextInt(maxSize) + 10);
        }
        return totalPixelesNube;

    }

    /**
     * Metodo para rellenar con cielos los pixeles no nube de la imagen
     * 
     * @param img Objeto BufferedImage sobre el cual se quiere rellenar
     */
    public static void pintarCielo(BufferedImage img) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (!esNube(i, j, img) && dentroCirculo(centroX, centroY, radio, i, j)) {
                    pintarPixelCielo(img, i, j);
                }
            }
        }
    }

    /**
     * Metodo para pintar una "nube"
     * 
     * @param img Objeto BufferedImage sobre el cual se quiere pintar la nube
     * @param x   centro en x de la nube
     * @param y   centro en y de la nube
     * @param r   radio de la nube
     * @return total de pixeles de nube añadidos al objeto BufferedImage
     */
    public static int pintarNube(BufferedImage img, int x, int y, int r) {
        int pixelesNube = 0;
        for (int i = x - r; i <= x + r; i++) {
            for (int j = y - r; j <= y + r; j++) {

                if (i >= 0 && i < width && j >= 0 && j < height) {
                    double probaSerNube = (0.80 * (r - distanciaPuntos(i, j, x, y)) / r);
                    if (hacerNube(probaSerNube) && !esNube(i, j, img) && dentroCirculo(centroX, centroY, radio, i, j)) {
                        pintarPixelNube(img, i, j);
                        pixelesNube++;
                    }
                }

            }
        }
        return pixelesNube;

    }

    /**
     * Metodo para pintar un pixel en las cordenadas dadas en un rango de colores
     * caracteristicos del cielo
     * 
     * @param img objeto BufferedImage sobre el cual se modificara el pixel
     * @param x   coordenada en x del pixel
     * @param y   coordenada en y del pixel
     */
    public static void pintarPixelCielo(BufferedImage img, int x, int y) {
        Random valores = new Random();
        int g = valores.nextInt(201);
        int b = g + valores.nextInt(256 - g);
        int p = (255 << 24) | (0 << 16) | (g << 8) | b;
        img.setRGB(x, y, p);
    }

    /**
     * Metodo para pintar un pixel en las cordenadas dadas en un rango de colores
     * caracteristicos de las nubes
     * 
     * @param img objeto BufferedImage sobre el cual se modificara el pixel
     * @param x   coordenada en x del pixel
     * @param y   coordenada en y del pixel
     */
    public static void pintarPixelNube(BufferedImage img, int x, int y) {
        Random valores = new Random();
        int b = (int) Math.floor((150 + valores.nextInt(100)) * 0.95);
        int r = valores.nextInt(250 - b) / 2 + b;
        int p = (255 << 24) | (r << 16) | (b << 8) | b;
        img.setRGB(x, y, p);
    }

    /**
     * Método que escribe la imagen pasada por parametro en la ruta especificada
     * 
     * @param img  Objeto BufferedImage que corresponde a la imagen de salida
     * @param ruta nombre que se le quiere dar al archivo
     */
    public static void escribirImagen(BufferedImage img, String ruta) {
        try {
            File archivo = new File(ruta + ".png");
            ImageIO.write(img, "png", archivo);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Informa sobre si un pixel en las coordenadas dadas es un pixel con colores
     * que representen una nube
     * 
     * @param x   coordenadas en x del pixel
     * @param y   coordenadas en y del pixel
     * @param img Objeto BufferedImage que corresponde a la imagen de referencia
     * @return True si es una nube, false en caso contrario
     */
    public static boolean esNube(int x, int y, BufferedImage img) {
        int aux[] = arrayRGB(x, y, img);
        aux[3] = aux[3] == 0 ? 1 : aux[3];
        return (double) aux[1] / (double) aux[3] > 0.95;
    }

    /**
     * Metodo que descompone un pixel en un arreglo con sus componentes en ARGB
     * 
     * @param x   coordenada en x del pixel
     * @param y   coordenada en y del pixel
     * @param img Objeto BufferedImage que corresponde a la imagen de referencia
     * @return Arreglo de enteros con las componetes ARGB del pixel {Alpha,
     *         Red,Green ,Blue}
     */
    public static int[] arrayRGB(int x, int y, BufferedImage img) {
        int p = img.getRGB(x, y);
        int a = (p >> 24) & 0xff;
        int r = (p >> 16) & 0xff;
        int g = (p >> 8) & 0xff;
        int b = p & 0xff;
        return new int[] { a, r, g, b };
    }

    /**
     * Metodo que dada una probabilidad de convertir en nube un pixel decide si se
     * vuelve pixel de nube o no
     * 
     * @param p probabilidad de convertirse en nube [0,1]
     * @return true si el pixel se volvera nube, false en caso contrario
     */
    public static boolean hacerNube(double p) {
        return Math.random() <= p;
    }

    /**
     * Metodo para calcular la distancia entre dos puntos
     * 
     * @param xi x inicial
     * @param yi y inicial
     * @param xf x final
     * @param yf y final
     * @return distancia en un valor entero redondeado hacia abajo que representa la
     *         distancia entre dos puntos
     */
    public static int distanciaPuntos(int xi, int yi, int xf, int yf) {
        return (int) Math.floor(Math.sqrt(Math.pow(xi - xf, 2) + Math.pow(yi - yf, 2)));
    }

    /**
     * Metodo que dado un pixel nos dice si se encuentra dentro de la circunferencia
     * descrita en los parametros
     * 
     * @param centroX centro en x de la circunferencia
     * @param centroY centro en y de la circunferencia
     * @param radio   radio de la circunferencia
     * @param x       cordenadas del pixel a verificar en x
     * @param y       cordenadas del pixel a verificar en y
     * @return true si se encuentra dentro del circulo, false en caso contrario
     */
    public static boolean dentroCirculo(int centroX, int centroY, int radio, int x, int y) {
        return (int) Math.pow(x - centroX, 2) + (int) Math.pow(y - centroY, 2) <= Math.pow(radio, 2);
    }

}