import java.util.ArrayList;
import java.util.List;

class ResultTesting {
    int randLen, maxNum, seed, totalThreads;
    List<Record> records;

    public ResultTesting(int randLen, int maxNum, int seed, int totalThreads) {
        this.randLen = randLen;
        this.maxNum = maxNum;
        this.seed = seed;
        this.totalThreads = totalThreads;
        records = new ArrayList<>();
    }

    static String formatter(String str) {
        return String.format("%-15s", str);
    }

    static String formatterMethod(String str) {
        return String.format("%-25s", str);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Number of threads:      ").append(totalThreads).append("\n");
        stringBuilder.append("Used seed:              ").append(seed).append("\n");
        stringBuilder.append("Random Sequence Length: ").append(randLen).append("\n");
        stringBuilder.append("Maximum Random Number:  ").append(maxNum).append("\n");
        stringBuilder.append("\n");

        stringBuilder
                .append(formatterMethod(""))
                .append(formatter("Add T(ms)"))
                .append(formatter("Contain T(ms)"))
                .append(formatter("Remove T(ms)"))
                .append(formatter("Len After A"))
                .append(formatter("Len After R"))
                .append(formatter("Success C"))
                .append(formatter("Failures C"))
                .append(formatter("Success R"))
                .append(formatter("Failures R"))
                .append("\n");

        records.stream()
                .map(Record::toString)
                .forEach(stringBuilder::append);

        return stringBuilder.toString();
    }

    static class Record {
        public String method;
        public long addTime, containTime, removeTime;
        public int lengthAfterAdd, lengthAfterRemove, successRemoveTimes;
        public int failureRemoveTimes, successContainTimes, failureContainTimes;

        public Record(String method) {
            this.method = method;
        }

        @Override
        public String toString() {
            return formatterMethod(method) +
                    formatter(Long.toString(addTime)) +
                    formatter(Long.toString(containTime)) +
                    formatter(Long.toString(removeTime)) +
                    formatter(Long.toString(lengthAfterAdd)) +
                    formatter(Long.toString(lengthAfterRemove)) +
                    formatter(Long.toString(successContainTimes)) +
                    formatter(Long.toString(failureContainTimes)) +
                    formatter(Long.toString(successRemoveTimes)) +
                    formatter(Long.toString(failureRemoveTimes)) +
                    "\n";
        }
    }
}
