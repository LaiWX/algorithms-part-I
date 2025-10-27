import java.util.Arrays;
import java.util.Random;

public class DutchFlag {
    private static final int RED = -1;
    private static final int WHITE = 0;
    private static final int BLUE = 1;
    private int[] buckets;
    private int swapTimes;
    private int readTimes;

    public DutchFlag(int n) {
        Random random = new Random();
        buckets = new int[n];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = random.nextInt(3) - 1; // -1, 0, 1
        }

        swapTimes = 0;
        readTimes = 0;
    }

    public void swap(int a, int b) {
        int temp = buckets[a];
        buckets[a] = buckets[b];
        buckets[b] = temp;
        swapTimes++;
    }

    public int color(int a) {
        readTimes++;
        return buckets[a];
    }

    public int getSwapTimes() {
        return swapTimes;
    }

    public int getReadTimes() {
        return readTimes;
    }

    public boolean isSorted() {
        for (int i = 1; i < buckets.length; i++) {
            if (buckets[i] < buckets[i-1]) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        return Arrays.toString(buckets);
    }

    public static void main(String[] args) {
        int N = 1000000;
        DutchFlag dutchFlag = new DutchFlag(N);
        int left = 0;
        int mid = left;
        int right = N - 1;
        while(mid <= right) {
            int temp = dutchFlag.color(mid);
            if (temp == RED) {
                dutchFlag.swap(left, mid);
                left++;
                mid++;
            } else if (temp == BLUE) {
                dutchFlag.swap(mid, right);
                right--;
            } else {
                mid++;
            }
        }
        System.out.printf("swap times: %d/%d\n", dutchFlag.getSwapTimes(), N);
        System.out.printf("read times: %d/%d\n", dutchFlag.getReadTimes(), N);
        System.out.printf("is sorted: %b", dutchFlag.isSorted());
    }
}
