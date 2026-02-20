package ejercicio3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Cliente del ejercicio 3.
 * Envía un número al servidor y muestra el resultado recibido.
 */
public class Cliente extends Conexion {

    Scanner scanner = new Scanner(System.in);

    /**
     * Construye el cliente y abre la conexión hacia el servidor.
     *
     * @throws IOException si falla la conexión
     */
    public Cliente() throws IOException {
        super("cliente");
    }

    /**
     * Ejecuta el flujo del cliente: leer número, enviarlo y mostrar respuesta.
     */
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