import java.util.Random;

public class Agencia extends Thread {

    private static final int N_OPERACOES = 1000; // número de operações (crédito, débito ou consulta) a ser realizadas
    private static final int MONEY = 1000; // [0 .. 999] intervalo de valor (aleatório) a ser creditado ou debitado

    private Banco banco;
    private int saldo; // saldo interno da Agencia ∑ ( creditado - debitado )

    /**
     * Construtor da Agencia.
     * @param banco banco no qual a Agencia vai realizar as operações.
     */
    public Agencia(Banco banco) {
        super();
        this.banco = banco;
        this.saldo = 0;
    }

    /**
     * Devolve o saldo da Agencia.
     * @return retorna o saldo do Agencia.
     */
    public int getSaldo() {
        return this.saldo;
    }

    /**
     * Operações a serem realizadas pela Agência.
     */
    @Override
    public void run() {

        int operacao, conta, valor;
        Random r = new Random();

        for (int i = 0; i < N_OPERACOES; i++) { // faz mil operações

            operacao = r.nextInt(3); // indica um número aletório do conjunto {0, 1, 2} indicando assim que operação vai ser realizada (crédito, débito ou consulta)
            conta = r.nextInt(banco.getNContas()); // index (aleatório) da conta a que vai ser realizada a operação

            if (operacao == 0) { // crédito
                valor = r.nextInt(MONEY); // valor entre [0 .. 999]
                banco.credito(valor, conta);
                this.saldo += valor;
            }
            else if (operacao == 1) { // débito
                valor = r.nextInt(MONEY); // valor entre [0 .. 999]
                banco.debito(valor, conta);
                this.saldo -= valor;
            }
            else banco.consulta(conta); // consulta
        }
    }
}
