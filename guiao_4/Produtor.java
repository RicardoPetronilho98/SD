import java.util.Random;

public class Produtor extends Thread {

    private static final int N = 10000;
    private BoundedBuffer buffer;

    public Produtor(BoundedBuffer buffer) {
        super();
        this.buffer = buffer;
    }

    @Override
    public void run() {

        Random r = new Random();

        for (int i=0; i<N; i++) {
            try {
                this.buffer.put(r.nextInt());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
