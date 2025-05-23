package tests.extremos;

/*
>> No modificar <<
Estos tests comprueban la resolución correcta en casos extremos del ejercicio 1.
 */

import app.Pruebas;
import app.Cliente;
import app.GradBar;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Ejercicio1Extremos {
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(output));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setIn(null);
    }


    @Test
    public void testGrafoSinClientes() {
        GradBar barVacio = new GradBar(new Cliente[]{});
        assertEquals("El número de clientes en un grafo vacío debe ser 0", 0, barVacio.getNumClientes());
    }

    @Test
    public void testConexionInexistente() {
        Cliente clara = new Cliente("Clara", 8, 16, "Agua");
        Cliente guille = new Cliente("Guillermo", 5, 21, "Refresco de limón");
        GradBar bar = new GradBar(new Cliente[]{clara, guille});

        assertFalse("No se puede conectar un cliente que no existe", bar.conectarClientes(clara, new Cliente("Fake", 99, 30, "Vino")));
        assertFalse("No se puede conectar un cliente nulo", bar.conectarClientes(clara, null));
    }

    @Test
    public void testMostrarSinClientes() {
        GradBar barVacio = new GradBar(new Cliente[]{});
        barVacio.mostrar();
        String salidaReal = output.toString().trim();
        restoreStreams();
        System.out.printf("|"+salidaReal+"|");
        assertTrue("La salida de mostrar debe indicar que el grafo está vacío", salidaReal.contains("El grafo contiene 0 vertices"));
    }
}
