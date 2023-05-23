import java.util.Arrays;

public class ExtendedRWLockListWithSortRemove extends ExtendedRWLockList {

    public ExtendedRWLockListWithSortRemove() {
        super();
    }

    @Override
    public int removeRange(Integer[] objects) {
        Arrays.sort(objects);
        return super.removeRange(objects);
    }
}
