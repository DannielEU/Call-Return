package ejercicio2;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.Scanner;

/**
 * Ejercicio 2: solicita una URL al usuario, consulta su contenido HTTP
 * y guarda la respuesta en un archivo local.
 */
public class URLReader {
    /**
     * Punto de entrada del programa.
     * Lee una URL desde consola, realiza una petición GET y escribe el
     * contenido obtenido en el archivo "resultado.html".
     *
     * @param args argumentos de línea de comandos (no usados)
     * @throws Exception si ocurre un error de lectura, red o escritura
     */
    public static void main(String[] args) throws Exception {
    try (Scanner scanner = new Scanner(System.in)) {
        System.out.print("Ingrese una URL: ");
        String uri = scanner.nextLine();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(uri))
            .GET()
            .build();

        HttpResponse<String> response =
            client.send(request, HttpResponse.BodyHandlers.ofString());

        try (PrintWriter writer = new PrintWriter(new FileWriter("resultado.html"))) {
        writer.print(response.body());
        }

        System.out.println("Contenido guardado en resultado.html");
    }
    }
}


