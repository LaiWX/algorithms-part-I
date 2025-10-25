public class WeightedQuickUnion extends UnionFind {
    int[] size;

    public WeightedQuickUnion(int n) {
        super(n);
        size = new int[n];
        for (int i = 0; i < n; i++) {
            size[i] = 1;
        }
    }

    @Override
    public boolean find(int x, int y) {
        return root(x) == root(y);
    }

    @Override
    public void union(int x, int y) {
        int rootX = root(x);
        int rootY = root(y);
        if (size[rootX] > size[rootY]) {
            id[rootY] = rootX;
            size[rootX] += size[rootY];
        } else {
            id[rootX] = rootY;
            size[rootY] += size[rootX];
        }
    }

    private int root(int i) {
        while (id[i] != i) {
            i = id[i];
        }
        return i;
    }

    public static void main(String[] args) {
        new WeightedQuickUnion(10).runTest();
    }
}
