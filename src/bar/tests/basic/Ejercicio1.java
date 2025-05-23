package tests.basic;

import app.Cliente;
import app.Pruebas;
import grafo.GrafoMA;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import app.GradBar;
import tests.FormateadorTexto;

public class Ejercicio1 {
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
    // --> Ejecuta tras limpiar los prints para comprobar
    //@Test
    public void testEjercicio1_Text() {
        // Ejecutar el metodo del ejercicio 1
        Pruebas.ejercicio_1();

        String salidaEsperada =
                "--------- Ejercicio 1 ---------\n" +
                        "-- Información de la red de GradBar --\n" +
                        "Cliente 8 {Clara, edad: 16, bebida: Agua}\n" +
                        "Cliente 5 {Guillermo, edad: 21, bebida: Refresco de limón}\n" +
                        "El grafo contiene 2 vertices\n" +
                        "No es dirigido\n" +
                        "F T\n" +
                        "T F\n";

        String salidaReal = output.toString().replace("\r\n", "\n");
        String normEsperada = FormateadorTexto.normalizaTexto(salidaEsperada);
        String normObtenida = FormateadorTexto.normalizaTexto(salidaReal);
        try {
            assertEquals("La salida del ejercicio 1 no coincide con la esperada", normEsperada, normObtenida);
        } catch (AssertionError e) {
            FormateadorTexto.printDiferencias("Ejercicio 1", normObtenida, normEsperada);
            throw e;
        }
    }
    @Test
    public void testEjercicio1() {
        System.out.println("--------- Ejercicio 1 ---------");

        // Paso 1: Crear Clientes
        System.out.println("-- Crear Clientes --");
        Cliente clara = new Cliente("Clara", 8, 16, "Agua");
        Cliente guille = new Cliente("Guillermo", 5, 21, "Refresco de limón");

        // Comprobar el toString de cada cliente
        String expClara = "Cliente 8 {Clara, edad: 16, bebida: Agua}";
        String expGuille = "Cliente 5 {Guillermo, edad: 21, bebida: Refresco de limón}";
        if (!clara.toString().equals(expClara)) {
            System.out.println("Error: toString de Clara incorrecto. Obtenido: " + clara.toString());
        }
        if (!guille.toString().equals(expGuille)) {
            System.out.println("Error: toString de Guillermo incorrecto. Obtenido: " + guille.toString());
        }

        // Imprimir clientes
        System.out.println(clara);
        System.out.println(guille);

        // Paso 2: Crear red
        System.out.println("-- Crear red --");
        Cliente[] clientes = new Cliente[] { clara, guille };
        GradBar red = new GradBar(clientes);

        // Comprobar que GradBar tiene el número correcto de clientes
        if (red.getNumClientes() != 2) {
            System.out.println("Error: GradBar no tiene 2 clientes, tiene: " + red.getNumClientes());
        } else {
            System.out.println("GradBar creado correctamente con 2 clientes.");
        }

        // Paso 3: Conectar Clientes
        System.out.println("-- Conectar Clientes --");
        red.conectarClientes(clara, guille);

        // Comprobar que la conexión se ha establecido
        int idxClara = red.getIndice(clara);
        int idxGuille = red.getIndice(guille);
        try {
            Field fieldRed = GradBar.class.getDeclaredField("red");
            fieldRed.setAccessible(true);
            GrafoMA grafo = (GrafoMA) fieldRed.get(red);
            if (!grafo.existeArista(idxClara, idxGuille)) {
                System.out.println("Error: No se ha conectado a Clara con Guillermo en el grafo.");
            } else {
                System.out.println("Clientes conectados correctamente en el grafo.");
            }
        } catch (Exception e) {
            System.out.println("Error al acceder al grafo interno: " + e.getMessage());
        }

        // Paso 4: Mostrar información de la red
        System.out.println("-- Información de la red de GradBar --");
        red.mostrar();
    }

    @Test
    public void testClienteDuplicado() {
        Cliente clara1 = new Cliente("Clara", 8, 16, "Agua");
        Cliente clara2 = new Cliente("Clara", 8, 16, "Agua");
        boolean iguales = clara1.equals(clara2);
        assertTrue("Los clientes con la misma información deberían ser iguales", iguales);
    }

    @Test
    public void testClienteDistinto(){
        Cliente clara1 = new Cliente("Clara", 8, 16, "Agua");
        Cliente clara2 = new Cliente("Clara", 81, 16, "Agua");
        boolean iguales = clara1.equals(clara2);
        assertFalse("Los clientes con distinto ID deberían ser distintos", iguales);
    }
}

