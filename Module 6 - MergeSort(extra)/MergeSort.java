import edu.princeton.cs.algs4.StdRandom;

public class MergeSort {
    public boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            if (a[i].compareTo(a[i-1]) < 0) {
                return false;
            }
        }
        return true;
    }

    public void sortAll(Comparable[] a) {
        Comparable[] aux = a.clone();
        sort(a, aux, 0, a.length - 1);
    }

    private void sort(Comparable[] dst, Comparable[] src, int lo, int hi) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        sort(src, dst, lo, mid);
        sort(src, dst, mid + 1, hi);
        merge(dst, src, lo, mid, hi);
    }

    private void merge(Comparable[] dst, Comparable[] src, int lo, int mid, int hi) {
        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid)                    dst[k] = src[j++];
            else if (j > hi)                dst[k] = src[i++];
            else if (less(src[j], src[i]))  dst[k] = src[j++];
            else                            dst[k] = src[i++];
        }
    }

    private boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    public static void main(String[] args) {
        int n = 10000;
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) {
            a[i] = StdRandom.uniformInt(n);
        }
        MergeSort mergeSort = new MergeSort();
        mergeSort.sortAll(a);
        System.out.println(mergeSort.isSorted(a, 0, n - 1));
    }
}
