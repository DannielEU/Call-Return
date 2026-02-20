package ejercicio8;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ChatServer {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Solicitar puerto antes de iniciar
            System.out.print("Ingrese el puerto para publicar el objeto RMI: ");
            int port = Integer.parseInt(scanner.nextLine().trim());

            // Crear e iniciar el registry en el puerto indicado
            Registry registry = LocateRegistry.createRegistry(port);

            // Crear e registrar el objeto remoto
            ChatImpl chatObj = new ChatImpl();
            registry.rebind("ChatService", chatObj);

            System.out.println("Servidor Chat RMI iniciado en el puerto " + port);
            System.out.println("Esperando conexiones...");

            // Hilo para leer mensajes recibidos e imprimirlos en consola
            Thread readerThread = new Thread(() -> {
                while (true) {
                    try {
                        String msg = chatObj.receiveMessage();
                        // Los mensajes ya se imprimen en sendMessage
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            readerThread.setDaemon(true);
            readerThread.start();

            // El servidor también puede enviar mensajes al cliente
            // (para eso el cliente también publica su objeto)
            System.out.println("Escriba mensajes para enviar al cliente (necesita conectarse primero):");
            // El servidor en este diseño recibe; el cliente también publica su propio objeto
            // Mantener vivo el servidor
            Thread.currentThread().join();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
