package ejercicio4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Cliente extends Conexion {

    Scanner scanner = new Scanner(System.in);

    public Cliente() throws IOException {
        super("cliente");
    }

    public void startCliente() {
        try {
            DataOutputStream salida = new DataOutputStream(cs.getOutputStream());
            DataInputStream entrada = new DataInputStream(cs.getInputStream());
            while(true){
                System.out.print("Ingrese un número o comando: ");
                String mensaje = scanner.nextLine();
                salida.writeUTF(mensaje);
                if (mensaje.isBlank() || mensaje.equalsIgnoreCase("exit")) {
                    System.out.println("Cliente cerró la conexión");
                    break;
                }
                String respuesta = entrada.readUTF();
                System.out.println(respuesta);
            }
            cs.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}