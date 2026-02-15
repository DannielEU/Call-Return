package ejercicio4;

import java.io.IOException;

public class Clientemain {
    public static void main(String[] args) throws IOException
    {
        Cliente cli = new Cliente();
        System.out.println("Iniciando cliente\n");
        cli.startCliente();
    }
}
