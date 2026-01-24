package java_concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureInterface {
    static ExecutorService esPool = Executors.newFixedThreadPool(5);
   public static void main(String[] args) throws Exception {
        int result = findSum();
        System.out.println("result " + result);
        esPool.shutdown();
   } 

   /*  Using Future interface to hold the result of asynchronous task */
   public static int findSum() throws InterruptedException, ExecutionException {
       int result = -1;
       Callable<Integer> task1 = new Callable<Integer>() {
           @Override
           public Integer call() {
                int sum = 0;
                for(int i = 1; i <= 10; i++) {
                    sum+=i;
                }
                return sum;
            }
        };

        Future<Integer> f = esPool.submit(task1);
        while(!f.isDone()) {
            System.out.println("Waiting...");
        }
        result = f.get();
        return result;
   }
}



