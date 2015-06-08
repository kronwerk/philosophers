import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Locks {
    private Lock[] locks;

    public Locks(int len) {
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

    public Lock get(int i) {
        return locks[i];
    }

}
