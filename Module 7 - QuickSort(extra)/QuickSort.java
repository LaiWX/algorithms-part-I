import edu.princeton.cs.algs4.StdRandom;

public class QuickSort {
    public boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            if (a[i].compareTo(a[i-1]) < 0) {
                return false;
            }
        }
        return true;
    }

    public void sortAll(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    private void sort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) return;
        Comparable v = a[lo];
        int lt = lo;
        int i = lo + 1;
        int gt = hi;
        while (i <= gt) {
            if (less(a[i], v)) exch(a, lt++, i++);
            else if (less(v, a[i])) exch(a, i, gt--);
            else i++;
        }
        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }

    private void exch(Comparable[] a, int index1, int index2) {
        Comparable tmp = a[index1];
        a[index1] = a[index2];
        a[index2] = tmp;
    }

    private boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    public static void main(String[] args) {
        int n = 1000;
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) {
            a[i] = StdRandom.uniformInt(10);
        }
        QuickSort quickSort = new QuickSort();
        quickSort.sortAll(a);
        System.out.println(quickSort.isSorted(a, 0, n - 1));
    }
}
