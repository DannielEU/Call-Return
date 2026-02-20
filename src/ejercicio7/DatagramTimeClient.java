package ejercicio7;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class DatagramTimeClient {

    public static void main(String[] args) {
        String lastKnownTime = "Sin hora recibida aún";

        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName("localhost");
            // Timeout de 3 segundos esperando respuesta del servidor
            socket.setSoTimeout(3000);

            byte[] buf = new byte[256];

            while (true) {
                try {
                    // Enviar solicitud al servidor
                    byte[] request = "request".getBytes();
                    DatagramPacket requestPacket = new DatagramPacket(request, request.length, address, 4445);
                    socket.send(requestPacket);

                    // Esperar respuesta
                    DatagramPacket responsePacket = new DatagramPacket(buf, buf.length);
                    socket.receive(responsePacket);

                    // Actualizar la hora con la del servidor
                    lastKnownTime = new String(responsePacket.getData(), 0, responsePacket.getLength());
                    System.out.println("[SERVIDOR] Hora actualizada: " + lastKnownTime);

                } catch (SocketTimeoutException e) {
                    // Servidor no disponible, mantener última hora conocida
                    System.out.println("[SIN CONEXIÓN] Servidor no disponible. Última hora conocida: " + lastKnownTime);
                }

                // Esperar 5 segundos antes de la siguiente actualización
                Thread.sleep(5000);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}