package ejercicio4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Servidor extends Conexion {
    private String function = "cos";

    public Servidor() throws IOException {
        super("servidor");
    }

    public void startServer() {
        try {
            cs = ss.accept();

            DataInputStream entrada = new DataInputStream(cs.getInputStream());
            DataOutputStream salida = new DataOutputStream(cs.getOutputStream());
            while (true) {
                String mensaje = entrada.readUTF();
                if (mensaje.isBlank() || mensaje.equalsIgnoreCase("exit")) {
                    System.out.println("Cliente cerró la conexión");
                    break;
                }
                if (mensaje.startsWith("fun")) {
                    mensaje = mensaje.substring(4);

                    this.changeOperation(mensaje);
                    salida.writeUTF("Operación cambiada. ");
                }else {
                    double resultado = makeoperation(mensaje);

                    salida.writeUTF("Resultado: " + resultado);

                    System.out.println("Resultado enviado: " + resultado);
                }
            }
            ss.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error del servidor. ");
        }
    }
    private void changeOperation(String mensaje){
        if(mensaje.startsWith("sin")){
            this.function = "sin";
        } else if (mensaje.startsWith("cos")) {
            this.function = "cos";
        } else {
            this.function = "tan";
        }
        System.out.println("cambio de función a: " + this.function);
    }

    private double makeoperation(String mensaje){
        double number = Float.parseFloat(mensaje);
        number = Math.toRadians(number);
        return switch (this.function) {
            case "sin" -> Math.sin(number);
            case "cos" -> Math.cos(number);
            case "tan" -> Math.tan(number);
            default -> 0;
        };
    }
}
