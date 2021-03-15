package main.java.app;

import main.java.sub.matriculas.Materia;
import main.java.sub.matriculas.Estudiante;
import main.java.sub.libs.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Main {

    private static Lista<Estudiante> estudiantes = new Lista<>();
    private static Lista<Materia> materias = new Lista<>();

    public static void main(String[] args) {
        int op = -2, opc2 = -1;
        incio();
        do {
            if (opc2 == -1) {
                op = menuPrincipal();
            }
            switch (op) {
                case 1:
                    opc2 = menuEstudiantes(); // mostrar alumno
                    if (opc2 != -1)
                        mostrarEstudiante(opc2);
                    break;
                case 2:
                    opc2 = menuMaterias(); // mostrar materia
                    if (opc2 != -1) {
                        mostrarMateria(opc2);
                    }
                    break;
                case 3:
                    opc2 = menuEstudiantes(); // editar numero de cuenta
                    if (opc2 != -1) {
                        actualizarNumCuenta(opc2);
                    }
                    break;
                case 4:
                    opc2 = menuMaterias(); // editar clave de materia
                    if (opc2 != -1) {
                        actualizarClaveMateria(opc2);
                    }
                    break;
                case 5:
                    opc2 = menuMaterias(); // editar profesor
                    if (opc2 != -1) {
                        actualizarProfesor(opc2);
                    }
                    break;
                case 0:
                    System.out.println("¡Hasta pronto!");
                    break;
                default:
                    break;
            }
            guardar();
        } while (op != 0);

    }

    private static void incio() {
        try {
            // recuperar archivos de serializacion si es que existen
            FileInputStream respaldo;
            ObjectInputStream entrada = null;

            respaldo = new FileInputStream("archivos/respaldo.dat");
            entrada = new ObjectInputStream(respaldo);
            estudiantes = (Lista<Estudiante>) entrada.readObject();
            materias = (Lista<Materia>) entrada.readObject();

            entrada.close();

        } catch (Exception e) {
            System.out.println("Buscando el respaldo");
            primeraEjecucion();
        }

    }

    private static void guardar() {
        try {
            FileOutputStream respaldo;
            ObjectOutputStream salida = null;

            respaldo = new FileOutputStream("archivos/respaldo.dat");

            salida = new ObjectOutputStream(respaldo);
            salida.writeObject(estudiantes);
            salida.writeObject(materias);
            salida.close();

        } catch (Exception e) {
            System.out.println("NO ES POSIBLE ACTUALIZAR EL REGISTRO DE LA BASE DE DATOS");
            e.printStackTrace();
        }
    }

    private static void actualizarNumCuenta(int index) {
        Estudiante e = estudiantes.getElemento(index);
        String numeroCuenta;

        System.out.println("Digite el numero de cuenta para : " + e.obtenerNombre() + "\n longitud de 10 digitos");
        numeroCuenta = leerClave("numero cuenta", 10);
        for (Estudiante n : estudiantes) {
            if (n.obtenerNumeroCta().equals(numeroCuenta)) {
                System.out.println("**Ya existe un alumno asociado a ese número de cuenta**");
                pausa();
                return;
            }
        }
        e.asignarNumeroCuenta(numeroCuenta);

    }

    private static void actualizarClaveMateria(int index) {
        Materia m = materias.getElemento(index);
        String clave;

        System.out.println("Digite la clave de materia para: " + m.obtenerNombre() + "\n longitud de 5   digitos");
        clave = leerClave("clave de materia", 5);
        for (Materia c : materias) {
            if (c.obtenerClave().equals(clave)) {
                System.out.println("**Ya existe una materia asociada a esa clave**");
                pausa();
                return;
            }
        }
        m.asignarClave(clave);

    }

    private static void actualizarProfesor(int index) {
        Materia m = materias.getElemento(index);
        Scanner in = new Scanner(System.in);
        String nombre;

        System.out.println("Digite el nombre del profesor para para: " + m.obtenerNombre());
        nombre = in.nextLine();
        if (nombreValido(nombre)) {
            m.asignarProfesor(nombre);
        } else {
            System.out.println("Digite un nombre valido , e intente de nuevo");
        }

    }

    private static boolean nombreValido(String nom) {
        if (nom == null) {
            return false;
        }
        for (int i = 0; i < nom.length(); i++) {
            if (Character.isDigit(nom.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static void mostrarEstudiante(int index) {

        Estudiante e = estudiantes.getElemento(index);
        System.out.println(e.toString());
        pausa();

    }

    private static void mostrarMateria(int index) {

        Materia m = materias.getElemento(index);
        System.out.println(m.toString());
        pausa();

    }

    private static int menuPrincipal() {
        int op = 0;

        System.out.println("**Bienvenido al sistema temporal de alumnos de ciencias de la computacion**");
        System.out.println(
                "\tSelecciona una opcion\n\n1) Consultar alumno\n2) Consultar materia\n3) Asignar Numero de cuenta"
                        + "\n4) Asginar clave de asignatura\n5) Asignar profesor de asignatura \n:s) Salir del programa");

        do {
            op = leerOpcion(5);
        } while (op < -1 || op > 5);

        return op;
    }

    private static int menuEstudiantes() {
        int i = 1, op = -2;
        String entrada = "";
        Scanner in = new Scanner(System.in);

        System.out.println("[LISTA DE ALUMNOS]");
        System.out.println("op Ncta\tNombre");
        for (Estudiante e : estudiantes) {
            System.out.println(i + ") " + e.toStringSimple());
            i++;
        }
        System.out.println(":b) VOLVER");
        do {
            op = leerOpcion(estudiantes.longitud());
        } while (op < 0 || op > estudiantes.longitud());

        return op - 1;

    }

    private static int menuMaterias() {
        int i = 1, op = -2;

        System.out.println("[LISTA DE MATERIAS]");
        System.out.println("op \tclave\t\tNombre\t\t\tProfesor");
        for (Materia e : materias) {
            System.out.println(i + ") " + e.toStringSimple());
            i++;
        }
        System.out.println(":b) VOLVER");
        do {
            op = leerOpcion(materias.longitud());
        } while (op < 0 || op > materias.longitud());
        return op - 1;
    }

    private static int leerOpcion(int sup) {
        int op = -2;
        String entrada = "";
        Scanner in = new Scanner(System.in);

        System.out.print("opción > ");
        entrada = in.nextLine();
        try {
            op = Integer.parseInt(entrada);
            if (op < 1 || op > sup) {
                System.out.println("**La opcion no es valida**");
                op = -2;
            }
        } catch (NumberFormatException e) {
            if (entrada.toLowerCase().equals(":s") || entrada.toLowerCase().equals(":b")) {
                op = 0;
            } else {
                System.out.println("**La opcion no es valida**");

            }
        }
        return op;

    }

    private static String leerClave(String tipo, int longitud) {
        String entrada;
        Scanner in = new Scanner(System.in);
        long aux = 0;

        System.out.print(tipo + "> ");
        entrada = in.nextLine();
        try {
            aux = Long.parseLong(entrada);
            if (longitud == entrada.length()) {
                return entrada;
            }
            System.out.println("**La longitud de la clave debe ser " + longitud + "**");
            return leerClave(tipo, longitud);
        } catch (NumberFormatException e) {
            System.out.println("**La clave debe ser una cadena numerica**");
            return leerClave(tipo, longitud);
        }

    }

    /**
     * Recupera la informacion guardada en el archivo de texto
     */
    private static void primeraEjecucion() {
        try {
            Scanner input = new Scanner(new File("archivos/alumnos.txt"));
            while (input.hasNextLine()) {
                String line = input.nextLine();

                line = line.substring(1, line.length() - 1);

                String[] partido = line.split(",", 0);
                Estudiante n = new Estudiante(partido[0]);
                Materia m = new Materia(partido[1]);
                int index = estudiantes.indiceDe(n);

                if (index == -1)
                    estudiantes.agregar(n);
                else
                    n = estudiantes.getElemento(index);

                index = materias.indiceDe(m);

                if (index == -1)
                    materias.agregar(m);
                else
                    m = materias.getElemento(index);

                n.agregarMateria(m);
                m.agregarEstudiante(n);

            }
            input.close();
            estudiantes = Lista.mergesort(estudiantes);
            materias = Lista.mergesort(materias);
            System.out.println("Respaldo creado exitosamente");
        } catch (Exception ex) {
            try {
                File miDir = new File(".");
                System.out.println(
                        "No se encontro ningun archivo de recuperacion, porfavor verifique su existencia en el directorio : "
                                + miDir.getCanonicalPath() + "/archivos");
                System.exit(0);
            } catch (Exception f) {
            }

        }
    }

    private static void pausa() {
        System.out.println("\nPRESIONE ENTER PARA CONTINUAR");
        Scanner in = new Scanner(System.in);
        String a;
        a = in.nextLine();
    }
}
