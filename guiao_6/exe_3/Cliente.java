package exe_3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente extends Socket {

    private static final String host = "localhost";
    private static final int port = 1024;

    public Cliente(String host, int port) throws IOException {
        super(host, port);
    }

    public static void main(String[] agrs) throws IOException {

        Scanner sc = new Scanner(System.in);
        String line;
        exe_1_2.Cliente cliente = new exe_1_2.Cliente(host, port);

        PrintWriter pw = new PrintWriter(cliente.getOutputStream());
        BufferedReader br = new BufferedReader( new InputStreamReader(cliente.getInputStream()) );

        while(true) {
            if (sc.hasNextLine()) line = sc.nextLine(); // lê uma linha do terminal
            else { // EOF
                cliente.shutdownOutput();
                System.out.println("MEDIA=" + br.readLine());
                break;
            }
            pw.write(line + "\n"); // Envia a linha para o servidor
            pw.flush(); // garante que escreve a linha
        }

        br.close();
        pw.close();
        cliente.close();
    }
}