public abstract class TestThread implements Runnable {
    SortList list;

    Integer[] numbers;

    public TestThread(RandomSeq seq, int seqPart, SortList setList) {
        this.list = setList;
        this.numbers = new Integer[seqPart];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = seq.next();
        }
    }
}
