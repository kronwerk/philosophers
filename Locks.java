import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Locks {
    private Lock[] locks;
    int maxCount;

    public Locks(int len) {
        locks = new Lock[len];
        for (int i = 0; i < len; i++)
            locks[i] = new ReentrantLock();
    }

    public Lock right(int position) {
        int i = (position==locks.length-1) ? 0: position+1;
        return locks[i];
    }

    public Lock left(int position) {
        int i = (position==0) ? locks.length-1: position-1;
        return locks[i];
    }

    public synchronized void update(int count) {
        maxCount = (maxCount < count) ? count : maxCount;
    }
}
