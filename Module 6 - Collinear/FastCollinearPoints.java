import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private int segmentsCount = 0;
    private LineSegment[] segments = new LineSegment[1];

    public FastCollinearPoints(Point[] points) {
        checkPoints(points);
        if (points.length < 4) {
            return;
        }

        for (Point point: points) {
            Point[] pointsToSort = points.clone();
            Arrays.sort(pointsToSort, 0, points.length, point.slopeOrder());

            double sameSlope = Double.NEGATIVE_INFINITY;
            int sameStartIndex = 1;
            for (int i = 1; i < points.length; i++) {
                double slope = pointsToSort[i].slopeTo(point);
                if (slope != sameSlope) {
                    if (i - sameStartIndex >= 3) {
                        saveSegment(point, pointsToSort, sameStartIndex, i - 1);
                    }
                    sameSlope = slope;
                    sameStartIndex = i;
                } else if (i == points.length - 1 && i - sameStartIndex >= 2) {
                    saveSegment(point, pointsToSort, sameStartIndex, i);
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

    private void saveSegment(Point thisPoint, Point[] points, int startIndex, int endIndex) {
        Point max = thisPoint;
        Point min = thisPoint;
        for (int i = startIndex; i <= endIndex; i++) {
            if (max.compareTo(points[i]) < 0) {
                max = points[i];
            }
            if (min.compareTo(points[i]) > 0) {
                min = points[i];
            }
        }
        LineSegment segmentToAdd = new LineSegment(min, max);
        if (thisPoint.compareTo(min) == 0) {
            segments[segmentsCount++] = segmentToAdd;
            if (segments.length == segmentsCount) increaseLineSegmentArray();
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
