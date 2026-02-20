package ejercicio8;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.Queue;

public class ChatImpl extends UnicastRemoteObject implements ChatInterface {

    private Queue<String> messages = new LinkedList<>();

    public ChatImpl() throws RemoteException {
        super();
    }

    @Override
    public synchronized void sendMessage(String message) throws RemoteException {
        messages.add(message);
        System.out.println("[Mensaje recibido]: " + message);
    }

    @Override
    public synchronized String receiveMessage() throws RemoteException {
        return messages.poll(); // Retorna null si no hay mensajes
    }
}
