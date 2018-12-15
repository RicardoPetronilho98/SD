package exe_1_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor extends ServerSocket {

    private static final int port = 1024;

    public Servidor(int port) throws IOException {
        super(port);
    }

    public static void main(String[] args) throws IOException {

        Servidor servidor = new Servidor(port);
        PrintWriter pw;
        BufferedReader br;

        while (true) {
            Socket cs = servidor.accept(); // socket do cliente
            System.out.println("Cliente conectou-se!");

            /** usado para escrever */
            pw = new PrintWriter(cs.getOutputStream());
            /** usado para ler */
            br = new BufferedReader( new InputStreamReader(cs.getInputStream()) );

            while(true) {
                String line = br.readLine();
                if (line == null) break; /** para quando o cliente não enviar mais mensagem (EOF - Ctrl - C) */
                System.out.println("cliente: " + line);
                pw.println(line.toUpperCase());
                pw.flush(); /** garante que a linha foi memso escrita */
            }

            /** fechar todos os obejtos usados */
            br.close();
            pw.close();
            cs.close();
        }

    }
}
