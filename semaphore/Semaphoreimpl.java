package semaphore;

import java.util.concurrent.Semaphore;;

public class Semaphoreimpl {
    public static void main(String[] args) throws InterruptedException {
        Implementation.example();
    }
}

/* Implementation */
class Implementation {

    public static void example() throws InterruptedException {
        final Semaphore semaphore = new Semaphore(1);

        Thread signaller = new Thread(() -> {
            semaphore.release();
            System.out.println("Sent signal");
        });

        Thread waiter = new Thread(() -> {
            try {
                semaphore.acquire();
                System.out.println("Received signal");
            } catch(InterruptedException ie) {

            }
        });

        signaller.start();
        signaller.join();
        Thread.sleep(5000);
        waiter.start();
        waiter.join();

        System.out.println("Program Exiting.");
    }
}
