public class ThreadCreation_2 {
    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();
        Thread thread = new Thread(myRunnable);
        thread.start();

        for (int i = 1; i <= 10; i++) {
            System.out.printf("Main %d%n", i);
        }
    }


}


/* implementing Runnable interface */
class MyRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.printf("Child Runnable %d%n", i);
        }
    }
}
