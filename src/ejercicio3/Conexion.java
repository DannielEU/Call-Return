package ejercicio3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Clase base de conexión para cliente y servidor en el ejercicio 3.
 * Inicializa un socket de servidor o de cliente según el tipo recibido.
 */
public class Conexion {
    private final int PUERTO = 3000;
    private final String Host = "localhost";
    protected ServerSocket ss;
    protected Socket cs;

    /**
     * Crea la conexión según el rol indicado.
     *
     * @param tipo "servidor" para crear ServerSocket, cualquier otro valor
     *             crea la conexión como cliente hacia localhost.
     * @throws IOException si falla la creación de sockets
     */
    public Conexion(String tipo) throws IOException{
        if(tipo.equalsIgnoreCase("servidor")){
            ss = new ServerSocket(PUERTO);
            cs = new Socket();
        }else {
            cs = new Socket(Host, PUERTO);
        }
    }
}
