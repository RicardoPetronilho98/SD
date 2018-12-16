package BoundedBufferChat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteHandlerReader extends Thread {

    private Socket cs;
    private String id;

    private PrintWriter pw;
    private BoundedBuffer buffer;

    public ClienteHandlerReader(Socket cs, String id, BoundedBuffer buffer) {
        super();
        this.cs = cs;
        this.id = id;
        try {
            this.pw = new PrintWriter(this.cs.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.buffer = buffer;
    }

    /**
     * Lê do Bounded buffer e envia as mensagens para o cliente.
     */
    @Override
    public void run() {

        System.out.println("Reader do cliente " + this.id + " começou!");

        try {

            while (true) {
                try {
                    String msg = this.buffer.get(this.id);
                    this.pw.println(msg);
                    this.pw.flush();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } finally {
            System.out.println("Reader do cliente " + this.id + " parou!");
        }
    }
}