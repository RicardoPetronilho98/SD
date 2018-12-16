package BoundedBufferChat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor extends ServerSocket {

    private static final int port = 10011;
    private BoundedBuffer buffer;

    public Servidor(int port) throws IOException {
        super(port);
        this.buffer = new BoundedBuffer();
    }

    public static void main(String[] args) throws IOException {

        Socket cs;
        Servidor servidor = new Servidor(port);
        int count = 0;

        while (true) {
            cs = servidor.accept(); // aceita o socket do cliente
            new ClienteHandlerReader(cs, String.valueOf(count), servidor.buffer).start();
            new ClienteHandlerWriter(cs, String.valueOf(count), servidor.buffer).start();
            count++;
        }
    }
}