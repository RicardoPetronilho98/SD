import java.util.concurrent.locks.ReentrantLock;

public class Conta implements Comparable<Conta> {

    private int id;
    private float saldo;
    public ReentrantLock rl;

    public Conta(int id, float saldo) {
        this.id = id;
        this.saldo = saldo;
        this.rl = new ReentrantLock();
    }

    public float getSaldo() {
        return this.saldo;
    }

    public void credito(float saldo) {
        this.saldo += saldo;
    }

    public void debito(float saldo) {
        this.saldo -= saldo;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "id=" + id +
                ", saldo=" + saldo +
                '}';
    }

    @Override
    public int compareTo(Conta o) {
        if (this.id == o.getId()) return 0;
        else if (this.id < o.getId()) return 1;
        else return -1;
    }
}
