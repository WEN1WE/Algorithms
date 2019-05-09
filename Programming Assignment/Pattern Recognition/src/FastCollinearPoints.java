import java.util.Arrays;

public class FastCollinearPoints {
    private int count = 0;
    private LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException();
        }

        int N = points.length;

        Point[] tPoints = new Point[N];
        System.arraycopy(points, 0, tPoints, 0, N);
        count = 0;
        lineSegments = new LineSegment[N];
        Point[] start = new Point[N];
        Point[] end = new Point[N];
        int sign = 0;

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
                            Arrays.sort(collinear);

                            for (int k = 0; k < count; k++) {
                                if (start[k] == collinear[0] && end[k] == collinear[j]) {
                                    sign = 1;
                                    break;
                                }
                            }

                            if (sign == 0) {
                                start[count] = collinear[0];
                                end[count] = collinear[j];
                                lineSegments[count++] = new LineSegment(collinear[0], collinear[j]);
                            } else {
                                sign = 0;
                            }


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
