package ejercicio2;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.Scanner;

public class URLReader {
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


