public class Thread1 extends Thread {

    private static final int N_OPERACOES = 10000; // nº de iterações (mil milhões)
    private Counter c;

    /**
     * Construtor da Thread1.
     * @param c Contador dado
     */
    public Thread1(Counter c) {
        super();
        this.c = c;
    }

    /**
     * Incrementa a veriável do Contador.
     */
    @Override
    public void run() {
        for(int i = 0; i < N_OPERACOES; i++)
            c.increment();
    }
}

