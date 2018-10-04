public class Counter {

    public int c;

    /**
     * Construtor do Counter.
     * Inicializa a variável a 0.
     */
    public Counter() {
        this.c = 0;
    }

    /**
     * Incrementa a variável c.
     */
    synchronized public void increment() {
        this.c++;
    }
}
