package exe2;

import java.io.*;
import java.net.Socket;

public class ClienteHandler extends Thread {

    private Socket cs; // socket do cliente
    private Servidor servidor;
    private String name;

    public ClienteHandler(Socket cs, Servidor servidor, String name) {
        super();
        this.cs = cs;
        this.servidor = servidor;
        this.name = name;
    }

    /**
     * Envia a mensagem para todos os clientes (sockets) conectados ao servidor.
     * @param msg mensagem
     * @throws IOException caso o acesso ao OutPutStream seja negado
     */
    private void replyAll(String msg) throws IOException {
        for (Socket cliente : this.servidor.getSockets()) {
            synchronized (cliente) {
                if (cliente.equals(this.cs)) continue; // não envia ao próprio
                PrintWriter otherPw = new PrintWriter(cliente.getOutputStream());
                otherPw.println(msg); // envia a mensagem para todos os outros clientes
                otherPw.flush();
            }
        }
    }

    @Override
    public void run() {
        String msg;
        PrintWriter pw;
        BufferedReader br;

        System.out.println("Client " + this.name + " connected at " + this.cs.getRemoteSocketAddress());

        try {
            pw = new PrintWriter(this.cs.getOutputStream());
            br = new BufferedReader( new InputStreamReader(this.cs.getInputStream()) );

            while (true) {
                msg = br.readLine();
                if (msg == null) { // EOF
                    break;
                }
                System.out.println("client " + this.name + ": " + msg);
                this.replyAll(msg);
            }

            // fechar todos os obejtos usados
            br.close();
            pw.close();
            this.cs.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.servidor.removeSocket(this.cs);
            System.out.println("Client " + this.name + " disconnected");
        }
    }
}