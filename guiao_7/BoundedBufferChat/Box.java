package BoundedBufferChat;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Box {

    private static final int CAPACITY = 5;

    private final String id;
    private String[] arr;
    private int inicio;
    private int ocupados;

    public Lock l;
    private Condition notFull;
    private Condition notEmpty;

    public Box(String id) {
        this.id = id;
        this.arr = new String[CAPACITY];
        this.inicio = 0;
        this.ocupados = 0;

        this.l = new ReentrantLock();
        this.notFull = this.l.newCondition();
        this.notEmpty = this.l.newCondition();
    }

    public String get() throws InterruptedException {
        while(this.ocupados == 0){
            this.notEmpty.await();
        }
        String res = this.arr[this.inicio];
        this.inicio = (this.inicio + 1) % this.arr.length;
        this.ocupados--;
        this.notFull.signal();
        return res;
    }

    public void put(String str) throws InterruptedException {
        while(this.ocupados == this.arr.length) {
            System.out.println("Waiting...");
            this.notFull.await();
        }
        int index = (this.inicio + this.ocupados) % (this.arr.length);
        this.arr[index] = str;
        this.ocupados++;
        this.notEmpty.signal();
    }
}