import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final ArrayList<LineSegment> lineSegments = new ArrayList<>();
    private final int n;

    public BruteCollinearPoints(Point[] points) {
        if (points == null || hasNull(points)) {
            throw new java.lang.IllegalArgumentException();
        }

        n = points.length;
        Point[] sortedPoints = points.clone();
        // time : nlogn

        Arrays.sort(sortedPoints);
        if (hasDuplicate(sortedPoints)) {
            throw new java.lang.IllegalArgumentException();
        }

        for (int i0 = 0; i0 < n; i0++) {
            for (int i1 = i0 + 1; i1 < n; i1++) {
                for (int i2 = i1 + 1; i2 < n; i2++) {
                    for (int i3 = i2 + 1; i3 < n; i3++) {
                        double s0 = sortedPoints[i0].slopeTo(sortedPoints[i1]);
                        double s1 = sortedPoints[i0].slopeTo(sortedPoints[i2]);
                        double s2 = sortedPoints[i0].slopeTo(sortedPoints[i3]);
                        if (Double.compare(s0, s1) == 0 && Double.compare(s1, s2) == 0) {
                            lineSegments.add(new LineSegment(sortedPoints[i0], sortedPoints[i3]));
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[0]);
    }

    private boolean hasNull(Point[] points) {
        for (Point p : points) {
            if (p == null) {
                return true;
            }
        }
        return false;
    }

    private boolean hasDuplicate(Point[] points) {
        for (int i = 0; i < n - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                return true;
            }
        }
        return false;
    }
}
