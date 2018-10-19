public class Conta {

    private int saldo;

    public Conta() {
        this.saldo = 0;
    }

    synchronized public void credito(int valor) {
        this.saldo += valor;
    }

    synchronized public void debito(int valor) {
        this.saldo -= valor;
    }

    synchronized public int consulta() {
        return this.saldo;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "saldo=" + saldo +
                '}';
    }
}
