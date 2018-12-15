package exe1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteHandler extends Thread {

    private Socket cs;
    private Banco banco;
    private String name;

    private PrintWriter pw;
    private BufferedReader br;

    public ClienteHandler(Socket cs, Banco banco, String name) {
        super();
        this.cs = cs;
        this.banco = banco;
        this.name = name;
        try {
            this.pw = new PrintWriter(this.cs.getOutputStream());
            this.br = new BufferedReader( new InputStreamReader(this.cs.getInputStream()) );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseMsg(String msg) throws InputErrorException {

        String[] parse = msg.split(" ");

        String op = parse[0];
        int valor, conta, o, d;

        switch (op) {
            case "d":
                valor = Integer.parseInt(parse[1]);
                conta = Integer.parseInt(parse[2]);
                this.banco.debito(valor, conta);
                break;

            case "c":
                valor = Integer.parseInt(parse[1]);
                conta = Integer.parseInt(parse[2]);
                this.banco.credito(valor, conta);
                break;

            case "t":
                valor = Integer.parseInt(parse[1]);
                o = Integer.parseInt(parse[2]);
                d = Integer.parseInt(parse[3]);
                this.banco.transferir(o, d, valor);
                break;

            case "consulta":
                this.pw.println(this.banco.toString());
                this.pw.flush();
                break;

            default:
                throw new InputErrorException(msg);
        }
    }

    @Override
    public void run() {

        String msg;

        System.out.println("Cliente " + this.name + " conectou-se!");

        try {

            while (true) {
                msg = this.br.readLine();
                if (msg == null) { // EOF
                    break;
                }

                System.out.println("cliente " + this.name + ": " + msg);

                try {
                    this.parseMsg(msg);
                } catch (InputErrorException e) {
                    e.printStackTrace();
                    this.pw.println("Input error!");
                    this.pw.flush();
                }
            }

            /** fechar todos os obejtos usados */
            this.br.close();
            this.pw.close();
            this.cs.close();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            System.out.println("Cliente " + this.name + " desconectou-se!");
        }
    }
}