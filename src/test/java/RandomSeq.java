import java.util.Random;

public class RandomSeq {
    Random random;
    int maxNum;
    int seed;

    public RandomSeq(int seed, int maxNum) {
        random = new Random(seed);
        this.seed = seed;
        this.maxNum = maxNum;
    }

    public int next() {
        return random.nextInt(maxNum);
    }
}
