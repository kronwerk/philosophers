import java.util.concurrent.locks.Lock;

public class MyPhilosopher extends Philosopher implements Runnable {
    volatile boolean stopFlag = false;
    private Locks locks;

    public MyPhilosopher(int position, Locks locks) {
        super(position);
        this.locks = locks;
    }

    @Override
    public void eat() {
        super.eat();
        //locks.update(eatCount);
    }

    public void run() {
        while (!stopFlag) {
            think();
            Lock left = locks.left(position);
            while (!left.tryLock())
                Thread.yield();
            System.out.println("[Philosopher " + position + "] took left fork");

            /*try { Thread.sleep(100);
            } catch (InterruptedException e) { e.printStackTrace(); }*/
            Lock right = locks.right(position);
            if (!right.tryLock()) {
                System.out.println("[Philosopher " + position + "] dropped left fork");
                left.unlock();
                continue;
            }

            System.out.println("[Philosopher " + position + "] took right fork");
            eat();
            right.unlock();
            left.unlock();
        }
        System.out.println("[Philosopher " + position + "] stopped");
    }

    public static void main(String[] args) throws Exception {
        int count = 30;
        MyPhilosopher[] phils = new MyPhilosopher[count];

        Locks locks = new Locks(count);
        for (int i=0; i<count; i++)
            phils[i] = new MyPhilosopher(i, locks);

        Thread[] threads = new Thread[count];
        for (int i = 0; i < count; i++) {
            threads[i] = new Thread(phils[i]);
            threads[i].start();
        }

        Thread.sleep(10000);

        for (MyPhilosopher phil : phils) {
            phil.stopFlag = true;
        }
        for (Thread thread : threads) {
            thread.join();
        }
        for (MyPhilosopher phil : phils) {
            System.out.println("[Philosopher " + phil.position + "] ate " +
            phil.eatCount + " times and waited " + phil.waitTime + " ns");
        }
    }
}
