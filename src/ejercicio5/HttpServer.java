package ejercicio5;

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

/**
 * Ejercicio 5: servidor HTTP básico para archivos estáticos.
 * Atiende peticiones GET y sirve archivos desde el directorio del ejercicio.
 */
public class HttpServer {

    private static final int PORT = 35000;
    private static final File BASE_DIR = new File("src/ejercicio5");

    /**
     * Inicia el servidor y atiende clientes de forma continua.
     *
     * @param args argumentos de línea de comandos (no usados)
     * @throws IOException si falla la creación del socket servidor
     */
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

    /**
     * Procesa una petición HTTP de un cliente.
     * Valida la solicitud, evita acceso fuera del directorio base y responde
     * con el archivo solicitado o con un estado de error.
     *
     * @param clientSocket socket de conexión con el cliente
     * @throws IOException si ocurre un error de entrada/salida
     */
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

        File requested = new File(BASE_DIR, path.substring(1));

        if (!requested.getCanonicalPath().startsWith(BASE_DIR.getCanonicalPath())) {
            sendTextResponse(rawOut, "403 Forbidden", "text/plain; charset=UTF-8", "Acceso denegado");
            return;
        }

        if (!requested.exists() || !requested.isFile()) {
            sendTextResponse(rawOut, "404 Not Found", "text/html; charset=UTF-8", "<h1>404 Not Found</h1>");
            return;
        }

        byte[] content;
        try (FileInputStream fis = new FileInputStream(requested)) {
            content = fis.readAllBytes();
        }

        String contentType = getContentType(requested.getName());
        String headers = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: " + contentType + "\r\n"
                + "Content-Length: " + content.length + "\r\n"
                + "Connection: close\r\n\r\n";

        rawOut.write(headers.getBytes(StandardCharsets.UTF_8));
        rawOut.write(content);
        rawOut.flush();
    }

    /**
     * Envía una respuesta HTTP de texto con estado y tipo de contenido.
     *
     * @param out flujo de salida del socket
     * @param status línea de estado HTTP (por ejemplo, "404 Not Found")
     * @param contentType tipo MIME de la respuesta
     * @param body contenido del cuerpo de respuesta
     * @throws IOException si falla la escritura en el socket
     */
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

    /**
     * Determina el tipo MIME según la extensión del archivo.
     *
     * @param fileName nombre del archivo solicitado
     * @return tipo de contenido HTTP correspondiente
     */
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