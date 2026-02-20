package ejercicio1;
import java.net.URL;

/**
 * Ejercicio 1: analiza una URL y muestra sus componentes principales.
 */
public class URLReader {
    /**
     * Punto de entrada del programa.
     * Crea una URL de ejemplo e imprime protocolo, host, puerto, ruta,
     * consulta, archivo y referencia.
     *
     * @param args argumentos de línea de comandos (no usados)
     * @throws Exception si ocurre un error al procesar la URL
     */
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://www.google.com:80/search?q=java#resultado");

        System.out.println("Protocol: " + url.getProtocol());
        System.out.println("Authority: " + url.getAuthority());
        System.out.println("Host: " + url.getHost());
        System.out.println("Port: " + url.getPort());
        System.out.println("Path: " + url.getPath());
        System.out.println("Query: " + url.getQuery());
        System.out.println("File: " + url.getFile());
        System.out.println("Ref: " + url.getRef());
    }
}
