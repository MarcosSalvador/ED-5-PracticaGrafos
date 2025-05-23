package tests.basic;

/*
>> No modificar <<
Estos tests comprueban la resolución correcta de los ejercicios propuestos para los ejemplos del enunciado.
 */

import app.Cliente;
import app.GradBar;
import app.Pruebas;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;

public class Ejercicio2 {
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


    public void testEjercicio2Text() {
        Pruebas.ejercicio_2(); // Ejecutar el metodo del ejercicio 2

        String salidaEsperada =
                "--------- Ejercicio 2 ---------\n" +
                        "Cliente 0 {Juan, edad: 20, bebida: Cerveza}\n" +
                        "Cliente 1 {José, edad: 17, bebida: Agua}\n" +
                        "Cliente 2 {Eva, edad: 18, bebida: Refresco de cola}\n" +
                        "Cliente 3 {Alicia, edad: 24, bebida: Refresco de naranja}\n" +
                        "Cliente 4 {Ernesto, edad: 17, bebida: Cerveza}\n" +
                        "Cliente 5 {Guillermo, edad: 21, bebida: Refresco de limón}\n" +
                        "Cliente 6 {Alberto, edad: 22, bebida: Refresco de cola}\n" +
                        "Cliente 7 {Lucas, edad: 19, bebida: Cerveza}\n" +
                        "Cliente 8 {Clara, edad: 16, bebida: Agua}\n" +
                        "Cliente 9 {Rosa, edad: 18, bebida: Cerveza sin alcohol}\n" +
                        "El grafo contiene 10 vertices\n" +
                        "No es dirigido\n" +
                        "F T F F T T F F F F\n" +
                        "T F F F T F F F F F\n" +
                        "F F F F F F F T F T\n" +
                        "F F F F F F F F F F\n" +
                        "T T F F F T F F F F\n" +
                        "T F F F T F F F F F\n" +
                        "F F F F F F F F T F\n" +
                        "F F T F F F F F F T\n" +
                        "F F F F F F T F F F\n" +
                        "F F T F F F F T F F\n" +
                        "--------------------------------\n" +
                        "Cliente 0 {Juan, edad: 20, bebida: Cerveza}\n" +
                        "Cliente 1 {José, edad: 17, bebida: Agua}\n" +
                        "Cliente 5 {Guillermo, edad: 21, bebida: Refresco de limón}\n" +
                        "Ernesto tiene 3 amigos\n" +
                        "--------------------------------\n" +
                        "Alicia tiene 0 amigos\n" +
                        "--------------------------------\n" +
                        "Cliente 0 {Juan, edad: 20, bebida: Cerveza}\n" +
                        "Cliente 2 {Eva, edad: 18, bebida: Refresco de cola}\n" +
                        "Cliente 3 {Alicia, edad: 24, bebida: Refresco de naranja}\n" +
                        "Cliente 6 {Alberto, edad: 22, bebida: Refresco de cola}\n" +
                        "En el Rincón de la Tapa tenemos 4 grupos de amigos\n" +
                        "--------------------------------\n" +
                        "El grupo más grande es de 4 personas, que son:\n" +
                        "Cliente 0 {Juan, edad: 20, bebida: Cerveza}\n" +
                        "Cliente 1 {José, edad: 17, bebida: Agua}\n" +
                        "Cliente 4 {Ernesto, edad: 17, bebida: Cerveza}\n" +
                        "Cliente 5 {Guillermo, edad: 21, bebida: Refresco de limón}\n" +
                        "--------------------------------\n" +
                        "Bebida del grupo de jose: Cerveza\n" +
                        "Bebida del grupo de clara: Refresco de cola\n";

        String salidaReal = output.toString().replace("\r\n", "\n"); // Normalizar saltos de línea
        assertEquals("La salida del ejercicio 2 no coincide con la esperada", salidaEsperada, salidaReal);
    }
    @Test
    public void testEjercicio2() {
        // Crear clientes
        Cliente juan = new Cliente("Juan", 0, 20, "Cerveza");
        Cliente jose = new Cliente("José", 1, 17, "Agua");
        Cliente eva = new Cliente("Eva", 2, 18, "Refresco de cola");
        Cliente alicia = new Cliente("Alicia", 3, 24, "Refresco de naranja");
        Cliente ernesto = new Cliente("Ernesto", 4, 17, "Zumo de naranja");
        Cliente guillermo = new Cliente("Guillermo", 5, 21, "Refresco de limón");
        Cliente alberto = new Cliente("Alberto", 6, 22, "Refresco de cola");
        Cliente lucas = new Cliente("Lucas", 7, 19, "Cerveza");
        Cliente clara = new Cliente("Clara", 8, 16, "Agua");
        Cliente rosa = new Cliente("Rosa", 9, 18, "Cerveza sin alcohol");

        Cliente[] clientes = new Cliente[]{juan, jose, eva, alicia, ernesto, guillermo, alberto, lucas, clara, rosa};
        GradBar red = new GradBar(clientes);

        // Conexiones
        red.conectarClientes(juan, jose);
        red.conectarClientes(juan, ernesto);
        red.conectarClientes(juan, guillermo);
        red.conectarClientes(jose, ernesto);
        red.conectarClientes(ernesto, guillermo);
        red.conectarClientes(eva, lucas);
        red.conectarClientes(eva, rosa);
        red.conectarClientes(lucas, rosa);
        red.conectarClientes(alberto, clara);

        // Contar grupos
        int grupos = red.contarGrupos();
        assertEquals("Debe haber 4 grupos", 4, grupos);

        // Amigos
        int amigosAlicia = red.mostrarAmigos(alicia);
        assertEquals("Alicia debe tener 0 amigos", 0, amigosAlicia);

        int amigosErnesto = red.mostrarAmigos(ernesto);
        assertEquals("Ernesto debe tener 3 amigos", 3, amigosErnesto);

        // Mayor grupo
        ArrayList<Cliente> obtenido = red.mayorGrupo();
        ArrayList<Cliente> esperado = new ArrayList<Cliente>();
        esperado.add(juan);
        esperado.add(jose);
        esperado.add(ernesto);
        esperado.add(guillermo);
        assertEquals("El grupo más grande debe tener 4 miembros", 4, obtenido.size());
        for (Cliente c : esperado) {
            assertTrue("Falta " + c + " en el grupo más grande", obtenido.contains(c));
        }

        // Bebida del grupo más grande
        String bebida = red.bebidaGrupo(jose);
        assertEquals("La bebida favorita en general es la cerveza", "Cerveza", bebida);
    }
}

