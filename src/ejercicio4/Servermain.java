package ejercicio4;

import java.io.IOException;

public class Servermain {
    public static void main(String[] args) throws IOException
    {
        Servidor serv = new Servidor();
        System.out.println("Iniciando servidor\n");
        serv.startServer();
    }

}
