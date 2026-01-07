package thread_memory;

public class SharedResources {
    public static void main(String[] args) throws InterruptedException {
        MyRunnable r = new MyRunnable();
        Thread t1 = new Thread(r, "Thread one");
        t1.start();

        Thread t2 = new Thread(r, "Thread two");
        t2.start();

        System.out.println("Main");
    }
}

/* Runnable interface */
class MyRunnable implements Runnable {
    int count = 0;

    @Override
    public void run() {
        System.out.printf("Current obj %s%n", this);
        for(int i = 1; i <= 1_00_000; i++) {
            synchronized(this) {
                this.count++;
            }
        }

        System.out.println("count: " + Thread.currentThread().getName() + " " + count);
    }
}
