package ejercicio3;

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
        try (DataOutputStream salida = new DataOutputStream(cs.getOutputStream());
             DataInputStream entrada = new DataInputStream(cs.getInputStream())) {

            System.out.print("Ingrese un número: ");
            String mensaje = scanner.nextLine();
            salida.writeUTF(mensaje);

            String respuesta = entrada.readUTF();
            System.out.println(respuesta);

            cs.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}