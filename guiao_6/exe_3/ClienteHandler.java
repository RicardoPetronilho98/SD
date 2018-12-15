package exe_3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteHandler extends Thread {

    private Socket cs;
    private String name;

    public ClienteHandler(Socket cs, String name) {
        super();
        this.cs = cs;
        this.name = name;
    }

    @Override
    public void run() {
        String msg;
        PrintWriter pw;
        BufferedReader br;
        int sum = 0, count = 0;

        System.out.println("Cliente " + this.name + " conectou-se!");

        try {
            pw = new PrintWriter(cs.getOutputStream());
            br = new BufferedReader( new InputStreamReader(cs.getInputStream()) );

            while(true) {
                msg = br.readLine();
                if (msg == null) { // EOF
                    pw.println("MEDIA = " + (sum/count));
                    pw.flush();
                    break;
                }
                sum += Integer.parseInt(msg);
                count++;
                System.out.println("cliente: " + msg + " | SOMA=" + sum);
            }

            /** fechar todos os obejtos usados */
            br.close();
            pw.close();
            cs.close();

            System.out.println("Cliente " + this.name + " desconectou-se!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    } // end method
}
