public class MinPQ<Key extends Comparable<Key>> {
    private int N;
    private Key pq[];

    MinPQ() {
        N = 0;
        pq = (Key[]) new Comparable[2]; // 1-base
    }

    public void add(Key item) {
        N++;
        pq[N] = item;
        swim(N);
        if (N + 1 == pq.length) changePQSize(N * 2 + 1);
    }

    public Key delMin() {
        Key min = pq[1];
        exch(1, N);
        pq[N--] = null;
        sink(1);
        if (N <= (pq.length - 1) / 4) changePQSize((pq.length - 1) / 2 + 1);
        return min;
    }

    public int size() {
        return N;
    }

    private void swim(int index) {
        while (index > 1 && less(index, index / 2)) {
            exch(index, index/2);
            index /= 2;
        }
    }

    private void sink(int index) {
        while (index * 2 <= N) {
            int j = index * 2;
            if (j + 1 <= N && less(j + 1, j)) j = j + 1;
            if (less(index, j)) break;
            exch(index, j);
            index = j;
        }
    }

    private boolean less(int a, int b) {
        return pq[a].compareTo(pq[b]) < 0;
    }

    private void exch(int index1, int index2) {
        Key temp = pq[index1];
        pq[index1] = pq[index2];
        pq[index2] = temp;
    }

    private void changePQSize(int capacity) {
        if (capacity < N) throw new IllegalArgumentException();
        Key newPQ[] = (Key[]) new Comparable[capacity];
        for (int i = 0; i <= N; i++) {
            newPQ[i] = pq[i];
        }
        pq = newPQ;
    }

    public static void main(String[] args) {
        MinPQ<Integer> minPQ = new MinPQ<>();
        minPQ.add(10);
        minPQ.add(1);
        minPQ.add(3);
        minPQ.add(5);
        minPQ.add(4);
        minPQ.add(20);
        minPQ.add(2);
        for (int i = 1; i <= minPQ.size(); i++) {
            System.out.println(minPQ.delMin());
        }
    }
}
