import edu.princeton.cs.algs4.Stack;

import java.util.Arrays;

public class Point2D {
    private final double x;
    private final double y;

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static int ccw(Point2D a, Point2D b, Point2D c) {
        double area2 = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
        if (area2 < 0) {
            return -1; // clockwise
        } else if (area2 > 0) {
            return 1;  // counter-clockwise
        } else {
            return 0;  // collinear
        }
    }

    public static void main(String[] args) {
        Stack<Point2D> hull = new Stack<>();

        Arrays.sort(p, Point2D.Y_ORDER);  // p[0] is now point with lowest y-coordinate
        Arrays.sort(p, p[0].BY_POLAR_ORDER);  // sort by polar angle with respect to p[0]

        hull.push(p[0]);
        hull.push(p[1]);

        for (int i = 2; i < N; i++) {
            Point2D top = hull.pop();
            while (Point2D.ccw(hull.peek(), top, p[i]) <= 0) {
                top = hull.pop();
            }
            hull.push(top);
            hull.push(p[i]);  // add p[i] to putative hull
        }
    }
}
