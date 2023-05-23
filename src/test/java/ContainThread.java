public class ContainThread extends TestThread {
    public ContainThread(RandomSeq seq, int seqPart, SortList setList) {
        super(seq, seqPart, setList);
    }

    @Override
    public void run() {
        int success = 0;
        int failure = 0;
        for (Integer num : numbers) {
            if (list.contain(num)) {
                success++;
            } else {
                failure++;
            }
        }

        Counter.increase(Counter.CounterType.SUCCESS, success);
        Counter.increase(Counter.CounterType.FAILURE, failure);

    }
}
