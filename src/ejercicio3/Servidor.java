package ejercicio3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Servidor extends Conexion {

    public Servidor() throws IOException {
        super("servidor");
    }

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
