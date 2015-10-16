public class MyPhilosopher extends Philosopher implements Runnable {
    volatile boolean stopFlag = false;
    private Locks locks;

    public MyPhilosopher(int position, Locks locks) {
        super(position);
        this.locks = locks;
    }

    public void run() {
        while (!stopFlag) {
            think();
            locks.get(getPosition());
            print("[Philosopher " + getPosition() + "] took forks");
            eat();
            locks.drop(getPosition());
            print("[Philosopher " + getPosition() + "] dropped forks");
        }
        print("[Philosopher " + getPosition() + "] stopped");
    }

    public static void main(String[] args) throws InterruptedException {
        MyPhilosopher[] phils = new MyPhilosopher[count];
        Locks locks = new Locks(count);
        for (int i = 0; i < count; i++)
            phils[i] = new MyPhilosopher(i, locks);

        Thread[] threads = new Thread[count];
        for (int i = 0; i < count; i++) {
            threads[i] = new Thread(phils[i]);
            threads[i].start();
        }

        Thread.sleep(DURATION);

        for (MyPhilosopher phil : phils) {
            phil.stopFlag = true;
        }
        for (Thread thread : threads) {
            thread.join();
        }

        finalize(phils);
    }
}
