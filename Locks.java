import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Locks {
    private Lock[] locks;
    private int len;
    private AtomicInteger total = new AtomicInteger();

    public Locks(int len) {
        this.len = len;
        locks = new Lock[len];
        for (int i = 0; i < len; i++)
            locks[i] = new ReentrantLock();
    }

    public int getLeft(int i) {
        return (i==0) ? locks.length-1: i-1;
    }

    public int getRight(int i) {
        return (i==locks.length-1) ? 0: i+1;
    }

    private void grab(int i) {
        while (!locks[i].tryLock() )
            Thread.yield();
        total.incrementAndGet();
    }

    public void get(int i) {
        int left = getLeft(i);
        int right = getRight(i);
        while (true) {
            grab(left);
            if (total.compareAndSet(len, len-1)) {
                locks[left].unlock();
                continue;
            }
            grab(right);
            break;
        }
    }

    public void drop(int i) {
        locks[getLeft(i)].unlock();
        total.decrementAndGet();
        locks[getRight(i)].unlock();
        total.decrementAndGet();
    }

}
