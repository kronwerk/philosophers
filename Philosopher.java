import java.util.Random;

public class Philosopher {
    int eatCount = 0;
    long waitTime = 0;
    long startWait;
    Random rnd = new Random();
    int maxTimeout = 10;
    private int position;

    public Philosopher(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void eat() {
        waitTime += System.currentTimeMillis() - startWait;
        System.out.println("[Philosopher " + position + "] is eating");
        try {
            Thread.sleep(rnd.nextInt(maxTimeout));
        } catch (InterruptedException e) { e.printStackTrace();}
        eatCount++;
        System.out.println("[Philosopher " + position + "] finished eating");
    }

    public void think() {
        System.out.println("[Philosopher " + position + "] is thinking");
        try {
            Thread.sleep(rnd.nextInt(maxTimeout));
        } catch (InterruptedException e) { e.printStackTrace();}
        System.out.println("[Philosopher " + position + "] is hungry");
        startWait = System.currentTimeMillis();
    }
}
