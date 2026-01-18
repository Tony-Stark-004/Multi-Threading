package java_concurrency;

import java.util.concurrent.Executor;

public class ExecutorClass {
    public static void main(String[] args) {
        DumbExecutor de = new DumbExecutor();
        MyTask mt = new MyTask();
        de.execute(mt);

    }
}

/* Creating own executor */
class DumbExecutor implements Executor {
    @Override
    public void execute(Runnable r) {
        Thread t = new Thread(r);
        t.start();
    }
}

/* implementing Runnable class */
class MyTask implements Runnable {
    @Override
    public void run() {
        System.out.println("MyTask is running now ...");
    }
}
 