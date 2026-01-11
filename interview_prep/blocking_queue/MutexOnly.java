package interview_prep.blocking_queue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;;

public class MutexOnly {
    public static void main(String[] args) throws InterruptedException {

        BlockQueueOnlyMutex<Integer> queue = new BlockQueueOnlyMutex<Integer>(10);
        Enqueue eq = new Enqueue(queue);

        Dequeue dq1 = new Dequeue(queue);
        Dequeue dq2 = new Dequeue(queue);

        Thread t1 = new Thread(eq);

        Thread t2 = new Thread(dq1);
        Thread t3 = new Thread(dq2);

        t1.start();
        Thread.sleep(4);

        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();


    }
}

/* BlockQueue Using Only Mutext not synchronized keyword */
class BlockQueueOnlyMutex<T> {
    private final Lock lock; 
    private final T[] array;
    private final int capacity;
    private int size = 0;
    private int head = 0;
    private int tail = 0;

    
    @SuppressWarnings("unchecked")
    public BlockQueueOnlyMutex(int capacity) {
        array = (T[]) new Object[capacity];
        this.capacity = capacity;
        this.lock = new ReentrantLock();
    }

    /* enqueue method */
    public void enqueue(T item) {
        lock.lock();
        while(capacity == size) {
            lock.unlock();
            lock.lock();
        }

        if(tail == capacity) {
            tail = 0;
        }

        array[tail] = item;
        size++;
        tail++;
        lock.unlock();
    }

    /* dequeue method */
    public T dequeue() {
        lock.lock();
        while(size == 0) {
            lock.unlock();
            lock.lock();
        }

        if(head == capacity) {
            head = 0;
        }

        T item = array[head];
        array[head] = null;
        head++;
        size--;
        lock.unlock();
        return item;
    }

}

/* Enqueue Implementation */
class Enqueue implements Runnable {
    private BlockQueueOnlyMutex<Integer> queue = null;

    public Enqueue(BlockQueueOnlyMutex<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for(int i = 1; i <= 50; i++) {
            queue.enqueue(i);
            System.out.println("enqueued " + i);
        }
    }
}

/* Dequeue Implementation */
class Dequeue implements Runnable {
    private BlockQueueOnlyMutex<Integer> queue = null;

    public Dequeue(BlockQueueOnlyMutex<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for(int i = 1; i <= 25; i++) {
            Integer dq = queue.dequeue();
            System.out.println("Dequeued " + dq);
        }
    }
}