import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Queue;


public class KdTree {
    private static final boolean RED = true;

    private Node root;

    /**
     * construct an empty set of points
     */
    public KdTree() {
        root = null;
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
        return 1;
    }

    /**
     * add the point to the set (if it is not already in the set)
     */
    public void insert(Point2D p) {
        isLegal(p);
        if (root == null) {
            root = new Node(p, RED, new Node(new Point2D(1, 1), !RED, null));
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
                    break;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new Node(p, !color, current);
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
        return true;
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(-0.5, 1.5);
        StdDraw.setYscale(-0.5, 1.5);
        StdDraw.line(0,0, 0, 1);
        StdDraw.line(0, 0, 1, 0);
        StdDraw.line(0,1, 1, 1);
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
        return null;
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        isLegal(p);
        return null;
    }

    private void isLegal(Object object) {
        if (object == null) {
            throw new java.lang.IllegalArgumentException();
        }
    }

    public static void main(String[] args) {
        Point2D a = new Point2D(0.7, 0.2);
        Point2D b = new Point2D( 0.5, 0.4);
        Point2D c = new Point2D(0.2, 0.3);
        Point2D d = new Point2D(0.4, 0.7);
        Point2D e = new Point2D(0.9, 0.6);
        KdTree t = new KdTree();
        t.insert(a);
        t.insert(b);
        t.insert(c);
        t.insert(d);
        t.insert(e);
        t.draw();

    }
}

