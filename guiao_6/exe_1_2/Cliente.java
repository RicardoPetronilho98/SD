package exe_1_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente extends Socket {

    private static final String host = "localhost"; // "188.82.64.150"

    /* abaixo do porto 1024 estão reservados para o sistema operativo
       na pasta /etc/services encontra-se listada as portas reservadas para o sistema
       "encontramo-nos" no porto 25565
       netstat -t | grep 22
       serversocketopt() força o servidor a usar a porta (memso que esteja a ser usada por outro, "rouba-lhe a porta")
       */
    private static final int port = 1024;

    public Cliente(String host, int port) throws IOException {
        super(host, port);
    }

    public static void main(String[] agrs) throws IOException {

        Scanner sc = new Scanner(System.in);
        Cliente cliente = new Cliente(host, port);
        String line;

        /** usado para escrever */
        PrintWriter pw = new PrintWriter(cliente.getOutputStream());
        /** usado para ler */
        BufferedReader br = new BufferedReader( new InputStreamReader(cliente.getInputStream()) );

        while(true) {
            if (sc.hasNextLine()) line = sc.nextLine(); // lê uma linha do terminal
            else break; // EOF
            pw.println(line); // Envia a linha para o servidor
            pw.flush(); // garante que escreve a linha

            System.out.println(br.readLine()); // lê a linha enviada pelo servidor
        }

        br.close();
        pw.close();
        cliente.close();
    }
}

/*
    Devemos escolher, poderadamente, o tipo de Reader e Writer para o contexto atual,
    se tivermos a usar texto usamos um Buffer textual
    caso seja binário, usamos binário
    devemos conseguir ler vários caracteres ao mesmo tempo para conseguir um melhor desemepenho
*/


