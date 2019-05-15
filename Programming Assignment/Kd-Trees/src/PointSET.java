import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

import java.util.TreeSet;

public class PointSET {
    private final TreeSet<Point2D> points;

    /**
     * construct an empty set of points
     */
    public PointSET() {
        points = new TreeSet<>();
    }

    /**
     * is the set empty?
     */
    public boolean isEmpty() {
        return points.isEmpty();
    }

    /**
     * number of points in the set
     */
    public int size() {
        return points.size();
    }

    /**
     * add the point to the set (if it is not already in the set)
     */
    public void insert(Point2D p) {
        isLegal(p);
        points.add(p);
    }

    /**
     * does the set contain point p?
     */
    public boolean contains(Point2D p) {
        isLegal(p);
        return points.contains(p);
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }
    }

    /**
     * all points that are inside the rectangle (or on the boundary)
     */
    public Iterable<Point2D> range(RectHV rect) {
        isLegal(rect);
        Queue<Point2D> queue = new Queue<>();
        for (Point2D p : points) {
            if (rect.contains(p)) {
                queue.enqueue(p);
            }
        }
        return queue;
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        isLegal(p);
        Point2D nearestP = null;
        double minDist = p.distanceTo(points.first());
        for (Point2D e : points) {
            double dist = p.distanceSquaredTo(e);
            if (Double.compare(minDist, dist) > 0) {
                minDist = dist;
                nearestP = e;
            }
        }
        return nearestP;
    }

    private void isLegal(Object object) {
        if (object == null) {
            throw new java.lang.IllegalArgumentException();
        }
    }
}
