package ejercicio3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Servidor del ejercicio 3.
 * Recibe un número, calcula su cuadrado y devuelve el resultado al cliente.
 */
public class Servidor extends Conexion {

    /**
     * Construye el servidor y deja abierto el puerto de escucha.
     *
     * @throws IOException si falla la creación del socket servidor
     */
    public Servidor() throws IOException {
        super("servidor");
    }

    /**
     * Atiende una conexión de cliente y procesa una solicitud.
     */
    public void startServer() {
        try {
            cs = ss.accept();

            DataInputStream entrada = new DataInputStream(cs.getInputStream());
            DataOutputStream salida = new DataOutputStream(cs.getOutputStream());

            String mensaje = entrada.readUTF();
            int numero = Integer.parseInt(mensaje);

            int resultado = numero * numero;

            salida.writeUTF("Resultado: " + resultado);

            System.out.println("Número recibido: " + numero);
            System.out.println("Resultado enviado: " + resultado);

            entrada.close();
            salida.close();
            cs.close();
            ss.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
