public class QuickFind extends UnionFind {
    public QuickFind(int n) {
        super(n);
    }

    @Override
    public boolean find(int x, int y) {
        return id[x] == id[y];
    }

    @Override
    public void union(int x, int y) {
        int groupX = id[x];
        int groupY = id[y];
        for (int i = 0; i < id.length; i++) {
            if (id[i] == groupY) {
                id[i] = groupX;
            }
        }
    }

    public static void main(String[] args) {
        new QuickFind(10).runTest();
    }
}
