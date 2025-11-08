import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private int segmentsCount = 0;
    private LineSegment[] segments = new LineSegment[1];

    public BruteCollinearPoints(Point[] points) {
        checkPoints(points);
        if (points.length < 4) {
            return;
        }

        for (int i = 0; i < points.length; i++) {
            Point point1 = points[i];
            for (int j = i + 1; j < points.length; j++) {
                Point point2 = points[j];
                for (int k = j + 1; k < points.length; k++) {
                    Point point3 = points[k];
                    for (int m = k + 1; m < points.length; m++) {
                        Point point4 = points[m];

                        double slope1t2 = point1.slopeTo(point2);
                        double slope2t3 = point2.slopeTo(point3);
                        double slope3t4 = point3.slopeTo(point4);

                        if (slope1t2 == slope2t3 && slope2t3 == slope3t4) {
                            Point max = point1;
                            if (point2.compareTo(max) > 0) max = point2;
                            if (point3.compareTo(max) > 0) max = point3;
                            if (point4.compareTo(max) > 0) max = point4;

                            Point min = point1;
                            if (point2.compareTo(min) < 0) min = point2;
                            if (point3.compareTo(min) < 0) min = point3;
                            if (point4.compareTo(min) < 0) min = point4;

                            segments[segmentsCount++] = new LineSegment(min, max);
                            if (segmentsCount == segments.length) {
                                increaseLineSegmentArray();
                            }
                        }
                    }
                }
            }
        }
    }

    private void checkPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
        }

        for (int i = 0; i < points.length; i++) {
            Point point1 = points[i];
            for (int j = i + 1; j < points.length; j++) {
                Point point2 = points[j];
                if (point1.compareTo(point2) == 0) throw new IllegalArgumentException();
            }
        }
    }

    private void increaseLineSegmentArray() {
        LineSegment[] newArray = new LineSegment[segments.length * 2];
        for (int i = 0; i < segments.length; i++) {
            newArray[i] = segments[i];
        }
        segments = newArray;
    }

    public int numberOfSegments() {
        return segmentsCount;
    }

    public LineSegment[] segments() {
        LineSegment[] results = new LineSegment[segmentsCount];
        for (int i = 0; i < segmentsCount; i++) {
            results[i] = segments[i];
        }
        return results;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
