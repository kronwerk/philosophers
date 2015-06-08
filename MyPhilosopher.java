import java.util.concurrent.locks.Lock;

public class MyPhilosopher extends Philosopher implements Runnable {
    volatile boolean stopFlag = false;
    private NumbersGenerator generator;

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
            if ((current == generator.getLeft(getPosition())) || (current == generator.getRight(getPosition()))) {
                generator.release();
                continue;
            }

            Lock left = generator.get(generator.getLeft(getPosition()));
            while (!left.tryLock())
                Thread.yield();
            System.out.println("[Philosopher " + getPosition() + "] took left fork");

            Lock right = generator.get(generator.getRight(getPosition()));
            if (!right.tryLock()) {
                System.out.println("[Philosopher " + getPosition() + "] dropped left fork");
                left.unlock();
                generator.release();
                continue;
            }

            System.out.println("[Philosopher " + getPosition() + "] took right fork");
            eat();
            right.unlock();
            left.unlock();
            generator.release();
        }
        System.out.println("[Philosopher " + getPosition() + "] stopped");
    }
}
