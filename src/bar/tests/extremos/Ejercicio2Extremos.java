package tests.extremos;

/*
>> No modificar <<
Estos tests comprueban la resolución correcta en casos extremos del ejercicio 2.
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
import java.util.ArrayList;

public class Ejercicio2Extremos {
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
    public void testGrafoSinConexiones() {
        Cliente juan = new Cliente("Juan", 0, 20, "Cerveza");
        Cliente jose = new Cliente("José", 1, 17, "Agua");
        Cliente eva = new Cliente("Eva", 2, 18, "Refresco de cola");

        GradBar bar = new GradBar(new Cliente[]{juan, jose, eva});
        assertEquals("Cada cliente debe ser su propio grupo", 3, bar.contarGrupos());
    }

    @Test
    public void testTodosConectados() {
        Cliente juan = new Cliente("Juan", 0, 20, "Cerveza");
        Cliente jose = new Cliente("José", 1, 17, "Agua");
        Cliente eva = new Cliente("Eva", 2, 18, "Refresco de cola");

        GradBar bar = new GradBar(new Cliente[]{juan, jose, eva});
        bar.conectarClientes(juan, jose);
        bar.conectarClientes(jose, eva);
        bar.conectarClientes(eva, juan);

        assertEquals("Todos los clientes conectados deben formar un solo grupo", 1, bar.contarGrupos());
    }

    @Test
    public void testGrupoMasGrandeUnico() {
        Cliente juan = new Cliente("Juan", 0, 20, "Cerveza");
        Cliente jose = new Cliente("José", 1, 17, "Agua");
        Cliente clara = new Cliente("Clara", 2, 16, "Agua");

        GradBar bar = new GradBar(new Cliente[]{juan, jose, clara});
        bar.conectarClientes(juan, jose);
        bar.conectarClientes(jose, clara);

        ArrayList<Cliente> grupo = bar.mayorGrupo();
        assertEquals("El grupo más grande debe contener los 3 clientes", 3, grupo.size());
    }

    @Test
    public void testGrupoBebida() {
        Cliente juan = new Cliente("Juan", 0, 20, "Cerveza");
        Cliente jose = new Cliente("José", 1, 17, "Agua");
        Cliente clara = new Cliente("Clara", 2, 16, "Agua");

        GradBar bar = new GradBar(new Cliente[]{juan, jose, clara});
        bar.conectarClientes(juan, jose);
        bar.conectarClientes(jose, clara);

        String bebida = bar.bebidaGrupo(clara);
        assertEquals("El menor del grupo es Clara con 16 años, su bebida favorita debe ser Agua", "Agua", bebida);
    }
}
