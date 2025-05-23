package grafo;

import java.util.ArrayDeque;

public class GrafoMA {
    private int numVertices;          // Número de vértices.
    private boolean dirigido;
    private boolean[][] matrizAdy;    // Matriz de adyacencias del grafo.

    public GrafoMA(int numVertices, boolean dirigido) { // Grafo sin aristas
        this.dirigido = dirigido;
        this.numVertices = numVertices;
        matrizAdy = new boolean[numVertices][numVertices];
        for (int i = 0; i < numVertices; i++)
            for (int j = 0; j < numVertices; j++)
                matrizAdy[i][j]=false;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public boolean getDirigido() {
        return dirigido;
    }

    public void insertarArista(int u, int v) {
        if (this.verticeEnRango(u) && this.verticeEnRango(v)) {
            matrizAdy[u][v] = true;
            if (!dirigido) {
                matrizAdy[v][u] = true;
            }
        }
    }

    private boolean verticeEnRango(int v) {
        return (v < numVertices) && (v >= 0);
    }


    public void eliminarArista(int u, int v) {
        if (this.verticeEnRango(u) && this.verticeEnRango(v)) {
            matrizAdy[u][v] = false;
            if (!dirigido) {
                matrizAdy[v][u] = false;
            }
        }
    }

    public boolean existeArista(int u, int v) {
        if (this.verticeEnRango(u) && this.verticeEnRango(v)) {
            return matrizAdy[u][v];
        } else {
            return false;
        }
    }

    public int gradoEntrada(int v) {
        int resultado = 0;
        if (verticeEnRango(v)) {
            for (int i = 0; i < numVertices; i++) {     //Recorrer columna v
                if (matrizAdy[i][v]) {
                    resultado++;
                }
            }
        }
        return resultado;
    }

    public int gradoSalida(int v) {
        int resultado = 0;
        if (verticeEnRango(v)) {
            for (int j = 0; j < numVertices; j++) {     //Recorrer fila v
                if (matrizAdy[v][j]) {
                    resultado++;
                }
            }
        }
        return resultado;
    }

    public int incidencia(int v) {
        int resultado = 0;
        if (verticeEnRango(v)) {
            if (!dirigido) {
                resultado = this.gradoEntrada(v);
            } else {
                resultado = this.gradoEntrada(v) + this.gradoSalida(v);
                if (this.existeArista(v, v)) {
                    resultado--;
                }
            }
        }
        return resultado;
    }

    public int numAristas() {   // Número total de aristas del grafo
        int aristas = 0;
        for (int i = 0; i < numVertices; i++) {
            aristas += this.incidencia(i);
        }
        int bucles = 0;
        for (int i = 0; i < numVertices; i++) {
            if (this.existeArista(i, i)) {
                bucles++;
            }
        }
        int aristasDuplicadas = (aristas - bucles) / 2;
        return aristas - aristasDuplicadas;
    }


    public void mostrar() {
        System.out.println("El grafo contiene " + numVertices + " vertices");
        if (dirigido) {
            System.out.println("Es dirigido");
        } else {
            System.out.println("No es dirigido");
        }
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (matrizAdy[i][j]) {
                    System.out.print("T ");
                } else {
                    System.out.print("F ");
                }
            }
            System.out.println();
        }
    }

    public boolean[] profundidadDesdeVertice(int v) {
        boolean[] visitados = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visitados[i] = false;
        }
        if (this.verticeEnRango(v)) {
            // Realizar recorrido desde el vértice v.
            this.profundidadDesdeVerticeR(v, visitados);
        }
        return visitados;
    }


    private void profundidadDesdeVerticeR(int v, boolean[] visitados) {
        visitados[v] = true;   // Nodo v visitado
        // System.out.println("Alcanzado el " + v);
        // Recorrido desde adyacentes a v y no visitados aún.
        for (int i = 0; i < numVertices; i++) {
            if (this.existeArista(v, i) && !visitados[i]) {
                this.profundidadDesdeVerticeR(i, visitados);
            }
        }
    }

    public boolean[] amplitudDesdeVertice(int v) {
        boolean[] visitados = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visitados[i] = false;
        }
        ArrayDeque<Integer> cola = new ArrayDeque<Integer>();
        cola.add(v);  	//Encolar y marcar como visitado
        visitados[v] = true;
        while (!cola.isEmpty()) {
            int vertice = cola.remove();  // Desencolar
            // System.out.println("Alcanzado el " + vertice);
            // Encolar adyacentes a vertice y no visitados aún
            for (int j = 0; j < numVertices; j++) {
                if (this.existeArista(vertice, j) && !visitados[j]) {
                    cola.add(j);  // Encolar y marcar como visitado
                    visitados[j] = true;
                }
            }
        }
        return visitados;
    }

}
