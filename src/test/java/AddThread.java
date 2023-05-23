public class AddThread extends TestThread {
    public AddThread(RandomSeq seq, int seqPart, SortList setList) {
        super(seq, seqPart, setList);
    }

    @Override
    public void run() {
        int success = 0;
        int failure = 0;
        for (Integer num : numbers) {
            if (list.add(num)) {
                success++;
            } else {
                failure++;
            }
        }
        Counter.increase(Counter.CounterType.SUCCESS, success);
        Counter.increase(Counter.CounterType.FAILURE, failure);
        Counter.increase(Counter.CounterType.SIZE, success);
    }
}
