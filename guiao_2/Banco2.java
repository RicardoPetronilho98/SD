import java.util.Arrays;

public class Banco2 {

    private Conta[] contas;

    public Banco2(int NCONTAS) {
        this.contas = new Conta[NCONTAS];
        for(int i = 0; i < this.contas.length; i++) // incializar todas as contas
            this.contas[i] = new Conta();
    }

    public int getNContas() {
        return this.contas.length;
    }

    public int balanco() {
        return Arrays.stream(this.contas).mapToInt(Conta::consulta).sum();
    }

    public void debito(int valor, int i) {
        this.contas[i].debito(valor);
    }

    public void credito(int valor, int i) {
        this.contas[i].credito(valor);
    }

    public void transferir(int a, int b, int valor) {
        synchronized (this.contas[a]) {
            synchronized (this.contas[b]) {
                this.debito(valor, a);
                this.credito(valor, b);
            }
        }
    }

    @Override
    public String toString() {
        return "Banco2{" +
                "contas=" + Arrays.toString(contas) +
                '}';
    }
}