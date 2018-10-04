public class Banco {

    private int[] contas;
    private int saldo; // saldo total do banco -> ∑ ( creditado - debitado )

    /**
     * Construtor do Banco.
     * Inicializa todas as contas a 0.
     * @param NCONTAS número de contas do banco
     */
    public Banco(int NCONTAS) {
        this.contas = new int[NCONTAS];
        this.saldo = 0;
        for(int i = 0; i < this.contas.length; i++) // incializar todas as contas a 0
            this.contas[i] = 0;
    }

    /**
     * Devolve o número de contas do Banco.
     * @return retorna o número de contas.
     */
    synchronized public int getNContas() {
        return this.contas.length;
    }

    /**
     * Devolve o saldo do Banco.
     * @return retorna o saldo do Banco.
     */
    synchronized public int getSaldo() {
        return this.saldo;
    }

    /**
     * Debita/ retira o valor dado da conta i.
     * @param valor valor a ser debitado.
     * @param i conta a ser debitada.
     */
    synchronized public void debito(int valor, int i) {
        this.contas[i] -= valor;
        this.saldo -= valor;
    }

    /**
     * Credita/ adiciona o valor dado da conta i.
     * @param valor valor a ser creditado.
     * @param i conta a ser creditada.
     */
    synchronized public void credito(int valor, int i) {
        this.contas[i] += valor;
        this.saldo += valor;
    }

    /**
     * Devolve o saldo da conta i.
     * @param i conta a ser consultada.
     * @return retorna o saldo da conta.
     */
    synchronized public int consulta(int i) {
        return this.contas[i];
    }
}