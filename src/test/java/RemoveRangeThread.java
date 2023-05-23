public class RemoveRangeThread extends RemoveThread {
    public RemoveRangeThread(RandomSeq seq, int seqPart, ExtendedRWLockList setList) {
        super(seq, seqPart, setList);
    }

    @Override
    public void run() {
        int success = ((ExtendedRWLockList) list).removeRange(numbers);
        Counter.increase(Counter.CounterType.SUCCESS, success);
        Counter.increase(Counter.CounterType.FAILURE, numbers.length - success);
        Counter.decrease(Counter.CounterType.SIZE, success);
    }
}