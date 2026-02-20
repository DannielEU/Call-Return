package ejercicio7;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatagramTimeServer {

    DatagramSocket socket;

    public DatagramTimeServer() {
        try {
            socket = new DatagramSocket(4445);
        } catch (SocketException ex) {
            Logger.getLogger(DatagramTimeServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startServer() {
        byte[] buf = new byte[256];
        System.out.println("Servidor iniciado, esperando clientes...");

        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                String dString = new Date().toString();
                byte[] sendBuf = dString.getBytes();
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                DatagramPacket response = new DatagramPacket(sendBuf, sendBuf.length, address, port);
                socket.send(response);

                System.out.println("Hora enviada: " + dString);

            } catch (IOException ex) {
                Logger.getLogger(DatagramTimeServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) {
        DatagramTimeServer ds = new DatagramTimeServer();
        ds.startServer();
    }
}