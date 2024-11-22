import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MySemaphore {
    private int permits;
    private final int maxPermits;
    private final Lock lock;
    private final Condition condition;

    public MySemaphore(int initialPermits, boolean fair) {
        if (initialPermits < 0) {
            throw new IllegalArgumentException("Initial permits cannot be negative");
        }
        this.permits = initialPermits;
        this.maxPermits = initialPermits;
        this.lock = new ReentrantLock(fair); // Respect fairness policy
        this.condition = lock.newCondition();
    }

    // Acquire a permit, blocking if none are available
    public void acquire() throws InterruptedException {
        lock.lock();
        try {
            while (permits == 0) {
                condition.await();
            }
            permits--;
        } finally {
            lock.unlock();
        }
    }

    // Try to acquire a permit without blocking, returns true if successful
    public boolean tryAcquire() {
        lock.lock();
        try {
            if (permits > 0) {
                permits--;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    // Release a permit, incrementing the available permits and notifying threads
    public void release() {
        lock.lock();
        try {
            if (permits < maxPermits) {
                permits++;
                condition.signal(); // Notify one waiting thread
            } else {
                throw new IllegalStateException("Permit count exceeded maxPermits");
            }
        } finally {
            lock.unlock();
        }
    }

    // Return the number of available permits
    public int availablePermits() {
        lock.lock();
        try {
            return permits;
        } finally {
            lock.unlock();
        }
    }
}
