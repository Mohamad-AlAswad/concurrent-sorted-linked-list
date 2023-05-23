import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ExtendedRWLockList extends SortList {
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    DoublyEntry head;

    public ExtendedRWLockList() {
        super();
        head = new DoublyEntry(Integer.MIN_VALUE, null);
        head.next = new DoublyEntry(Integer.MAX_VALUE, head);
    }

    public int removeRange(Integer[] objects) {
        List<DoublyEntry> queue = new ArrayList<>();
        int deleted = 0;
        try {
            lock.readLock().lock();
            for (int i = 0; i < objects.length; ) {
                DoublyEntry curr = head;
                do {
                    while (curr.object.compareTo(objects[i]) < 0) {
                        curr = curr.next;
                    }
                    if (curr.object.equals(objects[i])) {
                        queue.add(curr);
                    }
                    i++;
                } while (i < objects.length && objects[i - 1] <= objects[i]);
            }
        } finally {
            lock.readLock().unlock();
        }

        try {
            lock.writeLock().lock();
            for (DoublyEntry node : queue) {
                if (!node.deleted) {
                    node.prev.next = node.next;
                    node.next.prev = node.prev;
                    node.deleted = true;
                    deleted++;
                }
            }
        } finally {
            lock.writeLock().unlock();
        }

        return deleted;
    }

    @Override
    public boolean add(Integer obj) {
        try {
            lock.writeLock().lock();

            DoublyEntry curr = head;
            while (curr.object.compareTo(obj) < 0) {
                curr = curr.next;
            }

            if (curr.object.equals(obj)) {
                return false;
            } else {
                DoublyEntry newEntry = new DoublyEntry(obj, curr.prev);
                curr.prev = newEntry;
                newEntry.next = curr;
                newEntry.prev.next = newEntry;
                return true;
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean remove(Integer obj) {
        try {
            lock.writeLock().lock();

            DoublyEntry curr = head;
            while (curr.object.compareTo(obj) < 0) {
                curr = curr.next;
            }

            if (!curr.object.equals(obj)) {
                return false;
            } else {
                curr.prev.next = curr.next;
                curr.next.prev = curr.prev;
                return true;
            }
        } finally {
            lock.writeLock().unlock();
        }

    }

    @Override
    public boolean contain(Integer obj) {
        try {
            lock.readLock().lock();

            DoublyEntry curr = head;
            while (curr.object.compareTo(obj) < 0) {
                curr = curr.next;
            }

            return curr.object.equals(obj);
        } finally {
            lock.readLock().unlock();
        }
    }

    public boolean isSorted() {
        DoublyEntry curr = this.head;
        while (curr != null) {
            if (curr.next == null)
                return true;
            if (curr.object >= curr.next.object)
                return false;
            curr = curr.next;
        }
        return true;
    }

    static class DoublyEntry extends Entry {
        DoublyEntry prev;
        DoublyEntry next;
        boolean deleted;

        public DoublyEntry(Integer object, DoublyEntry prev) {
            super(object);
            this.prev = prev;
            deleted = false;
        }
    }
}
