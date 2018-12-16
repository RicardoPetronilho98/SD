package BoundedBufferChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;

public class ClienteHandlerWriter extends Thread {

    private Socket cs;
    private String name;

    private BufferedReader br;
    private BoundedBuffer buffer;

    public ClienteHandlerWriter(Socket cs, String name, BoundedBuffer buffer) {
        super();
        this.cs = cs;
        this.name = name;
        try {
            this.br = new BufferedReader( new InputStreamReader(this.cs.getInputStream()) );
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.buffer = buffer;
    }

    private String[] parse(String msg) {
        String[] tmp = msg.split("_");
        String[] res = new String[2];
        res[0] = tmp[0];
        res[1] = "";
        for (int i=1; i<tmp.length; i++)
            res[1] += tmp[i];
        return res;
    }

    /**
     * Lê as mensagens do cliente e envia para o bounded buffer.
     */
    @Override
    public void run() {

        String msg;

        System.out.println("Writer do cliente " + this.name + " começou!");

        try {

            while (true) {
                msg = this.br.readLine(); // lê mensagem do cliente
                if (msg == null) { // EOF
                    break;
                }

                // System.out.println("cliente " + this.name + ": " + msg);

                try {
                    String[] parse = this.parse(msg);
                    // System.out.println("Debug:" + Arrays.toString(parse));
                    this.buffer.put(parse[0], parse[1]); // escreve mensagem no buffer
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            /** fechar todos os obejtos usados */
            this.br.close();
            this.cs.close();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            System.out.println("Writer do cliente " + this.name + " parou!");
        }
    }
}