public class Dispatcher {
    private MyPhilosopher[] phils;

    public Dispatcher(int locksNum) {
        phils = new MyPhilosopher[locksNum];
    }

    public static void main(String[] args) throws InterruptedException {
        Dispatcher d = new Dispatcher(Philosopher.count);
        NumbersGenerator generator = new NumbersGenerator(Philosopher.count);
        for (int i = 0; i < Philosopher.count; i++)
            d.phils[i] = new MyPhilosopher(i, generator);

        Thread[] threads = new Thread[Philosopher.count];
        for (int i = 0; i < Philosopher.count; i++) {
            threads[i] = new Thread(d.phils[i]);
            threads[i].start();
        }

        Thread.sleep(Philosopher.DURATION);

        for (MyPhilosopher phil : d.phils) {
            phil.stopFlag = true;
        }
        for (Thread thread : threads) {
            thread.join();
        }

        Philosopher.finalize(d.phils);
    }
}
