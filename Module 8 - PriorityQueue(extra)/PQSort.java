import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class PQSort {
    public static <Key extends Comparable<Key>> void sort(Key[] items) {
        MinPQ<Key> minPQ = new MinPQ<>();
        for (Key item: items) {
            minPQ.add(item);
        }
        for (int i = 0; i < items.length; i++) {
            items[i] = minPQ.delMin();
        }
    }

    public static <Key extends Comparable<Key>> boolean isSorted(Key[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i].compareTo(a[i-1]) < 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        int n = 1000;
        Integer[] a = new Integer[n];
        for (int i = 0; i < a.length; i++) {
            a[i] = StdRandom.uniformInt(n/10);
        }
        System.out.println(Arrays.toString(a));
        PQSort.sort(a);
        System.out.println(Arrays.toString(a));
        System.out.println(PQSort.isSorted(a));
    }
}
