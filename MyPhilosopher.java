import java.util.concurrent.locks.Lock;

public class MyPhilosopher extends Philosopher implements Runnable {
    volatile boolean stopFlag = false;
    private NumbersGenerator generator;
    private static int count = 0;

    public MyPhilosopher(int position, NumbersGenerator generator) {
        super(position);
        this.generator = generator;
    }

    public void run() {
        int current = 0;
        while (!stopFlag) {
            think();
            current = generator.get();
            if (current == generator.FAIL)
                continue;
            if ((generator.getRight(current) == generator.getLeft(getPosition()))
                || (generator.getLeft(current) == generator.getRight(getPosition()))
                || (count < eatCount)
                ) {
                count = eatCount;
                generator.release();
                continue;
            }

            Lock left = generator.get(generator.getLeft(getPosition()));
            while (!left.tryLock())
                Thread.yield();
            print("[Philosopher " + getPosition() + "] took left fork");

            Lock right = generator.get(generator.getRight(getPosition()));
            if (!right.tryLock()) {
                print("[Philosopher " + getPosition() + "] dropped left fork");
                left.unlock();
                generator.release();
                continue;
            }

            print("[Philosopher " + getPosition() + "] took right fork");
            eat();
            right.unlock();
            left.unlock();
            generator.release();
        }
        print("[Philosopher " + getPosition() + "] stopped");
    }
}
