import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Banco implements Bank {

    private Map<Integer, Conta> contas;
    private int currentId;
    private ReentrantLock rl;

    public Banco() {
        this.contas = new HashMap<>();
        this.currentId = 0;
        this.rl = new ReentrantLock();
    }

    @Override
    public int createAccount(float initialBalance) {
        try {
            this.rl.lock();
            int id = this.currentId;
            this.contas.put(id, new Conta(id, initialBalance));
            this.currentId++;
            return id;
        } finally {
            this.rl.unlock();
        }
    }

    @Override
    public float closeAccount(int id) throws InvalidAccountException {
        try {
            this.rl.lock(); // lock() do banco
            if (!this.contas.containsKey(id)) throw new InvalidAccountException(Integer.toString(id));
            float saldo = this.contas.get(id).getSaldo();
            this.contas.remove(id);
            return saldo;
        } finally {
            this.rl.unlock();
        }
    }

    @Override
    public void transfer(int from, int to, float amount) throws InvalidAccountException, NotEnoughFundsException {
        if (!this.contas.containsKey(from)) throw new InvalidAccountException(Integer.toString(from));
        else if (!this.contas.containsKey(to)) throw new InvalidAccountException(Integer.toString(to));

        try {

            this.rl.lock(); // lock() do banco

            Conta contaFrom = this.contas.get(from);
            Conta contaTo = this.contas.get(to);

            /**
             * Aqui tomo partido de ter implementado a interface Comparable<>
             * pois à medida que adiciono uma Conta ao Set<> ele automagicamente insere ordenadamente
             */
            Set<Conta> contas = new TreeSet<>();
            contas.add(contaFrom);
            contas.add(contaTo);

            /** agr que as contas estão ordenadas, posso fazer lock() de cada uma delas */
            contas.forEach(c -> c.rl.lock()); // lock() das contas

            this.rl.unlock(); // unlock() do banco

            contaFrom.debito(amount);
            contaFrom.rl.unlock(); // unlock() da conta

            contaTo.credito(amount);
            contaTo.rl.unlock(); // unlock() da conta

        } finally {
            // TODO
        }
    }

    @Override
    public float totalBalance(int[] accounts) {

        try {
            this.rl.lock(); // lock() do banco

            List<Conta> tmp = new ArrayList<>(accounts.length);
            for(int id: accounts)
                if (this.contas.containsKey(id))
                    tmp.add(this.contas.get(id));

            /**
             * se a Class Conta implementasse a interface Comparable, era mais fácil usar um Set<>
             * aqui em vez de um List<>.
             * Uma vez que o construtor vazio do Set<> utiliza ordenação natural, assim não tinha de criar um Comparator<>
             */
            tmp.sort(new Comparator<Conta>() {
                @Override
                public int compare(Conta o1, Conta o2) {
                    if (o1.getId() == o2.getId()) return 0;
                    else if (o1.getId() < o2.getId()) return 1;
                    else return -1;
                }
            });

            /** agr que as contas estão ordenadas, posso fazer lock() de cada uma delas */
            tmp.forEach(conta -> conta.rl.lock()); // lock() das contas

            this.rl.unlock(); // unlock() do banco

            float total = 0f;
            for(Conta conta: tmp) {
                total += conta.getSaldo();
                conta.rl.unlock(); // unlock() da conta ao fim de fazer o trabalho
            }

            return total;

        } finally {
            // TODO
        }
    }

    @Override
    public String toString() {
        return "Banco{" +
                "contas=" + this.contas +
                ", currentId=" + this.currentId +
                '}';
    }
}