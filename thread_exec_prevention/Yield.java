package thread_exec_prevention;

public class Yield {
    public static void main(String[] args) {
        System.out.println("Main priority " + Thread.currentThread().getPriority());

        MyRunnable r = new MyRunnable();
        Thread t = new Thread(r);
        t.setPriority(8);
        t.start();
    
        for (int i = 1; i <= 10; i++) {
            System.out.printf("Main %d%n", i);
        }
    }
}

/* child thread */
class MyRunnable implements Runnable {
    @Override
    public void run() {
        Thread.yield();
        System.out.println("Child Priority is " + Thread.currentThread().getPriority());
        for (int i = 1; i <= 10; i++) {
            System.out.printf("Child thread %d%n", i);
        }
    }
}