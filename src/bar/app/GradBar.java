package app;

import grafo.GrafoMA;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;

public class GradBar {
    private GrafoMA red;
    private Cliente[] clientes;
    private int numClientes;

    // Constructor
    public GradBar(Cliente[] clientes) {
        this.clientes = clientes;
        this.numClientes = clientes.length;
        this.red = new GrafoMA(numClientes, false);  // No dirigido
    }

    public int getNumClientes() {
        return numClientes;
    }

    public int getIndice(Cliente cliente) {
        for (int i = 0; i < numClientes; i++) {
            if (clientes[i].equals(cliente)) {
                return i;
            }
        }
        return -1;
    }

    public boolean conectarClientes(Cliente c1, Cliente c2) {
        int i = getIndice(c1);
        int j = getIndice(c2);
        if (i == -1 || j == -1 || c1 == null || c2 == null) return false;
        red.insertarArista(i, j);
        return true;
    }

    public void mostrar() {
        System.out.println("-- Información de la red de GradBar --");
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
        red.mostrar();
    }

    public int mostrarAmigos(Cliente cliente) {
        int idx = getIndice(cliente);
        if (idx == -1) return 0;

        int total = 0;
        for (int j = 0; j < numClientes; j++) {
            if (red.existeArista(idx, j)) {
                System.out.println(clientes[j]);
                total++;
            }
        }
        return total;
    }

    public int contarGrupos() {
        boolean[] visitado = new boolean[numClientes];
        int grupos = 0;

        for (int i = 0; i < numClientes; i++) {
            if (!visitado[i]) {
                grupos++;
                System.out.println(clientes[i]);
                dfs(i, visitado);
            }
        }

        return grupos;
    }

    private void dfs(int v, boolean[] visitado) {
        visitado[v] = true;
        for (int i = 0; i < numClientes; i++) {
            if (red.existeArista(v, i) && !visitado[i]) {
                dfs(i, visitado);
            }
        }
    }

    public ArrayList<Cliente> mayorGrupo() {
        boolean[] visitado = new boolean[numClientes];
        ArrayList<Cliente> mayorGrupo = new ArrayList<>();

        for (int i = 0; i < numClientes; i++) {
            if (!visitado[i]) {
                ArrayList<Cliente> grupoActual = new ArrayList<>();
                dfsGrupo(i, visitado, grupoActual);
                if (grupoActual.size() > mayorGrupo.size()) {
                    mayorGrupo = grupoActual;
                }
            }
        }

        return mayorGrupo;
    }

    private void dfsGrupo(int v, boolean[] visitado, ArrayList<Cliente> grupo) {
        visitado[v] = true;
        grupo.add(clientes[v]);
        for (int i = 0; i < numClientes; i++) {
            if (red.existeArista(v, i) && !visitado[i]) {
                dfsGrupo(i, visitado, grupo);
            }
        }
    }

    public String bebidaGrupo(Cliente cliente) {
        int idx = getIndice(cliente);
        if (idx == -1) return "";

        boolean[] visitado = new boolean[numClientes];
        ArrayList<Cliente> grupo = new ArrayList<>();
        dfsGrupo(idx, visitado, grupo);

        TreeMap<String, Integer> contador = new TreeMap<>();
        for (Cliente c : grupo) {
            contador.put(c.getBebida_favorita(),
                    contador.getOrDefault(c.getBebida_favorita(), 0) + 1);
        }

        String bebidaPopular = "";
        int max = 0;
        for (Map.Entry<String, Integer> entry : contador.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                bebidaPopular = entry.getKey();
            }
        }

        return bebidaPopular;
    }

    public void mostrarGrafico() {
        JFrame frame = new JFrame("Grafo de Clientes");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int n = getNumClientes();
                if (n == 0) return;

                // Usamos un layout circular para posicionar los vértices
                int radius = Math.min(getWidth(), getHeight()) / 2 - 50;
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2;
                Point[] puntos = new Point[n];

                // Dibujar los vértices (círculos) y el nombre del cliente
                for (int i = 0; i < n; i++) {
                    double angle = 2 * Math.PI * i / n;
                    int x = centerX + (int) (radius * Math.cos(angle));
                    int y = centerY + (int) (radius * Math.sin(angle));
                    puntos[i] = new Point(x, y);

                    // Dibujar el vértice
                    g.setColor(Color.WHITE);
                    g.fillOval(x - 20, y - 20, 40, 40);
                    g.setColor(Color.BLACK);
                    g.drawOval(x - 20, y - 20, 40, 40);

                    // Dibujar el nombre del cliente
                    String nombre = clientes[i].getNombre(); // Asegúrate de tener este método en Cliente
                    FontMetrics fm = g.getFontMetrics();
                    int textWidth = fm.stringWidth(nombre);
                    g.drawString(nombre, x - textWidth / 2, y - 25);
                }

                // Dibujar las aristas (flechas si es dirigido)
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (red.existeArista(i, j)) {
                            Point p1 = puntos[i];
                            Point p2 = puntos[j];

                            // Dibuja una línea entre p1 y p2
                            g.drawLine(p1.x, p1.y, p2.x, p2.y);

                            // Si el grafo es dirigido, dibuja una flecha en el extremo
                            if (red.getDirigido()) {
                                dibujarFlecha(g, p1, p2);
                            }
                        }
                    }
                }
            }
        };

        frame.add(panel);
        frame.setVisible(true);
    }

    // Metodo auxiliar para dibujar una flecha en la línea
    private void dibujarFlecha(Graphics g1, Point pInicio, Point pFin) {
        Graphics2D g = (Graphics2D) g1.create();
        double dx = pFin.x - pInicio.x;
        double dy = pFin.y - pInicio.y;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx * dx + dy * dy);

        // Traslada el origen al final de la línea
        AffineTransform at = AffineTransform.getTranslateInstance(pInicio.x, pInicio.y);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);

        // Dibuja la línea (desde 0,0 hasta len,0)
        g.drawLine(0, 0, len, 0);

        // Dibuja la cabeza de la flecha en (len,0)
        int arrowSize = 10;
        Polygon arrowHead = new Polygon();
        arrowHead.addPoint(len, 0);
        arrowHead.addPoint(len - arrowSize, -arrowSize / 2);
        arrowHead.addPoint(len - arrowSize, arrowSize / 2);
        g.fill(arrowHead);
        g.dispose();
    }
}
