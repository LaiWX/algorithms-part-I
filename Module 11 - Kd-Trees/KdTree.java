import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private Node root = null;
    private int size = 0;

    private class Node {
        Point2D p;

        Node left, right;

        boolean isHType;

        Node(Point2D p, boolean isHType) {
            this.p = p;
            this.isHType = isHType;
        }

        public boolean largerThanPoint(Point2D point) {
            if (point == null) throw new IllegalArgumentException();

            if (isHType) {
                return this.p.y() > point.y();
            } else {
                return this.p.x() > point.x();
            }
        }
    }

    public KdTree() {

    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        root = insert(root, p, false);
    }

    private Node insert(Node node, Point2D p, boolean isHType) {
        if (node == null) {
            size++;
            return new Node(p, isHType);
        }

        if (node.p.equals(p)) return node;

        if (node.largerThanPoint(p)) {
            node.left = insert(node.left, p, !isHType);
        } else {
            node.right = insert(node.right, p, !isHType);
        }

        return node;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        Node currNode = root;

        while (currNode != null) {
            if (currNode.p.equals(p)) return true;

            if (currNode.largerThanPoint(p)) {
                currNode = currNode.left;
            } else {
                currNode = currNode.right;
            }
        }

        return false;
    }

    public void draw() {
        draw(root, 0, 1, 0, 1);
    }

    private void draw(Node node, double xMin, double xMax, double yMin, double yMax) {
        if (node == null) return;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.p.draw();

        if (node.isHType) {
            double nodeY = node.p.y();

            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(xMin, nodeY, xMax, nodeY);

            draw(node.left, xMin, xMax, yMin, nodeY);
            draw(node.right, xMin, xMax, nodeY, yMax);
        } else {
            double nodeX = node.p.x();

            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(nodeX, yMin, nodeX, yMax);

            draw(node.left, xMin, nodeX, yMin, yMax);
            draw(node.right, nodeX, xMax, yMin, yMax);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();

        Bag<Point2D> bag = new Bag<>();

        addPointByRange(bag, root, rect.xmin(), rect.xmax(), rect.ymin(), rect.ymax());

        return bag;
    }

    private void addPointByRange(Bag<Point2D> bag, Node node,
                                 double xMin, double xMax, double yMin, double yMax) {
        if (node == null) return;

        double pointX = node.p.x();
        double pointY = node.p.y();

        if (node.isHType) {
            if (pointY > yMax) {
                addPointByRange(bag, node.left, xMin, xMax, yMin, yMax);
            } else if (pointY < yMin) {
                addPointByRange(bag, node.right, xMin, xMax, yMin, yMax);
            } else {
                if (pointX >= xMin && pointX <= xMax) {
                    bag.add(node.p);
                }

                addPointByRange(bag, node.left, xMin, xMax, yMin, pointY);
                addPointByRange(bag, node.right, xMin, xMax, pointY, yMax);
            }
        } else {
            if (pointX > xMax) {
                addPointByRange(bag, node.left, xMin, xMax, yMin, yMax);
            } else if (pointX < xMin) {
                addPointByRange(bag, node.right, xMin, xMax, yMin, yMax);
            } else {
                if (pointY >= yMin && pointY <= yMax) {
                    bag.add(node.p);
                }

                addPointByRange(bag, node.left, xMin, pointX, yMin, yMax);
                addPointByRange(bag, node.right, pointX, xMax, yMin, yMax);
            }
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        NearestPoint nearestPoint = new NearestPoint(null, Double.POSITIVE_INFINITY);
        return nearest(root, nearestPoint, p, 0, 1, 0, 1).p;
    }

    private NearestPoint nearest(Node node, NearestPoint nearestPoint, Point2D p, double xMin, double xMax, double yMin, double yMax) {
        if (node == null) return new NearestPoint(null, Double.POSITIVE_INFINITY);

        double currDistSq = node.p.distanceSquaredTo(p);
        if (currDistSq < nearestPoint.distSq) {
            nearestPoint = new NearestPoint(node.p, node.p.distanceSquaredTo(p));
        }

        Node sameSideNode;
        double sameSideXMin = xMin;
        double sameSideXMax = xMax;
        double sameSideYMin = yMin;
        double sameSideYMax = yMax;

        if (node.largerThanPoint(p)) {
            sameSideNode = node.left;
            if (node.isHType) {
                sameSideYMax = node.p.y();
            } else {
                sameSideXMax = node.p.x();
            }
        } else {
            sameSideNode = node.right;
            if (node.isHType) {
                sameSideYMin = node.p.y();
            } else {
                sameSideXMin = node.p.x();
            }
        }

        NearestPoint sameSideNearestPoint = nearest(sameSideNode, nearestPoint, p, sameSideXMin, sameSideXMax, sameSideYMin, sameSideYMax);

        if (sameSideNearestPoint.distSq < nearestPoint.distSq) {
            nearestPoint = sameSideNearestPoint;
        }

        Node diffSideNode;
        double diffSideXMin = xMin;
        double diffSideXMax = xMax;
        double diffSideYMin = yMin;
        double diffSideYMax = yMax;

        if (node.largerThanPoint(p)) {
            diffSideNode = node.right;
            if (node.isHType) {
                diffSideYMin = node.p.y();
            } else {
                diffSideXMin = node.p.x();
            }
        } else {
            diffSideNode = node.left;
            if (node.isHType) {
                diffSideYMax = node.p.y();
            } else {
                diffSideXMax = node.p.x();
            }
        }

        RectHV diffRect = new RectHV(diffSideXMin, diffSideYMin, diffSideXMax, diffSideYMax);

        double rectDistSq = diffRect.distanceSquaredTo(p);

        if (rectDistSq < nearestPoint.distSq) {
            NearestPoint diffSideNearestPoint = nearest(diffSideNode, nearestPoint, p, diffSideXMin, diffSideXMax, diffSideYMin, diffSideYMax);
            if (diffSideNearestPoint.distSq < nearestPoint.distSq) {
                nearestPoint = diffSideNearestPoint;
            }
        }

        return nearestPoint;
    }

    private class NearestPoint {
        Point2D p;
        double distSq;

        NearestPoint(Point2D p, double distSq) {
            this.p = p;
            this.distSq = distSq;
        }
    }

    public static void main(String[] args) {
        String filename = "testCase/circle10.txt";
        In in = new In(filename);
        PointSET bf = new PointSET();
        KdTree kdTree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            bf.insert(p);
            kdTree.insert(p);
        }

        kdTree.draw();
    }
}
