public class BoundedBufferTeste {

    private static final int N = 100;

    public static void main(String[] args) {

      BoundedBuffer buffer = new BoundedBuffer(10);

        Thread[] threads = new Thread[N];

        for (int i=0; i<threads.length; ) {
            threads[i++] = new Consumidor(buffer);
            threads[i++] = new Produtor(buffer);
        }

        for(Thread t: threads) t.start();

        for(Thread t: threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(buffer);
    }
}
