package ejercicio4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Clase base de conexión para cliente y servidor en el ejercicio 4.
 * Inicializa sockets según el rol indicado.
 */
public class Conexion {
    private final int PUERTO = 3000;
    private final String Host = "localhost";
    protected ServerSocket ss;
    protected Socket cs;

    /**
     * Crea la conexión como servidor o cliente.
     *
     * @param tipo "servidor" para abrir ServerSocket; otro valor para cliente
     * @throws IOException si ocurre un error en la creación del socket
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
