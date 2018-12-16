package BoundedBufferChat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente extends Socket {

    private static final String host = "localhost";
    private static final int port = 10011;

    public Cliente(String host, int port) throws IOException {
        super(host, port);
    }

    public static void main(String[] agrs) throws IOException {

        for (int i=0; i<100; i++) { // 100 clientes

            Cliente cliente = new Cliente(host, port);

            /** usado para escrever */
            PrintWriter pw = new PrintWriter(cliente.getOutputStream(), true);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i=0; i<1000; i++) {
                        pw.println("0_teste"); // cliente N escreve 10 vezes para o cliente 0
                        pw.flush();
                    }
                }
            }).start();

            pw.close();
            cliente.close();
        }
    }
}
