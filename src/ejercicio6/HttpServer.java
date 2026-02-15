package ejercicio6;

import java.net.*;
import java.io.*;

public class HttpServer {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        Socket clientSocket = null;
        try {
            System.out.println("Listo para recibir ...");
            while (true) {
                clientSocket = serverSocket.accept();
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
    }
    private static void handleClient(Socket clientSocket) throws IOException{
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String requestLine = in.readLine();
        System.out.println(requestLine);

        String[] parts = requestLine.split(" ");
        String fileName = parts[1];

        if (fileName.equals("/")) {
            fileName = "/index.html";
        }
        fileName = fileName.substring(1);

        File file = new File("src/ejercicio6/"+fileName);

        if (file.exists()) {

            FileInputStream fis = new FileInputStream(file);
            byte[] content = fis.readAllBytes();

            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/html; charset=UTF-8");
            out.println("Content-Length: " + content.length);
            out.println();
            out.flush();

            clientSocket.getOutputStream().write(content);
            clientSocket.getOutputStream().flush();

            fis.close();

        } else {

            String notFound = "<h1>404 Not Found</h1>";
            out.println("HTTP/1.1 404 Not Found");
            out.println("Content-Type: text/html");
            out.println("Content-Length: " + notFound.length());
            out.println();
            out.println(notFound);
        }
    }
}