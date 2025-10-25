import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int n = (int)1e4;

        int[][] unionList = new int[n][2];
        int[][] testList = new int[n*10][2];
        int seed = 42;
        Random random = new Random(seed);
        for (int i = 0; i < unionList.length; i++) {
            unionList[i][0] = random.nextInt(n);
            unionList[i][1] = random.nextInt(n);
        }
        for (int i = 0; i < testList.length; i++) {
            testList[i][0] = random.nextInt(n);
            testList[i][1] = random.nextInt(n);
        }

        // init
        // UnionFind qf = new QuickFind(n);
        // UnionFind qn = new QuickUnion(n);
        UnionFind wqu = new WeightedQuickUnion(n);
        // UnionFind quwpc = new QUWPC(n);

        // test find and union
        // test_all(qf, unionList, "QuickFind");
        // test_all(qn, unionList, "QuickUnion");
        // test_all(quwpc, unionList, "QuickUnionWithPathComp");
        test_all(wqu, unionList, "WeightedQuickUnion");

        // test find
        // boolean[] qf_test = test_find(qf, testList, "QuickFind");
        // boolean[] qn_test = test_find(qn, testList, "QuickUnion");
        // boolean[] quwpc_test = test_find(quwpc, testList, "QuickUnionWithPathComp");
        boolean[] wqu_test = test_find(wqu, unionList, "WeightedQuickUnion");

        // System.out.println(Arrays.equals(wqu_test, quwpc_test));
    }

    public static boolean[] test_find(UnionFind uf, int[][] findList, String testName) {
        boolean[] res = new boolean[findList.length];
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < findList.length; i++) {
            res[i] = uf.find(findList[i][0], findList[i][1]);
        }
        long endTime = System.currentTimeMillis();
        System.out.println(testName + " find 耗时： " + (endTime - startTime) + "ms");
        return res;
    }

    public static void test_all(UnionFind uf, int[][] unionList, String testName){
        long startTime = System.currentTimeMillis();
        for (int[] ints : unionList) {
            int x = ints[0];
            int y = ints[1];
            if (!uf.find(x, y)) {
                uf.union(x, y);
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println(testName + " find + union 耗时： " + (endTime - startTime) + "ms");
    }
}