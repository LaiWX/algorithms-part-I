import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Test {
    public static void main(String[] args) {
        PointSET brute = new PointSET();
        KdTree kdTree = new KdTree();

        int n = 100000;

        for (int i = 0; i < n; i++) {
            double x = StdRandom.uniformDouble();
            double y = StdRandom.uniformDouble();
            Point2D point = new Point2D(x, y);

            brute.insert(point);
            kdTree.insert(point);
        }


        int correctCnt = 0;
        for (int i = 0; i < n; i++) {
            double x = StdRandom.uniformDouble();
            double y = StdRandom.uniformDouble();
            Point2D point = new Point2D(x, y);

            Point2D nearestBrute = brute.nearest(point);
            Point2D nearestKdTree = kdTree.nearest(point);

            if (nearestBrute.equals(nearestKdTree)) {
                correctCnt++;
            }
        }

        StdOut.printf("%d/%d correct\n", correctCnt, n);
    }
}
