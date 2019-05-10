import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> lineSegments = new ArrayList<>();
    private final int n;

    public FastCollinearPoints(Point[] points) {
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

        for (Point point : points) {

            Arrays.sort(sortedPoints);
            Arrays.sort(sortedPoints, point.slopeOrder());

            int start = 1;
            int end = 2;
            double startSlope;
            double endSlope;

            while (end < n) {
                startSlope = point.slopeTo(sortedPoints[start]);
                endSlope = point.slopeTo(sortedPoints[end]);

                if (Double.compare(startSlope, endSlope) == 0) {
                    end += 1;

                    // if last points are Collinear, can't contain them;
                    if (end != n) {
                        continue;
                    }
                }

                // the origin point must be the start of the line.
                if (end - start > 2  && sortedPoints[0].compareTo(sortedPoints[start]) < 0) {
                    lineSegments.add(new LineSegment(point, sortedPoints[end - 1]));
                }

                start = end;
                end += 1;

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




