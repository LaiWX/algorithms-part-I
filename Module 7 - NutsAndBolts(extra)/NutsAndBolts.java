import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class NutsAndBolts {
    private double[] nuts;
    private double[] bolts;
    NutsAndBolts(int n) {
        nuts = new double[n];
        for (int i = 0; i < n; i++) {
            nuts[i] = StdRandom.uniformDouble();
        }
        bolts = nuts.clone();
        StdRandom.shuffle(bolts);

        sort(nuts, bolts, 0, n - 1);
    }

    private void sort(double[] nuts, double[] bolts, int lo, int hi) {
        if (lo >= hi) return;
        double v = nuts[lo];
        int lt = lo;
        int gt = hi;
        int i = lt;
        while (i <= gt) {
            if (bolts[i] < v) exch(bolts, lt++, i++);
            else if (bolts[i] > v) exch(bolts, i, gt--);
            else i++;
        }

        v = bolts[lt];
        lt = lo;
        gt = hi;
        i = lt;
        while (i <= gt) {
            if (nuts[i] < v) exch(nuts, lt++, i++);
            else if (nuts[i] > v) exch(nuts, i, gt--);
            else i++;
        }

        sort(nuts, bolts, lo, lt - 1);
        sort(nuts, bolts, gt + 1, hi);
    }

    private void exch(double[] a, int i, int j) {
        double temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public boolean isPaired() {
        return isSorted(nuts) && isSorted(bolts);
    }

    private boolean isSorted(double[] items) {
        for(int i = 1; i < items.length; i++) {
            if (items[i-1] > items[i]) return false;
        }
        return true;
    }

    public String toString() {
        return String.format("nuts: \t%s\nbolts\t%s\n",
                             Arrays.toString(nuts),
                             Arrays.toString(bolts));
    }

    public static void main(String[] args) {
        NutsAndBolts nutsAndBolts = new NutsAndBolts(2000);
        System.out.println(nutsAndBolts);
        System.out.println(nutsAndBolts.isPaired());
    }
}
