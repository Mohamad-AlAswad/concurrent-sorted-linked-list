import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class SyncListTest extends TestCase {
    int randLen = 40_000;
    int maxNum = 100_000;
    int seed = 0;
    int totalThreads = 4;

    ResultTesting data = new ResultTesting(randLen, maxNum, seed, totalThreads);
    ResultTesting.Record record;

    public void testRun() {
        record = new ResultTesting.Record("Synchronization");
        data.records.add(record);
        testHelp(record, new SyncList(), RemoveThread::new);


        record = new ResultTesting.Record("RWLock");
        data.records.add(record);
        testHelp(record, new RWLockList(), RemoveThread::new);


        record = new ResultTesting.Record("Lock");
        data.records.add(record);
        testHelp(record, new LockList(), RemoveThread::new);


        record = new ResultTesting.Record("Extended RWLock");
        data.records.add(record);
        testHelp(record, new ExtendedRWLockList(),
                (seq, seqPart, setList) -> new RemoveRangeThread(seq, seqPart, (ExtendedRWLockList) setList)
        );


        record = new ResultTesting.Record("Sort Extended RWLock");
        data.records.add(record);
        testHelp(record, new ExtendedRWLockListWithSortRemove(),
                (seq, seqPart, setList) -> new RemoveSortedRangeThread(seq, seqPart, (ExtendedRWLockListWithSortRemove) setList)
        );

        System.out.println(data);
    }


    private void testHelp(ResultTesting.Record record, SortList list, IRemoveFactory factory) {
        RandomSeq seq = new RandomSeq(seed, maxNum);
        List<TestThread> threadsA = new ArrayList<>();
        List<TestThread> threadsC = new ArrayList<>();
        List<TestThread> threadsR = new ArrayList<>();

        for (int i = 0; i < totalThreads; i++) {
            threadsA.add(new AddThread(seq, randLen / totalThreads, list));
            threadsC.add(new ContainThread(seq, randLen / totalThreads, list));
            threadsR.add(factory.factory(seq, randLen / totalThreads, list));
        }

        // Add
        Counter.clear(Counter.CounterType.SIZE);
        Counter.clear(Counter.CounterType.FAILURE);
        Counter.clear(Counter.CounterType.SUCCESS);
        record.addTime = testHelperThreads(threadsA);
        record.lengthAfterAdd = Counter.get(Counter.CounterType.SIZE);
        assert list.isSorted();


        // Contain
        Counter.clear(Counter.CounterType.FAILURE);
        Counter.clear(Counter.CounterType.SUCCESS);
        record.containTime = testHelperThreads(threadsC);
        record.successContainTimes = Counter.get(Counter.CounterType.SUCCESS);
        record.failureContainTimes = Counter.get(Counter.CounterType.FAILURE);


        // Remove
        Counter.clear(Counter.CounterType.FAILURE);
        Counter.clear(Counter.CounterType.SUCCESS);
        record.removeTime = testHelperThreads(threadsR);
        record.successRemoveTimes = Counter.get(Counter.CounterType.SUCCESS);
        record.failureRemoveTimes = Counter.get(Counter.CounterType.FAILURE);
        record.lengthAfterRemove = Counter.get(Counter.CounterType.SIZE);
        assert list.isSorted();
    }


    private long testHelperThreads(List<TestThread> testThreads) {

        long start = System.currentTimeMillis();

        List<Thread> threads = testThreads.stream().map(Thread::new).toList();

        threads.stream().forEach(e -> e.start());
        threads.stream().forEach(e -> {
            try {
                e.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        return System.currentTimeMillis() - start;
    }

}


