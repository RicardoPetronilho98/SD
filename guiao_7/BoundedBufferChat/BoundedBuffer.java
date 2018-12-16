package BoundedBufferChat;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer {

    private Lock l;
    private Map<String, Box> messages;

    public BoundedBuffer() {
        this.l = new ReentrantLock();
        this.messages = new HashMap<>();

        // apenas para testar
        for(int i=0; i<150; i++) this.messages.put(Integer.toString(i), new Box(Integer.toString(i)));
    }

    public String get(String id) throws InterruptedException {
        this.l.lock(); // lock() de toda a estrutura
        Box box = this.messages.get(id);
        box.l.lock();  // lock() da box
        this.l.unlock(); // unlock() de toda a estrutura
        String res = box.get();
        box.l.unlock(); // unlock() da box
        return res;
    }

    public void put(String id, String msg) throws InterruptedException {
        this.l.lock(); // lock() de toda a estrutura
        Box box = this.messages.get(id);
        box.l.lock(); // lock() da box
        this.l.unlock(); // unlock() de toda a estrutura
        box.put(msg);
        box.l.unlock(); // unlock() da box
    }

    @Override
    public String toString() {
        return this.messages.toString();
    }
}
