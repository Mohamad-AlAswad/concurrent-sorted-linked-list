public class RemoveSortedRangeThread extends RemoveThread {
    public RemoveSortedRangeThread(RandomSeq seq, int seqPart, ExtendedRWLockListWithSortRemove setList) {
        super(seq, seqPart, setList);
    }

    @Override
    public void run() {
        int success = ((ExtendedRWLockListWithSortRemove) list).removeRange(numbers);
        Counter.increase(Counter.CounterType.SUCCESS, success);
        Counter.increase(Counter.CounterType.FAILURE, numbers.length - success);
        Counter.decrease(Counter.CounterType.SIZE, success);
    }
}