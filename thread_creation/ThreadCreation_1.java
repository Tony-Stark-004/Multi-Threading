public class ThreadCreation_1 {
    public static void main(String[] args) {
        System.out.printf("Main --> %s%n",Thread.currentThread().getName());

        MyThread childThread = new MyThread();
        childThread.start();


        for(int i = 1; i <= 10; i++) {
            System.out.printf("Main %d%n", i);
        }
    } 
}



/* 1. extending Thread class */
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.printf("Child --> %s%n", Thread.currentThread().getName());
        for(int i = 1; i <= 10; i++) {
            System.out.printf("Child Thread %d%n", i);
        }
    }
}


