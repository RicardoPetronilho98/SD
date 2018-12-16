package exe2;

public class WareHouseTeste {

    public static void main(String[] args) {

        WareHouse wareHouse = new WareHouse();
        wareHouse.supply("1", 1);
        wareHouse.consume(new String[]{"1"});

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("t1: Não há nada para consumir, vou esperar...");
                wareHouse.consume(new String[]{"1"});
                System.out.println("t1: finalmente consegui consumir!");
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                wareHouse.supply("1", 1);
                System.out.println("t2: Forneci...");
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
