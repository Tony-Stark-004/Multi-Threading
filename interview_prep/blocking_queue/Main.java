package interview_prep.blocking_queue;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> queue = new BlockingQueue<Integer>(5);
        Enqueue eqr = new Enqueue(queue);

        Dequeue dqr1 = new Dequeue(queue);
        Dequeue dqr2 = new Dequeue(queue);

        Thread t1 = new Thread(eqr);
        Thread t2 = new Thread(dqr1);
        Thread t3 = new Thread(dqr2);

        t1.start();
        Thread.sleep(4000);
        t2.start();

        t2.join();

        t3.start();
        t1.join();
        t3.join();

    }
}

/* Blocking Queue implementation class */
class BlockingQueue<T> {

    T[] array;
    int capacity;
    int size = 0;
    int head = 0;
    int tail = 0;

    @SuppressWarnings("unchecked")
    public BlockingQueue(int capacity) {
        array = (T[]) new Object[capacity];
        this.capacity = capacity;
    }

    /* Enqueue in queue */
    public synchronized void enqueue(T item) throws InterruptedException {
        while (size == capacity) {
            this.wait();
        }

        if(tail == capacity) {
            tail = 0;
        }

        array[tail] = item;
        size++;
        tail++;
        this.notifyAll();
    }

    /* Dequeue from the queue */
    public synchronized T dequeue() throws InterruptedException {
        while (size == 0) {
            this.wait();
        }

        if(head == capacity) {
            head = 0;
        }
        T item = array[head];
        array[head] = null;
        head++;
        size--;

        this.notifyAll();
        return item;
    }
}

/* Enqueue Runnable implementations */
class Enqueue implements Runnable {
    private BlockingQueue<Integer> queue = null;

    public Enqueue(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 50; i++) {
                queue.enqueue(i);
                System.out.println("enqueued " + i);
            }
        } catch (InterruptedException ie) {

        }
    }
}

/* Dequeue Runnable implementations */
class Dequeue implements Runnable {
    private BlockingQueue<Integer> queue = null;

    public Dequeue(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 25; i++) {
                Integer dq = queue.dequeue();
                System.out.println("Dequeued " + dq);
            }
        } catch (InterruptedException ie) {

        }
    }
}