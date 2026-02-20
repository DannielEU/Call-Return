package ejercicio4;

import java.io.IOException;

/**
 * Clase de arranque del servidor del ejercicio 4.
 */
public class Servermain {
    /**
     * Inicia el servidor y queda a la espera de conexiones.
     *
     * @param args argumentos de línea de comandos (no usados)
     * @throws IOException si falla la creación del servidor
     */
    public static void main(String[] args) throws IOException
    {
        Servidor serv = new Servidor();
        System.out.println("Iniciando servidor\n");
        serv.startServer();
    }

}
