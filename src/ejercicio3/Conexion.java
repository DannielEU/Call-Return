package ejercicio3;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Conexion {
    private final int PUERTO = 3000;
    private final String Host = "localhost";
    protected ServerSocket ss;
    protected Socket cs;

    public Conexion(String tipo) throws IOException{
        if(tipo.equalsIgnoreCase("servidor")){
            ss = new ServerSocket(PUERTO);
            cs = new Socket();
        }else {
            cs = new Socket(Host, PUERTO);
        }
    }
}
