package exe2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** O lock() e unlock() não foi usado nesta classe propositadamente para pode ser realizado o TWO-PHASE LOCKING */

public class Item {

    public final String id;
    private int amount;
    public Lock l;

    private int maxCapacity;
    private Condition notFull;
    private Condition notEmpty;

    public Item(String id, int amount, int maxCapacity) {
        this.id = id;
        this.amount = amount;
        this.l = new ReentrantLock();

        this.maxCapacity = maxCapacity;
        if (this.amount > this.maxCapacity) this.amount = this.maxCapacity; // caso o gajo se engane
        this.notFull = this.l.newCondition();
        this.notEmpty = this.l.newCondition();
    }

    public void put(int quantity) throws InterruptedException {
        while(this.amount == this.maxCapacity) this.notFull.await();
        this.amount += quantity;
        if (this.amount > this.maxCapacity) this.amount = this.maxCapacity;
        this.notEmpty.signal();

    }

    public void get() throws InterruptedException {
        while(this.amount == 0) this.notEmpty.await();
        this.amount--;
        this.notFull.signal();
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", maxCapacity=" + maxCapacity +
                '}';
    }
}