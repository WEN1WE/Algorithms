import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

import java.util.TreeSet;

public class PointSET {
    TreeSet<Point2D> points;

    /** construct an empty set of points */
    public PointSET() {
        points = new TreeSet<>();
    }

    /** is the set empty? */
    public boolean isEmpty() {
        return points.isEmpty();
    }

    /** number of points in the set */
    public int size() {
        return points.size();
    }

    /** add the point to the set (if it is not already in the set) */
    public void insert(Point2D p) {
        points.add(p);
    }

    /** does the set contain point p? */
    public boolean contains(Point2D p) {
        return points.contains(p);
    }

    /** draw all points to standard draw */
    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }
    }

    /** all points that are inside the rectangle (or on the boundary) */
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> queue = new Queue<>();
        for (Point2D p : points) {
            if (rect.contains(p)) {
                queue.enqueue(p);
            }
        }
        return queue;
    }

    public Point2D nearest(Point2D p) {
        
    }
}
