package interview_prep.rate_limiting;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        RateLimiting rateLimit= new RateLimiting(10); 
        RunnableThreadImpl r = new RunnableThreadImpl(rateLimit);

        
        Set<Thread> allThreads = new HashSet<>();
        for(int i = 0; i < 10; i++) {
            Thread t = new Thread(r);
            Thread.currentThread().setName("Thread_" + (i+1));
            allThreads.add(t);
        }

        for(Thread t : allThreads) {
            t.start();
        }

        for (Thread t : allThreads) {
            t.join();
        }
    }
}

/* Rate Limit implemenation class */
class RateLimiting {
    private int MAX_TOKENS;
    private long lastRequest = System.currentTimeMillis();
    long possibleTokens = 0;

    public RateLimiting(int maxTokens) {
        this.MAX_TOKENS = maxTokens;
    }

    /* get tokens each seconds */
    synchronized public void getTokens() throws InterruptedException {
        possibleTokens += (System.currentTimeMillis() - lastRequest) / 1000;

        if(possibleTokens > MAX_TOKENS) {
            possibleTokens = MAX_TOKENS;
        } else if(possibleTokens == 0) {
            Thread.sleep(1000);
        } else {
            possibleTokens--;
        }
        lastRequest = System.currentTimeMillis();
        System.out.println("Granting " + Thread.currentThread().getName() + " token at " + (System.currentTimeMillis() / 1000));
    }
}

/* Runnable implemetation */
class RunnableThreadImpl implements Runnable {
    
    RateLimiting rateLimiting;

    public RunnableThreadImpl(RateLimiting rateLimiting) {
        this.rateLimiting = rateLimiting;
    }

    @Override
    public void run() {
            try {
                rateLimiting.getTokens();
            } catch (InterruptedException ie) {
                System.out.println("We have a problem");
            }
    }
}
