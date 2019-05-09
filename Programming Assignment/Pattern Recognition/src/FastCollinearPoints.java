import java.util.Arrays;

public class FastCollinearPoints {
    private int count = 0;
    private LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException();
        }

        int N = points.length;
        int countVisited = 0;

        Point[] tPoints = new Point[N];
        System.arraycopy(points, 0, tPoints, 0, N);
        count = 0;
        lineSegments = new LineSegment[N];

        for (Point point : points) {

            Arrays.sort(tPoints, point.slopeOrder());

            for (int i = 1; i < N - 2;) {
                double firstSlope = tPoints[0].slopeTo(tPoints[i]);
                for (int j = 1; i + j < N; j++) {
                    double nextSlope = tPoints[0].slopeTo(tPoints[i + j]);
                    if (nextSlope != firstSlope || i + j == N - 1) {
                        if (i + j == N - 1 && nextSlope == firstSlope) {
                            j += 1;
                        }

                        if (j > 2) {
                            Point[] collinear = new Point[j + 1];
                            collinear[0] = tPoints[0];
                            System.arraycopy(tPoints, i, collinear, 1, j);
                            countVisited += j;
                            Arrays.sort(collinear);
                            lineSegments[count++] = new LineSegment(collinear[0], collinear[j]);
                        }
                        i += j;
                        break;
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return count;
    }

    public LineSegment[] segments() {
        LineSegment[] t = new LineSegment[count];
        System.arraycopy(lineSegments, 0, t, 0, count);
        return t;
    }
}
