import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
    private SET<Point2D> set;

    // construct an empty set of points
    public PointSET() {
        set = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        set.add(p);
    }

    // dose the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p: set) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();

        Bag<Point2D> bag = new Bag<>();

        for (Point2D point2D: set) {
            boolean isXInside = point2D.x() <= rect.xmax() && point2D.x() >= rect.xmin();
            boolean isYInside = point2D.y() <= rect.ymax() && point2D.y() >= rect.ymin();
            if (isXInside && isYInside) {
                bag.add(point2D);
            }
        }

        return bag;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        double minDistSq = Double.POSITIVE_INFINITY;
        Point2D nearest = null;

        for (Point2D point2D: set) {
            double distSq = point2D.distanceSquaredTo(p);
            if (distSq < minDistSq) {
                minDistSq = distSq;
                nearest = point2D;
            }
        }

        return nearest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
