package exe_5;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor extends ServerSocket {

    private static final int port = 1024;

    public Servidor(int port) throws IOException {
        super(port);
    }

    public static void main(String[] args) throws IOException {

        Socket cs;
        exe_1_2.Servidor servidor = new exe_1_2.Servidor(port);
        int count = 0;

        while (true) {
            cs = servidor.accept(); // aceita o socket do cliente
            new ClienteHandler(cs, String.valueOf(count)).start(); // cria o handler para o cliente e inicia a thread
            count++;
        }
    }
}