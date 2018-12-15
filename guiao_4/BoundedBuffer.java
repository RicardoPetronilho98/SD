import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer {

    /** variáveis comum a todos os BoundedBuffer's */
    private Lock l;
    private Condition notFull;
    private Condition notEmpty;

    /** variáveis específicas a este BoundedBuffer, neste caso de inteiros */
    private int[] arr;
    private int inicio;
    private int ocupados;

    public BoundedBuffer(int initialCapacity) {
        this.l = new ReentrantLock();
        this.notFull = this.l.newCondition();
        this.notEmpty = this.l.newCondition();

        this.arr = new int[initialCapacity];
        this.inicio = 0;
        this.ocupados = 0;
    }

    public void put(int v) throws InterruptedException {
        this.l.lock();

        try {
            while(this.ocupados == this.arr.length) {
                this.notFull.await();
            }
            int index = (this.inicio + this.ocupados) % (this.arr.length);
            this.arr[index] = v;
            this.ocupados++;
            this.notEmpty.signal();

        } finally {
            this.l.unlock();
        }
    }

    public int get() throws InterruptedException {
        this.l.lock();

        try {
            while(this.ocupados == 0) {
                this.notEmpty.await();
            }
            int res = this.arr[this.inicio];
            this.inicio = (this.inicio + 1) % this.arr.length;
            this.ocupados--;
            this.notFull.signal();
            return res;

        } finally {
            this.l.unlock();
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(arr);
    }
}
