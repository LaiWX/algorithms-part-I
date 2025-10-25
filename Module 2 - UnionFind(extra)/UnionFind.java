import java.util.Arrays;

public abstract class UnionFind {
    protected int[] id;

    public UnionFind(int n) {
        id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
    }

    public abstract boolean find(int x, int y);

    public abstract void union(int x, int y);

    private void printFind(int x, int y) {
        System.out.printf("%d %d %s\n", x, y, find(x, y));
    }

    private void printUnion(int x, int y) {
        System.out.printf("before Union: %s\n", Arrays.toString(id));
        union(x, y);
        System.out.printf("union %d %d\n", x, y);
        System.out.printf("after Union: %s\n", Arrays.toString(id));
    }

    void runTest() {
        printFind(0, 0);
        printFind(0, 1);
        printUnion(0, 1);
        printFind(0, 1);
        printFind(1, 3);
        printUnion(2, 1);
        printUnion(2, 3);
        printFind(1, 3);
    }
}
