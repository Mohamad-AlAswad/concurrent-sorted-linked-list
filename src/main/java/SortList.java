public abstract class SortList {

    public Entry head;

    public SortList() {
        this.head = new Entry(Integer.MIN_VALUE);
        this.head.next = new Entry(Integer.MAX_VALUE);
    }

    public abstract boolean add(Integer obj);

    public abstract boolean remove(Integer obj);

    public abstract boolean contain(Integer obj);

    public boolean isSorted() {
        Entry curr = this.head;
        while (curr != null) {
            if (curr.next == null)
                return true;
            if (curr.object >= curr.next.object)
                return false;
            curr = curr.next;
        }
        return true;
    }

}
