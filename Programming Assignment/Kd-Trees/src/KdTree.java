import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Queue;

public class KdTree {
    private static final boolean RED = true;
    private static final double MINSCARE = -0.5;
    private static final double MAXSCARE = 1.5;

    private Node sentinel;
    private int size;

    /**
     * construct an empty set of points
     */
    public KdTree() {
        sentinel = new Node(new Point2D(1, 1), !RED, null);
        size = 0;
    }

    private class Node {
        Point2D point;
        Node left;
        Node right;
        boolean color;
        Node predecessor;

        private Node(Point2D point, boolean color, Node predecessor) {
            this.point = point;
            this.color = color;
            this.predecessor = predecessor;
        }

        private double getX() {
            return point.x();
        }

        private double getY() {
            return point.y();
        }

        private double compareX(Point2D p) {
            return p.x() - point.x();
        }

        private double compareY(Point2D p) {
            return p.y() - point.y();
        }

        private boolean isRed() {
            return color == RED;
        }
    }

    /**
     * is the set empty?
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * number of points in the set
     */
    public int size() {
        return size;
    }

    /**
     * add the point to the set (if it is not already in the set)
     */
    public void insert(Point2D p) {
        isLegal(p);
        size += 1;
        sentinel.left = insert(sentinel.left, p, sentinel);
    }

    private Node insert(Node node, Point2D p, Node predecessor) {
        if (node == null) {
            return new Node(p, !predecessor.color, predecessor);
        }
        if (node.point.equals(p)) {
            return node;
        }

        double cmp = node.isRed() ? node.compareX(p) : node.compareY(p);
        if (cmp < 0) {
            node.left = insert(node.left, p, node);
        } else {
            node.right = insert(node.right, p, node);
        }
        return node;
    }
    /**
     * does the set contain point p?
     */

    public boolean contains(Point2D p) {
        isLegal(p);
        return contains(sentinel, p);
    }

    private boolean contains(Node node, Point2D p) {
        if (node == null) {
            return false;
        }
        if (node.point.equals(p)) {
            return true;
        }

        double cmp = node.isRed() ? node.compareX(p) : node.compareY(p);

        if (cmp < 0) {
            return contains(node.left, p);
        } else {
            return contains(node.right, p);
        }
    }


    /**
     * draw all points to standard draw
     */
    public void draw() {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(MINSCARE, MAXSCARE);
        StdDraw.setYscale(MINSCARE, MAXSCARE);
        StdDraw.rectangle(0.5, 0.5, 0.5, 0.5);

        Queue<Node> q = new Queue<>();
        inorder(sentinel.left, q);

        for (Node node : q) {
            double x = node.getX();
            double y = node.getY();
            double xP = node.predecessor.getX();
            double yP = node.predecessor.getY();

            StdDraw.setPenRadius(0.001);

            if (node.color == RED) {
                StdDraw.setPenColor(StdDraw.RED);
                double y0 = (yP > y) ? 0 : 1;
                StdDraw.line(x, y0, x, yP);
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                double x0 = (xP > x) ? 0 : 1;
                StdDraw.line(x0, y, xP, y);
            }

            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            StdDraw.point(x, y);
        }
        StdDraw.show();
    }

    private void inorder(Node node, Queue<Node> q) {
        if (node == null) {
            return;
        }
        inorder(node.left, q);
        q.enqueue(node);
        inorder(node.right, q);
    }

    /**
     * all points that are inside the rectangle (or on the boundary)
     */

    public Iterable<Point2D> range(RectHV rect) {
        isLegal(rect);
        Queue<Point2D> q = new Queue<>();
        RectHV maxRect = new RectHV(0, 0, 1,1);
        range(sentinel, rect, q, maxRect);
        return q;
    }

    private void range(Node node, RectHV rect, Queue<Point2D> q, RectHV maxRect) {
        if (node == null) {
            return;
        }
        if (!rect.intersects(maxRect)) {
            return;
        }
        if (rect.contains(node.point)) {
            q.enqueue(node.point);
        }

        double xmin = rect.xmin();
        double xmax = rect.xmax();
        double ymin = rect.ymin();
        double ymax = rect.ymax();

        if (node.color == RED) {
            range(node.left, rect, q, new RectHV(xmin, ymin, node.getX(), ymax));
            range(node.right, rect, q, new RectHV(node.getX(), ymin, xmax, ymax));
        } else {
            range(node.left, rect, q, new RectHV(xmin, ymin, xmax, node.getY()));
            range(node.right, rect, q, new RectHV(xmin, node.getY(), xmax, ymax));
        }
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     */

    /*
    public Point2D nearest(Point2D p) {
        isLegal(p);
        return nearest(root, p, root.point, 0);

    }

    private Point2D nearest(Node node, Point2D p, Point2D minPoint, double minGuess) {
        if (node == null) {
            return null;
        }

        double minDist = p.distanceSquaredTo(minPoint);
        if (p.distanceSquaredTo(node.point) < minDist) {
            minPoint = node.point;
        }

        if (minGuess >= minDist) {
            return null;
        }

        if (node.color == RED) {
            if (p.x() < node.point.x()) {
                Point2D temp = nearest(node.left, p, minPoint, 0);
                if (temp != null) {
                    minPoint = temp;
                }

                Point2D temp2 = nearest(node.right, p, minPoint, Math.abs(p.x() - node.point.x()));
                if (temp2 != null && p.distanceSquaredTo(minPoint) > p.distanceSquaredTo(temp2)) {
                    minPoint = temp2;
                }
            } else {
                Point2D temp = nearest(node.right, p, minPoint, 0);
                if (temp != null) {
                    minPoint = temp;
                }

                Point2D temp2 = nearest(node.left, p, minPoint, Math.abs(node.point.x() - p.x()));
                if (temp2 != null && p.distanceSquaredTo(minPoint) > p.distanceSquaredTo(temp2)) {
                    minPoint = temp2;
                }
            }
        } else {
            if (p.y() < node.point.y()) {
                Point2D temp = nearest(node.left, p, minPoint, 0);
                if (temp != null) {
                    minPoint = temp;
                }

                Point2D temp2 = nearest(node.right, p, minPoint, Math.abs(p.x() - node.point.x()));
                if (temp2 != null && p.distanceSquaredTo(minPoint) > p.distanceSquaredTo(temp2)) {
                    minPoint = temp2;
                }

            } else {
                Point2D temp = nearest(node.right, p, minPoint, 0);
                if (temp != null) {
                    minPoint = temp;
                }

                Point2D temp2 = nearest(node.left, p, minPoint, Math.abs(node.point.x() - p.x()));
                if (temp2 != null && p.distanceSquaredTo(minPoint) > p.distanceSquaredTo(temp2)) {
                    minPoint = temp2;
                }
            }
        }

        return minPoint;
    }
    */

    private void isLegal(Object object) {
        if (object == null) {
            throw new java.lang.IllegalArgumentException();
        }
    }

    public static void main(String[] args) {
        Point2D a = new Point2D(0.7, 0.2);
        Point2D b = new Point2D(0.5, 0.4);
        Point2D c = new Point2D(0.2, 0.3);
        Point2D d = new Point2D(0.4, 0.7);
        Point2D e = new Point2D(0.9, 0.6);

        Point2D f = new Point2D(0.2, 0.1);

        KdTree t = new KdTree();
        t.insert(a);
        t.insert(b);
        t.insert(c);
        t.insert(d);
        t.insert(e);
        t.draw();
        System.out.println(t.contains(f));




        /*
        RectHV rect = new RectHV(0.5, 0.2, 0.7, 0.4);
        for (Point2D p : t.range(rect)) {
            System.out.println(p);
        }
        System.out.println(t.contains(f));
        t.draw();
        System.out.println(t.nearest(c)); */
    }


}

