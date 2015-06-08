import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NumbersGenerator {
    private static int pointer = -1;
    private static Lock lock = new ReentrantLock();
    public static final int FAIL = -1;
    private Locks locks;

    public NumbersGenerator(int locksNum) {
        this.locks = new Locks(locksNum);
    }

    public int getLeft(int i) {
        return locks.getLeft(i);
    }

    public int getRight(int i) {
        return locks.getRight(i);
    }

    public Lock get(int i) {
        return locks.get(i);
    }

    public int get() {
        if (!lock.tryLock())
            return FAIL;
        pointer = locks.getRight(pointer);
        return pointer;
    }

    public void release() {
        lock.unlock();
    }
}
