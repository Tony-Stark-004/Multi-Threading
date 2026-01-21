package java_concurrency;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolClass {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 5, 1,
                TimeUnit.MINUTES,
                new LinkedBlockingDeque<>(3),
                new ThreadPoolExecutor.AbortPolicy());

        try {
            for (int i = 0; i < 6; i++) {
                executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("This is worker thread " + Thread.currentThread().getName() + " executing");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ie) {

                        }
                    }
                });
            }
        } finally {
            executor.shutdown();
        }

    }
}
