public class QuickUnion extends UnionFind {
    public QuickUnion(int n) {
        super(n);
    }

    @Override
    public boolean find(int x, int y) {
        return root(x) == root(y);
    }

    @Override
    public void union(int x, int y) {
        int rootX = root(x);
        int rootY = root(y);
        id[rootY] = rootX;
    }

    private int root(int i) {
        while (id[i] != i) {
            i = id[i];
        }
        return i;
    }

    public static void main(String[] args) {
        new QuickUnion(10).runTest();
    }
}
