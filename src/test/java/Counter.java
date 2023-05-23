import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class Counter {
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private int counter;

    private Counter() {
        counter = 0;
    }

    public static void clear(CounterType counterType) {
        try {
            lock.writeLock().lock();
            Holder.map.get(counterType).counter = 0;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static void increase(CounterType counterType, int x) {
        try {
            lock.writeLock().lock();
            Holder.map.get(counterType).counter += x;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static void decrease(CounterType counterType, int x) {
        try {
            lock.writeLock().lock();
            Holder.map.get(counterType).counter -= x;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static int get(CounterType counterType) {
        try {
            lock.readLock().lock();
            return Holder.map.get(counterType).counter;
        } finally {
            lock.readLock().unlock();
        }
    }


    enum CounterType {
        SIZE,
        SUCCESS,
        FAILURE
    }

    private static class Holder {
        static final Map<CounterType, Counter> map = new TreeMap<>();

        static {
            map.put(CounterType.SIZE, new Counter());
            map.put(CounterType.FAILURE, new Counter());
            map.put(CounterType.SUCCESS, new Counter());
        }
    }
}
