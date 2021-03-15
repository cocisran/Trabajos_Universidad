package test.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.lib.Lugar;
import main.lib.Conexion;

public class Pruebas {
    private Lugar ciudad;

    @BeforeEach
    public void limpiarCiudad() {
        ciudad = null;
    }

    @Test
    @DisplayName("Peticion vacia")
    public void peticionNula() {
        try {
            int i = Conexion.num_conexiones();
            Conexion.actualizarCiudad(ciudad);
            if (Conexion.num_conexiones() > i) {
                fail("La peticion vacia no debe solicitar una conexion");
            }
        } catch (Exception e) {
            fail("La peticion vacia debio ser ignorada");
        }
    }

    @Test
    @DisplayName("Autocompletado Datos Erroneos")
    public void datosErroneos() {
        ciudad = new Lugar("Sydney", "", "8000", "-89", "", "");
        Conexion.actualizarCiudad(ciudad);
        assertTrue(ciudad.getPais().equals("AU") && ciudad.getCoorLat().equals("-33.87")
                && ciudad.getCoorLong().equals("151.21") && !ciudad.getClima().isEmpty());

    }

    @Test
    @DisplayName("Busqueda de ciudad falsa")
    public void ciudadFalsa(){
        ciudad = new Lugar("Programalandia", "", "", "", "", "");
        Conexion.actualizarCiudad(ciudad);
        assertTrue(ciudad.getClima().equals("No disponible"));
    }

}