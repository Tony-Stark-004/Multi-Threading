package thread_locking;

public class Locking {
    public static void main(String[] args) throws InterruptedException {
        MyRunnable r = new MyRunnable("Dhoni");

        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}

/* implements Runnable */
class MyRunnable implements Runnable {
    String name;

    public MyRunnable(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 20; i++) {
            this.wish(i, name);
        }
    }

    /* without synchronized */
    public synchronized void wish(int i, String name) {
        System.out.printf("Hello %d : %s%n", i, name);
    }
}
