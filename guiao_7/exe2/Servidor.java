package exe2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor extends ServerSocket {

    private static final int MAX_CONNECTED_AT_SAME_TIME = 3;
    private static final int port = 10011;
    private List<Socket> sockets; // contém os sockets de todos os clientes atualmente conectados

    public Servidor(int port) throws IOException {
        super(port);
        this.sockets = new ArrayList<>();
    }

    public void addSocket(Socket cs) {
        synchronized (this.sockets){
            this.sockets.add(cs);
        }
    }

    public void removeSocket(Socket cs) {
        synchronized (this.sockets) {
            this.sockets.remove(cs);
        }
    }

    public List<Socket> getSockets() {
        synchronized (this.sockets) {
            return this.sockets;
        }
    }

    public int nConnected() {
        synchronized (this.sockets) {
            return this.sockets.size();
        }
    }

    public static void main(String[] args) throws IOException {

        Socket cs; // socket de cada cliente
        Servidor servidor = new Servidor(port);
        int count = 0; // identificador (básico) do cliente

        System.out.println("Daemon running on port " + port);

        while (true) {
            if (servidor.nConnected() < MAX_CONNECTED_AT_SAME_TIME) {
                cs = servidor.accept(); // aceita o socket do cliente
                servidor.addSocket(cs);
                new ClienteHandler(cs, servidor, String.valueOf(count)).start(); // cria o handler para o cliente e inicia a thread
                count++;
            }
        }
    }
}