package interview_prep.blocking_queue;

public class SemaphoreImpl {
    public static void main(String[] args) {

    }
}

/* Blocking queue logic using CountingSemaphore */
class BlockingQueueSemaphore<T> {
    T[] arr;
    int capacity;
    int size = 0;
    int tail = 0;
    int head = 0;

    CountingSemaphore semLock = new CountingSemaphore(1, 1);
    CountingSemaphore semConsumser;
    CountingSemaphore semProducer;

    @SuppressWarnings("unchecked")
    public BlockingQueueSemaphore(int capacity) {
        arr = (T[]) new Object[capacity];
        this.capacity = capacity;
        semProducer = new CountingSemaphore(capacity, capacity);
        semConsumser = new CountingSemaphore(capacity, 0);
    }

    /* enqueue method */
    public void enqueue(T item) {
        semProducer.accquire();
        semLock.accquire();

        if(tail == capacity) {
            tail = 0;
        }

        arr[tail] = item;
        tail++;
        size++;

        semLock.release();
        semConsumser.release();

    }

    /* dequeue method */
    public T dequeue() {
        semConsumser.accquire();
        semLock.accquire();

        if(head == capacity) {
            head = 0;
        }

        T item = arr[head];
        arr[head] = null;
        head++;
        size--;

        semLock.release();
        semProducer.release();
        return item;
    }
}

/* Counting Semaphore */
class CountingSemaphore {
    private int permits;
    private final int maxPermits;

    public CountingSemaphore(int maxPermits, int initialPermits) {
        this.maxPermits = maxPermits;
        this.permits = initialPermits;
    }

    public synchronized void accquire() {
        while (permits == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        permits--;
        notifyAll();
    }

    public synchronized void release() {
        while (permits == maxPermits) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        permits++;
        notifyAll();
    }
}

