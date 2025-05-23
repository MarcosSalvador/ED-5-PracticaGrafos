package app;

import java.util.ArrayList;
import java.util.Iterator;

public class Pruebas {
    public static void main(String[] args) {
        System.out.println("Práctica 5 grafos");
        ejercicio_1();
        ejercicio_2();
    }

    // Completar ejercicio_1
    public static void ejercicio_1() {
        System.out.println("--------- Ejercicio 1 ---------");

        Cliente clara = new Cliente("Clara", 8, 16, "Agua");
        Cliente guillermo = new Cliente("Guillermo", 5, 21, "Refresco de limón");

        Cliente[] clientes = {clara, guillermo};

        GradBar bar = new GradBar(clientes);
        bar.conectarClientes(clara, guillermo);

        bar.mostrar();
        bar.mostrarGrafico();
    }


    // Completar ejercicio_2
    public static void ejercicio_2() {
        System.out.println("--------- Ejercicio 2 ---------");

        Cliente[] clientes = new Cliente[]{
                new Cliente("Juan", 0, 20, "Cerveza"),
                new Cliente("José", 1, 17, "Agua"),
                new Cliente("Eva", 2, 18, "Refresco de cola"),
                new Cliente("Alicia", 3, 24, "Refresco de naranja"),
                new Cliente("Ernesto", 4, 17, "Cerveza"),
                new Cliente("Guillermo", 5, 21, "Refresco de limón"),
                new Cliente("Alberto", 6, 22, "Refresco de cola"),
                new Cliente("Lucas", 7, 19, "Cerveza"),
                new Cliente("Clara", 8, 16, "Agua"),
                new Cliente("Rosa", 9, 18, "Cerveza sin alcohol")
        };

        GradBar bar = new GradBar(clientes);

        bar.conectarClientes(clientes[0], clientes[1]);
        bar.conectarClientes(clientes[0], clientes[4]);
        bar.conectarClientes(clientes[0], clientes[5]);
        bar.conectarClientes(clientes[1], clientes[4]);
        bar.conectarClientes(clientes[4], clientes[5]);

        bar.conectarClientes(clientes[2], clientes[7]);
        bar.conectarClientes(clientes[2], clientes[9]);
        bar.conectarClientes(clientes[6], clientes[8]);
        bar.conectarClientes(clientes[7], clientes[9]);

        bar.mostrar();
        bar.mostrarGrafico();

        System.out.println("--------------------------------");
        System.out.println("Amigos de Ernesto:");
        int numAmigosErnesto = bar.mostrarAmigos(clientes[4]);
        System.out.println("Ernesto tiene " + numAmigosErnesto + " amigos");

        System.out.println("--------------------------------");
        System.out.println("Amigos de Alicia:");
        int numAmigosAlicia = bar.mostrarAmigos(clientes[3]);
        System.out.println("Alicia tiene " + numAmigosAlicia + " amigos");

        System.out.println("--------------------------------");
        int grupos = bar.contarGrupos();
        System.out.println("En el Rincón de la Tapa tenemos " + grupos + " grupos de amigos");

        System.out.println("--------------------------------");
        ArrayList<Cliente> grupoMayor = bar.mayorGrupo();
        System.out.println("El grupo más grande es de " + grupoMayor.size() + " personas, que son:");
        for (Cliente c : grupoMayor) {
            System.out.println(c);
        }

        System.out.println("--------------------------------");
        System.out.println("Bebida del grupo de José: " + bar.bebidaGrupo(clientes[1]));
        System.out.println("Bebida del grupo de Clara: " + bar.bebidaGrupo(clientes[8]));
    }
}
