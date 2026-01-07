package reentrant_lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReenteredLockImp {
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private int item;
    private boolean hasItem = false;

    /* producer */
    public void produce(int value) throws InterruptedException {
        lock.lock();
        try {
            while (hasItem) {
                notFull.await();
            }

            item = value;
            hasItem = true;
            notEmpty.signal(); // wake consumer
        } finally {
            lock.unlock();
        }
    }

    public int consumer() throws InterruptedException {
        lock.lock();
        try {
            while (!hasItem) {
                notEmpty.await();
            }

            hasItem = false;
            notFull.signal(); // wake producer
            return item;

        } finally {
            lock.unlock();
        }
    }

}
