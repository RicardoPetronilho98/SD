public class Consumidor extends Thread {

    private static final int N = 10000;
    private BoundedBuffer buffer;

    public Consumidor (BoundedBuffer buffer) {
        super();
        this.buffer = buffer;
    }

    @Override
    public void run() {

        for (int i=0; i<N; i++) {
            try {
                this.buffer.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
