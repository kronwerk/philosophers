public class Dispatcher {
    private MyPhilosopher[] phils;

    public Dispatcher(int locksNum) {
        phils = new MyPhilosopher[locksNum];
    }

    public static void main(String[] args) throws InterruptedException {
        final int count = 30;
        final int DURATION = 10000;
        Dispatcher d = new Dispatcher(count);
        NumbersGenerator generator = new NumbersGenerator(count);
        for (int i = 0; i < count; i++)
            d.phils[i] = new MyPhilosopher(i, generator);

        Thread[] threads = new Thread[count];
        for (int i = 0; i < count; i++) {
            threads[i] = new Thread(d.phils[i]);
            threads[i].start();
        }

        Thread.sleep(DURATION);

        for (MyPhilosopher phil : d.phils) {
            phil.stopFlag = true;
        }
        for (Thread thread : threads) {
            thread.join();
        }
        for (MyPhilosopher phil : d.phils) {
            System.out.println("[Philosopher " + phil.getPosition() + "] ate " +
                    phil.eatCount + " times and waited " + phil.waitTime + " ns");
        }

    }
}
