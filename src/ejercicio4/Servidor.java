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
                if (mensaje.startsWith("fun:")) {
                    String nuevaFuncion = mensaje.substring(4).trim();
                    if (this.changeOperation(nuevaFuncion)) {
                        salida.writeUTF("Operación cambiada a " + this.function);
                    } else {
                        salida.writeUTF("Operación no válida. Use fun:sin, fun:cos o fun:tan");
                    }
                } else {
                    double resultado = makeOperation(mensaje);
                    salida.writeUTF("Resultado: " + resultado);
                    System.out.println("Resultado enviado: " + resultado);
                }
            }

            entrada.close();
            salida.close();
            cs.close();
            ss.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error del servidor. ");
        }
    }
    private boolean changeOperation(String mensaje){
        if (mensaje.equalsIgnoreCase("sin")) {
            this.function = "sin";
            System.out.println("Cambio de función a: " + this.function);
            return true;
        } else if (mensaje.equalsIgnoreCase("cos")) {
            this.function = "cos";
            System.out.println("Cambio de función a: " + this.function);
            return true;
        } else if (mensaje.equalsIgnoreCase("tan")) {
            this.function = "tan";
            System.out.println("Cambio de función a: " + this.function);
            return true;
        }
        return false;
    }

    private double makeOperation(String mensaje){
        double number = Double.parseDouble(mensaje);
        return switch (this.function) {
            case "sin" -> Math.sin(number);
            case "cos" -> Math.cos(number);
            case "tan" -> Math.tan(number);
            default -> 0;
        };
    }
}
