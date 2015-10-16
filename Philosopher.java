import java.util.Random;

public class Philosopher {
    int eatCount = 0;
    long waitTime = 0;
    long startWait;
    Random rnd = new Random();
    int maxTimeout = 10;
    private int position;
    private boolean debug = false;
    public static final int count = 5;
    public static final int DURATION = 10000;

    public Philosopher(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setDebug() {
        debug = true;
    }

    public static void finalize(Philosopher[] phils) {
        long totalWait = 0;
        long minWait = Long.MAX_VALUE;
        long maxWait = 0;
        int minEat = Integer.MAX_VALUE;
        int maxEat = 0;
        int sumEat = 0;
        for (Philosopher phil : phils) {
            System.out.println("[Philosopher " + phil.getPosition() + "] ate " +
                    phil.eatCount + " times and waited " + phil.waitTime + " ms");
            totalWait += phil.waitTime;
            sumEat += phil.eatCount;
            minEat = Math.min(phil.eatCount, minEat);
            maxEat = Math.max(phil.eatCount, maxEat);
            minWait = Math.min(phil.waitTime, minWait);
            maxWait = Math.max(phil.waitTime, maxWait);
        }
        System.out.println("Avg: waited " + totalWait/count + " ms, ate " + sumEat/count + " times.");
        System.out.println("Min: waited " + minWait + " ms, ate " + minEat + " times.");
        System.out.println("Max: waited " + maxWait + " ms, ate " + maxEat + " times.");
    }

    public void print(String s) {
        if (debug)
            System.out.println(s);
    }

    public void eat() {
        waitTime += System.currentTimeMillis() - startWait;
        print("[Philosopher " + position + "] is eating");
        try {
            Thread.sleep(rnd.nextInt(maxTimeout));
        } catch (InterruptedException e) { e.printStackTrace();}
        eatCount++;
        print("[Philosopher " + position + "] finished eating");
    }

    public void think() {
        print("[Philosopher " + position + "] is thinking");
        try {
            Thread.sleep(rnd.nextInt(maxTimeout));
        } catch (InterruptedException e) { e.printStackTrace();}
        print("[Philosopher " + position + "] is hungry");
        startWait = System.currentTimeMillis();
    }

    public static void main(String[] args) throws InterruptedException {
        Philosopher[] phils = new Philosopher[count];
        for (int i = 0; i < count; i++)
            phils[i] = new Philosopher(i);

        int i = -1;
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < DURATION) {
            i = (i + 1 < count) ? i + 1 : 0;
            phils[i].think();
            phils[i].eat();
        }

        finalize(phils);
    }
}
