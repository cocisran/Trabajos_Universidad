package almacen;

import java.util.Random;
import java.util.Scanner;
import java.io.*;

public class GeneradorInventario {

    private FileWriter fichero;
    private String ruta;
    private int contador;

    /** Constructor por omision */
    public GeneradorInventario() {
        fichero = null;
        ruta = null;
    }

    /**
     * Constructor que usa la ruta pasada como parametro para guardar el almacen
     * generado en esa posicion
     * 
     * @param ruta ruta donde se desea guardar el almacen
     */
    public GeneradorInventario(String ruta) {
        fichero = null;
        this.ruta = ruta;
    }

    /**
     * Crea el almacen ya sea en el directorio por default: ./Inventario.txt o en en
     * la ruta pasada por parametro
     */
    public void CrearAlmacen() {
        this.abrirBufer();
        String[] inventario = { dulces(), misceleano(), frutasYverduras(), ropa(), panaderia(), salchichoneria(),
                electrodomesticos(), salud(), papeleria() };
        for (String categoria : inventario) {
            escritor(categoria);
        }
        this.cerrarBufer();
    }

    private String dulces() {
        String exit = "";
        String[] dulces = { "chocolates", "paletas", "chicles", "gomitas", "caramelos", "caramelo-suave", "chiclosos" };
        String[] marcas_dulces = { "hersheys", "nestle", "turin", "De La Rosa", "Sonricks", "La abuelita" };
        String[] sabor = { "menta", "fresa", "vainilla", " ", "cacahuate", "cajeta" };
        String[] complemento = { "5 gr", "15 gr", "55 gr", "250 gr", "500 gr", "1000 gr" };
        double valorxGramo = 0;
        Random cantidad = new Random();
        for (int i = 0; i < dulces.length; i++) {
            for (int j = 0; j < sabor.length; j++) {
                for (int k = 0; k < marcas_dulces.length; k++) {
                    for (int l = 0; l < complemento.length; l++) {
                        if (l == 0) {
                            valorxGramo = valor_unidad(.25, 3);
                        }
                        String valor = "" + (transformarMedida(complemento[l], "gr") * valorxGramo);
                        exit += indentificador() + ", " + dulces[i] + " " + sabor[j] + " " + marcas_dulces[k] + " "
                                + complemento[l] + ", $" + valor + ", " + (cantidad.nextInt(200) + 5) + "\n";

                    }
                }
            }
        }
        return exit;
    }

    private String misceleano() {
        String exit = "";
        String[] producto = { "mermelada", "mayonesa", "mostaza", "catsup", "vinagre", "salsa inglesa", "salsa de soya",
                "salsa picante", "aderezo" };
        String[] marca = { "McCormik", "Heinz", "Hellmanns", "Nacional", "La costeña", "kraft" };
        String[] presentacion = { "125ml", "255 ml", "500 ml", "1000 ml" };
        double valorxMl = 0;
        Random cantidad = new Random();

        for (int i = 0; i < producto.length; i++) {
            for (int j = 0; j < marca.length; j++) {
                for (int l = 0; l < presentacion.length; l++) {
                    if (l == 0) {
                        valorxMl = valor_unidad(.25, 1);
                    }
                    String valor = "" + (transformarMedida(presentacion[l], "ml") * valorxMl);
                    exit += indentificador() + ", " + producto[i] + " " + marca[j] + " " + presentacion[l] + ", $"
                            + valor + ", " + (cantidad.nextInt(200) + 5) + "\n";

                }
            }
        }
        return exit;
    }

    private String frutasYverduras() {
        String exit = "";
        String[] frutasVerduras = { "Manzanas", "Aguacates", "Platanos", "Coco", "Kiwi", "Limones", "Papayas", "Tuna",
                "Fresa", "Uvas", "Peras", "Naranjas", "Mangos", "Mandarinas", "Sandia", "Melon", "Toronja", "Jamaica",
                "Tamarindo", "Piña", "Cerezas", "Frijoles", "Arroz", "Zanahoria", "Rabano", " Cebolla", "Espinaca",
                "Brocoli", "Coliflor", "Pimientos", "Pepino" };
        String[] origen = { "nacional", "importacion" };
        Random precio = new Random();
        int valor = 0;

        for (int i = 0; i < frutasVerduras.length; i++) {
            valor = precio.nextInt(78) + 14;
            for (int j = 0; j < origen.length; j++) {
                if (j == 1) {
                    valor += precio.nextInt(10) + 5;
                }
                exit += indentificador() + ", " + frutasVerduras[i] + " " + origen[j] + ", $" + valor + ", "
                        + (precio.nextInt(1000) + 200) + "\n";
            }
        }

        return exit;
    }

    private String ropa() {
        String exit = "";
        String[] tipo = { "Chamarra", "Chamarra formal", "Chamarra algodon", "Sudadera", "Sueter", "Chaleco",
                "Chaleco deportivo", "Saco", "Playera", "Camisa", "Camiseta", "Camiseta deportiva", "Player estampada",
                "Sueter estampado", "Impermeable", "Zapato", " Tennis", " Calcetines", "Cacetines pack", "Bermuda",
                "Pantalones", "Pantalon formal", " Pantalon vestir", "Pantalon mezclilla", "Short", "Short deportivo" };
        String[] color = { "negro", "azul", "gris", "verde", "rosa", "blanco", "morado", "beige", "rojo" };
        String[] talla = { "s", "m", "l", "xl", "xxl" };
        String[] genero = { "hombre", "mujer", "unisex", "infantil" };
        Random precio = new Random();
        int valor = 0;
        for (int i = 0; i < tipo.length; i++) {
            valor = precio.nextInt(955) + 85;
            for (int j = 0; j < color.length; j++) {
                for (int k = 0; k < talla.length; k++) {
                    for (int l = 0; l < genero.length; l++) {
                        exit += indentificador() + ", " + tipo[i] + " " + color[j] + " " + talla[k] + "-" + genero[l]
                                + ", $" + (valor - (l == genero.length - 1 ? valor / 10 : 0)) + ", "
                                + (precio.nextInt(150) + 25) + "\n";
                    }
                }
            }
        }
        return exit;
    }

    private String panaderia() {
        String exit = "";
        String[] panes = { "Concha", "Cuernito", " Campechana", " Panque", " Oreja", " Ojaldra", "Pastel", "Merengue",
                "Galleta" };
        String[] sabor = { "", "vainilla", "fresa", "chocolate", "integral" };
        String[] pieza = { "pza", "6 pza", "12 pza" };
        double valor = 0;
        Random precio = new Random();
        for (int i = 0; i < panes.length; i++) {
            valor = this.valor_unidad(0.5, 8);
            for (int j = 0; j < sabor.length; j++) {
                for (int k = 0; k < pieza.length; k++) {
                    exit += indentificador() + ", " + panes[i] + " " + sabor[j] + " " + pieza[k] + ", $"
                            + (valor * (k == 0 ? 1 : (6 * k))) + "," + (precio.nextInt(100) + 20) + "\n";
                }
            }
        }
        return exit;
    }

    private String salchichoneria() {
        String exit = "";
        String[] producto = { "Salchicha", "Salami", "Jamon", "Mortadela", "Chorizo", "Tocino", "Carne molida",
                "Filete" };
        String[] tipo = { " ", " light", "bajo en sal", "vegano" };
        String[] carne = { "res", "pollo", "cerdo", "soya" };
        String[] peso = { "125 gr", "250 gr", "500 gr", "1000 gr" };
        int valor = 0;
        Random precio = new Random();

        for (int i = 0; i < producto.length; i++) {
            for (int j = 0; j < tipo.length; j++) {
                for (int k = 0; k < carne.length; k++) {
                    valor = precio.nextInt(45) + 25;
                    for (int l = 0; l < peso.length; l++) {
                        exit += indentificador() + ", " + producto[i] + " " + tipo[j] + " de " + carne[k] + " "
                                + peso[l] + ", $" + valor * (l == 0 ? 1 : l + 1) + "," + (precio.nextInt(200) + 20)
                                + "\n";
                    }
                }
            }
        }
        return exit;
    }

    private String electrodomesticos() {
        String exit = "";

        // cocina y lavado
        String[] producto = { "Lavadora", "Cafetera", "Tostadora", "Estufa", "Refrigerador", "Horno", "Licuadora",
                "Batidora", "Sandwichera", "Aspiradora" };
        String[] marca = { "Philips", "Samsung", "LG", "Koblenz", "Mabe", "Daewoo" };
        String[] tipo = { " ", "premiun" };
        Random precio = new Random();
        int valor = 0;
        for (int i = 0; i < producto.length; i++) {
            for (int j = 0; j < marca.length; j++) {
                for (int k = 0; k < tipo.length; k++) {
                    valor = precio.nextInt(8000) + 850;
                    exit += indentificador() + ", " + producto[i] + " " + marca[j] + " de " + tipo[k] + ", $" + valor
                            + "," + (precio.nextInt(30) + 20) + "\n";
                }
            }
        }

        // televisiones
        String[] marcaTV = { "Samsung", "Sony", "LG", "Philips", "Noblex", "TCL", "RCA", "Hitachi", "BGH" };
        String[] dimension = { "24 in", "48 in", "84 in", "120 in", "240 in" };
        for (int i = 0; i < marcaTV.length; i++) {
            valor = precio.nextInt(20000) + 5850;
            for (int j = 0; j < dimension.length; j++) {
                valor += valor / 2 * j;
                exit += indentificador() + ", " + "Tv " + " " + marcaTV[i] + " de " + dimension[j] + ", $" + valor + ","
                        + (precio.nextInt(30) + 20) + "\n";
            }
        }
        // pc
        String[] tipoPC = { "escritorio", "laptop" };
        String[] marcaPc = { "Apple", "Asus", "Hp", "Dell", "Lenovo", "Acer", "MSI", "Toshiba", "Alienware" };
        for (int i = 0; i < tipoPC.length; i++) {
            for (int j = 0; j < marcaPc.length; j++) {
                valor = precio.nextInt(3000) + 4850;
                exit += indentificador() + ", " + "pc " + " " + tipoPC[i] + " de " + marcaPc[j] + ", $"
                        + valor * (1 + j) + "," + (precio.nextInt(30) + 5) + "\n";
            }
        }

        return exit;
    }

    private String salud() {
        String exit = "";
        String[] producto = { "Shampoo", "Acondicionador", "Desodorante", "Antitraspirante", "Gel" };
        String[] marca = { "Dove", "Blanco", "Plusbelle", "Dove", "Pantene", "P&G", "Elvive", "Eggo", "Clear",
                "Suave" };
        String[] olor = { "Pino", "Primavera", "Invierno", "Organico", "Avellana", "Coco", "Jalea Real", "Miel" };

        Random precio = new Random();
        int valor = 0;

        for (int i = 0; i < producto.length; i++) {
            for (int j = 0; j < marca.length; j++) {
                valor = precio.nextInt(80) + 20;
                for (int k = 0; k < olor.length; k++) {

                    exit += indentificador() + ", " + producto[i] + " " + marca[j] + " " + olor[k] + ", $" + valor + ","
                            + (precio.nextInt(500) + 200) + "\n";
                }
            }
        }

        return exit;
    }

    private String papeleria() {
        String exit = "";
        String[] producto = { "pluma", "lapiz", "borrador", "cinta", "sacapuntas", "paquete hojas" };
        String[] color = { "negro", "azul", "gris", "verde", "rosa", "blanco", "morado", "beige", "rojo" };

        Random precio = new Random();
        int valor = 0;

        for (int i = 0; i < producto.length; i++) {
            valor = (i == producto.length - 1 ? 40 : 3) + precio.nextInt(15);
            for (int j = 0; j < color.length; j++) {
                exit += indentificador() + ", " + producto[i] + " " + color[j] + ", $" + valor + ","
                        + (precio.nextInt(500) + 200) + "\n";
            }
        }
        return exit;
    }

    private double valor_unidad(double min, int variante) {
        Random p = new Random();
        return (p.nextInt(variante) * min) + min;
    }

    private int transformarMedida(String peso, String clase) {

        String salida = "";
        salida = peso.replaceAll(clase, "");
        salida = salida.trim();
        int num = Integer.parseInt(salida);
        return num;
    }

    private String indentificador() {
        String num = "" + contador;
        while (num.length() < 6) {
            num = "0" + num;
        }
        contador++;
        return num;
    }

    /**
     * Escribe el texto pasado por parametro en el archivo con el cual fue
     * inicializado el objeto. Arroja una excepcion si el archivo no se encuentra o
     * el directorio es incorrecto
     * 
     * @param text texto a escribir en el archivo
     */
    public void escritor(String text) {

        try {
            fichero.write(text);
        } catch (Exception ex) {
            System.out.println("Algo salio mal en la escritura:  " + ex.getMessage());
        }

    }

    /**
     * Abre el archivo con el cual se construyo objeto para escritura
     */
    private void abrirBufer() {
        try {
            if (ruta != null) {
                fichero = new FileWriter(ruta);
            } else {
                fichero = new FileWriter("Inventario.txt");
            }
        } catch (Exception e) {
            System.out.println("No encontramos el archivo de generacion del inventario");
        }

    }

    /**
     * Cierra el archivo para la escritura
     */
    private void cerrarBufer() {
        try {
            fichero.close();
        } catch (Exception e) {
            System.out.println("Algo salio mal:  " + e.getMessage());
        }

    }
}