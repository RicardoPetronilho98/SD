package exe1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor extends ServerSocket {

    private static final int port = 1024;

    private Banco banco;

    public Servidor(int port) throws IOException {
        super(port);
        this.banco = new Banco(10);
    }

    public Banco getBanco() {
        return this.banco;
    }

    public static void main(String[] args) throws IOException {

        Socket cs;
        Servidor servidor = new Servidor(port);
        int count = 0;

        while (true) {
            cs = servidor.accept(); // aceita o socket do cliente
            new ClienteHandler(cs, servidor.getBanco(), String.valueOf(count)).start(); // cria o handler para o cliente e inicia a thread
            count++;
        }
    }
}