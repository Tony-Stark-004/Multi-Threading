package virtual_thread;

public class VirtualThread {
    public static void main(String[] args) throws InterruptedException {
        MyRunnable r = new MyRunnable();
        Thread vt1 = Thread.startVirtualThread(r);
        vt1.join();

        System.out.println("vr2");
        Thread vt2 = Thread.ofVirtual().start(r);
        vt2.join();  
        /* 
            join is required because virtual thread is inside plateform thread and 
            if plateform thread runs completely then virtual thread dies whether it completed the task or not
        */ 

        System.out.println("vr3");
        Thread vr3 = Thread.ofVirtual().unstarted(r);
        vr3.start();
        vr3.join();


        for (int i = 1; i <= 10; i++) {
            System.out.printf("Main %d%n", i);
        }
    }
}

/* Implements Runnable interface */
class MyRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.printf("Child vritual thread %d%n", i);
        }
    }
}
