package ejercicio8;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ChatClient {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Solicitar IP y puerto del servidor remoto
            System.out.print("Ingrese la IP del servidor remoto: ");
            String serverIP = scanner.nextLine().trim();

            System.out.print("Ingrese el puerto del servidor remoto: ");
            int serverPort = Integer.parseInt(scanner.nextLine().trim());

            // Solicitar puerto local para publicar el objeto del cliente
            System.out.print("Ingrese el puerto local para publicar su objeto de chat: ");
            int localPort = Integer.parseInt(scanner.nextLine().trim());

            // Conectarse al servidor remoto
            Registry serverRegistry = LocateRegistry.getRegistry(serverIP, serverPort);
            ChatInterface serverChat = (ChatInterface) serverRegistry.lookup("ChatService");
            System.out.println("Conectado al servidor " + serverIP + ":" + serverPort);

            // Publicar objeto local para que el servidor pueda enviarle mensajes
            ChatImpl localChat = new ChatImpl();
            Registry localRegistry = LocateRegistry.createRegistry(localPort);
            localRegistry.rebind("ChatService", localChat);
            System.out.println("Objeto local publicado en puerto " + localPort);

            // Hilo para leer mensajes entrantes al cliente
            Thread readerThread = new Thread(() -> {
                while (true) {
                    try {
                        String msg = localChat.receiveMessage();
                        if (msg != null) {
                            System.out.println("[Remoto]: " + msg);
                        }
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            readerThread.setDaemon(true);
            readerThread.start();

            // Enviar mensajes al servidor
            System.out.println("Escriba sus mensajes (o 'salir' para terminar):");
            String input;
            while (true) {
                input = scanner.nextLine();
                if (input.equalsIgnoreCase("salir")) break;
                serverChat.sendMessage("[Cliente]: " + input);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
