package ejercicio3;

import java.io.IOException;

/**
 * Clase de arranque del cliente del ejercicio 3.
 */
public class Clientemain {
    /**
     * Inicia la ejecución del cliente.
     *
     * @param args argumentos de línea de comandos (no usados)
     * @throws IOException si falla la conexión del cliente
     */
    public static void main(String[] args) throws IOException
    {
        Cliente cli = new Cliente();
        System.out.println("Iniciando cliente\n");
        cli.startCliente();
    }
}
