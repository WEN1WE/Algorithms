import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Queue;


public class KdTree {
    private static final boolean RED = true;

    private Node root;
    private int size;

    /**
     * construct an empty set of points
     */
    public KdTree() {
        root = null;
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
    }

    /**
     * is the set empty?
     */
    public boolean isEmpty() {
        return root == null;
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
        if (root == null) {
            root = new Node(p, RED, new Node(new Point2D(1, 1), !RED, null));
            size += 1;
            return;
        }

        boolean color = RED;
        Node current = root;
        double diff;

        while (true) {
            diff = (color == RED) ? (p.x() - current.point.x()) : (p.y() - current.point.y());

            if (diff < 0) {
                if (current.left == null) {
                    current.left = new Node(p, !color, current);
                    size += 1;
                    break;
                }
                current = current.left;
            } else {
                // don't insert the equal point.
                if (current.point.equals(p)) {
                    return;
                }

                if (current.right == null) {
                    current.right = new Node(p, !color, current);
                    size += 1;
                    break;
                }
                current = current.right;
            }
            color = !color;
        }
    }

    /**
     * does the set contain point p?
     */
    public boolean contains(Point2D p) {
        isLegal(p);
        return contains(root, p);
    }

    private boolean contains(Node x, Point2D p) {
        if (x == null) {
            return false;
        }

        if (x.point.equals(p)) {
            return true;
        }

        double diff = (x.color == RED) ? (p.x() - x.point.x()) : (p.y() - x.point.y());

        if (diff < 0) {
            return contains(x.left, p);
        } else {
            return contains(x.right, p);
        }
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(-0.5, 1.5);
        StdDraw.setYscale(-0.5, 1.5);
        StdDraw.line(0, 0, 0, 1);
        StdDraw.line(0, 0, 1, 0);
        StdDraw.line(0, 1, 1, 1);
        StdDraw.line(1, 0, 1, 1);

        Queue<Node> q = new Queue<>();
        inorder(root, q);

        for (Node node : q) {
            double x = node.point.x();
            double y = node.point.y();
            double xP = node.predecessor.point.x();
            double yP = node.predecessor.point.y();

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

    private void inorder(Node x, Queue<Node> q) {
        if (x == null) {
            return;
        }
        inorder(x.left, q);
        q.enqueue(x);
        inorder(x.right, q);
    }

    /**
     * all points that are inside the rectangle (or on the boundary)
     */
    public Iterable<Point2D> range(RectHV rect) {
        isLegal(rect);
        Queue<Point2D> q = new Queue<>();
        range(root, rect, q);
        return q;
    }

    private void range(Node node, RectHV rect, Queue<Point2D> q) {
        if (node == null) {
            return;
        }

        if (rect.contains(node.point)) {
            q.enqueue(node.point);
        }

        if (node.color == RED) {
            if (rect.xmax() < node.point.x()) {
                range(node.left, rect, q);
            } else if (rect.xmin() >= node.point.x()){
                range(node.right, rect, q);
            } else {
                range(node.left, rect, q);
                range(node.right, rect, q);
            }
        } else {
            if (rect.ymax() < node.point.y()) {
                range(node.left, rect, q);
            } else if (rect.xmin() >= node.point.y()) {
                range(node.right, rect, q);
            } else {
                range(node.left, rect, q);
                range(node.right, rect, q);
            }
        }
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        isLegal(p);
        return nearest(root, p, root.point, 0);

    }

    private Point2D nearest(Node node, Point2D p, Point2D minPoint, double minGuess) {
        if (node == null) {
            return null;
        }

        double minDist = p.distanceTo(minPoint);
        if (p.distanceTo(node.point) < minDist) {
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

                nearest(node.right, p, minPoint, Math.abs(p.x() - node.point.x()));
            } else {
                Point2D temp = nearest(node.right, p, minPoint, 0);
                if (temp != null) {
                    minPoint = temp;
                }

                nearest(node.left, p, minPoint, Math.abs(node.point.x() - p.x()));
            }
        } else {
            if (p.y() < node.point.y()) {
                Point2D temp = nearest(node.left, p, minPoint, 0);
                if (temp != null) {
                    minPoint = temp;
                }

                nearest(node.right, p, minPoint, Math.abs(p.x() - node.point.x()));
            } else {
                Point2D temp = nearest(node.right, p, minPoint, 0);
                if (temp != null) {
                    minPoint = temp;
                }

                nearest(node.left, p, minPoint, Math.abs(node.point.x() - p.x()));
            }
        }

        return minPoint;
    }

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
        //t.insert(c);
        t.insert(d);

        t.insert(e);

        RectHV rect = new RectHV(0.5, 0.2, 0.7, 0.4);
        for (Point2D p : t.range(rect)) {
            System.out.println(p);
        }
        System.out.println(t.contains(f));
        t.draw();


        System.out.println(t.nearest(c));
    }


}

