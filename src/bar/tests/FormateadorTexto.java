package tests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.out;
import static org.junit.Assert.fail;


public class FormateadorTexto {
    public static String capturaSalida(Runnable funcion) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));
        try {
            funcion.run();
        } finally {
            System.setOut(original);
        }
        return out.toString().trim();
    }

    public static String normalizaTexto(String texto) {
        if (texto == null) return "";
        // Normaliza los caracteres (quita tildes, etc.)
        String normalized = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        // Procesa cada línea: elimina todos los espacios, convierte a minúsculas y elimina puntuación,
        // pero solo conserva las líneas no vacías
        String[] lines = normalized.split("\\R");
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            String trimmed = line.trim();
            if (!trimmed.isEmpty()) {
                String processed = trimmed.replaceAll("\\s+", "")
                        .replaceAll("\\p{Punct}", "")
                        .toLowerCase();
                sb.append(processed).append("\n");
            }
        }
        return sb.toString().trim();
    }


    public static Method encontrarMetodoAlternativo(Class<?> clase, String baseName, Class<?>... parametros) throws NoSuchMethodException {
        String[] variantes = {
                baseName,
                baseName.replace("ny", "n"),
                baseName.replace("ny", "ny"),
                baseName.replace("ny", "nh"),
                baseName.replace("ny", "nn")
        };
        for (String nombre : variantes) {
            try {
                return clase.getMethod(nombre, parametros);
            } catch (NoSuchMethodException ignored) {}
        }
        throw new NoSuchMethodException("No se encontró ninguna variante del método: " + baseName);
    }

    public static void printDiferencias(String contexto, String obtenido, String esperado) {
        out.println("FALLO: Se esperaba una salida con el formato " + contexto + ".");
        out.println("Esperado:\n---\n" + esperado + "\n---");
        out.println("Obtenido:\n---\n" + obtenido + "\n---");

        // Además, se pueden mostrar línea a línea las diferencias
        String[] lineasEsperadas = esperado.split("\\R");
        String[] lineasObtenidas = obtenido.split("\\R");
        out.println("Diferencias línea a línea:");
        int max = Math.max(lineasEsperadas.length, lineasObtenidas.length);
        for (int i = 0; i < max; i++) {
            String exp = i < lineasEsperadas.length ? normalizaTexto(lineasEsperadas[i]) : "<vacío>";
            String obt = i < lineasObtenidas.length ? normalizaTexto(lineasObtenidas[i]) : "<vacío>";
            if (!exp.equals(obt)) {
                out.println("  Línea " + (i+1) + " -> Esperado: '" + exp + "', Obtenido: '" + obt + "'");
            }
        }
    }
    public static String normalizaNumerico(String texto) {
        if (texto == null) return "";
        // Normaliza eliminando marcas diacríticas y espacios, pero conserva puntos.
        String res = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replaceAll("[\\s]+", "");
        // Convertir comas a puntos y quitar el símbolo de euro
        res = res.replace(",", ".").replace("€", "E");
        return res.toLowerCase();
    }

    public static float extraerImporte(String texto, String descripcion) {
        // Normalizamos la descripción usando normalizaNumerico para que sea consistente con el texto.
        String normDesc = normalizaNumerico(descripcion);
        out.println("normDesc " + normDesc);
        // Normalizamos el texto completo (elimina espacios, elimina €, etc.)
        String textForNumbers = normalizaNumerico(texto);
        out.println("numbers " + textForNumbers);
        // Requerimos que, tras la descripción, aparezca "c/u.importe:" antes del número
        Pattern p = Pattern.compile(Pattern.quote(normDesc) + ".*?c/u\\.importe[:]?\\s*([0-9]+(?:\\.[0-9]+)?)");
        Matcher m = p.matcher(textForNumbers);
        if (m.find()) {
            return Float.parseFloat(m.group(1));
        }
        fail("No se encontró la línea para " + descripcion);
        return 0;
    }


    public static float extraerImporteTotal(String texto) {
        String textForNumbers = normalizaNumerico(texto);
        // Permitir tanto coma como punto como separador decimal
        Pattern p = Pattern.compile("importetotal[:]?\\s*([0-9]+(?:[.,][0-9]+)?)(?:e)?(?![0-9.,])");
        Matcher m = p.matcher(textForNumbers);
        if (m.find()) {
            String num = m.group(1).replace(",", "."); // Normalizamos el separador decimal
            return Float.parseFloat(num);
        }
        fail("No se encontró el importe total");
        return 0;
    }

}
