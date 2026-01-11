package thread_exec_prevention;

public class Join {
    public static void main(String[] args) throws InterruptedException {
        MyRunnableJoin r = new MyRunnableJoin();
        Thread t = new Thread(r);
        t.start();
        t.join();

        for (int i = 1; i <= 10; i++) {
            System.out.printf("Main %d%n", i);
        }
    }
}


/* implementing Runnable interface */
class MyRunnableJoin implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.printf("Child thread %d%n", i);
        }
    }
}