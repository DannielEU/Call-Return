package ejercicio6;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class HttpServer {

    private static final int PORT = 35000;
    private static final File BASE_DIR = new File("src/ejercicio6");

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor listo en puerto " + PORT);

            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    handleClient(clientSocket);
                } catch (IOException e) {
                    System.out.println("Error atendiendo cliente: " + e.getMessage());
                }
            }
        }
    }

    private static void handleClient(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        OutputStream rawOut = new BufferedOutputStream(clientSocket.getOutputStream());

        String requestLine = in.readLine();
        if (requestLine == null || requestLine.isBlank()) {
            return;
        }

        String line;
        while ((line = in.readLine()) != null && !line.isEmpty()) {
            // Consumir headers
        }

        String[] parts = requestLine.split(" ");
        if (parts.length < 2) {
            sendTextResponse(rawOut, "400 Bad Request", "text/plain; charset=UTF-8", "Solicitud inválida");
            return;
        }

        String path = URLDecoder.decode(parts[1], StandardCharsets.UTF_8);
        if (path.equals("/")) {
            path = "/index.html";
        }

        File file = new File(BASE_DIR, path.substring(1));
        if (!file.getCanonicalPath().startsWith(BASE_DIR.getCanonicalPath())) {
            sendTextResponse(rawOut, "403 Forbidden", "text/plain; charset=UTF-8", "Acceso denegado");
            return;
        }

        if (!file.exists() || !file.isFile()) {
            sendTextResponse(rawOut, "404 Not Found", "text/html; charset=UTF-8", "<h1>404 Not Found</h1>");
            return;
        }

        byte[] content;
        try (FileInputStream fis = new FileInputStream(file)) {
            content = fis.readAllBytes();
        }

        String contentType = getContentType(file.getName());
        String headers = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: " + contentType + "\r\n"
                + "Content-Length: " + content.length + "\r\n"
                + "Connection: close\r\n\r\n";

        rawOut.write(headers.getBytes(StandardCharsets.UTF_8));
        rawOut.write(content);
        rawOut.flush();
    }

    private static void sendTextResponse(OutputStream out, String status, String contentType, String body) throws IOException {
        byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);
        String headers = "HTTP/1.1 " + status + "\r\n"
                + "Content-Type: " + contentType + "\r\n"
                + "Content-Length: " + bodyBytes.length + "\r\n"
                + "Connection: close\r\n\r\n";
        out.write(headers.getBytes(StandardCharsets.UTF_8));
        out.write(bodyBytes);
        out.flush();
    }

    private static String getContentType(String fileName) {
        String name = fileName.toLowerCase();
        if (name.endsWith(".html") || name.endsWith(".htm")) return "text/html; charset=UTF-8";
        if (name.endsWith(".jpg") || name.endsWith(".jpeg")) return "image/jpeg";
        if (name.endsWith(".png")) return "image/png";
        if (name.endsWith(".gif")) return "image/gif";
        if (name.endsWith(".svg")) return "image/svg+xml";
        if (name.endsWith(".ico")) return "image/x-icon";
        if (name.endsWith(".css")) return "text/css; charset=UTF-8";
        if (name.endsWith(".js")) return "application/javascript; charset=UTF-8";
        return "application/octet-stream";
    }
}