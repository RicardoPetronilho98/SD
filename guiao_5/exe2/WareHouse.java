package exe2;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** Este guião junta two-phase locking com BoundedBuffer */

public class WareHouse {

    private static final int MAX_QUANTITY = 3;

    private Map<String, Item> items;
    private Lock l;

    public WareHouse(){
        this.items = new HashMap<>();
        this.l = new ReentrantLock();
    }

    private void createItem(String item, int quantity) {
        this.l.lock(); // lock() do armazem
        try {
            this.items.put(item, new Item(item, quantity, MAX_QUANTITY));
        } finally {
            this.l.unlock(); // unlock() do armazem
        }
    }

    public void supply(String item, int quantity) {
        if (!this.items.containsKey(item)) this.createItem(item, quantity);
        else {
            this.l.lock(); // lock() do armazem
            Item i = this.items.get(item);
            i.l.lock(); // lock() do item
            this.l.unlock(); // unlock() do armazem
            try {
                i.put(quantity);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i.l.unlock(); // unlock() do item
        }
    }

    public void consume(String[] items) {

        this.l.lock(); // lock() do armazem

        /** Obter os items que vão ser consumidos */
        List<Item> tmp = new ArrayList<>(items.length);
        for (String id: items)
            if (this.items.containsKey(id))
                tmp.add(this.items.get(id));

        /** Ordenar os items. Assim garantimos que a ordem de bloqueio é sempre a mesma, evitando DeadLock */
        tmp.sort(new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o1.id.compareTo(o2.id);
            }
        });

        /** Agora que os Items estão ordenados podemos bloquear cada um */
        tmp.forEach(item -> item.l.lock());

        /** uma vez que os Items já estão bloqueados, já se pode desbloquear o armazem */
        this.l.unlock();// unlock() do armazem

        /** Faz o "trabalho" e desbloqueia o Item */
        for(Item item: tmp) {
            try {
                item.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            item.l.unlock();
        }
    }

    @Override
    public String toString() {
        return "WareHouse{" +
                "items=" + items +
                '}';
    }
}