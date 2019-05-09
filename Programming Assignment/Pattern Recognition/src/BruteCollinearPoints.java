import java.util.Arrays;

public class BruteCollinearPoints {
    private int count;
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException();
        }

        int N = points.length;
        count = 0;
        lineSegments = new LineSegment[N / 4 + 1];

        for (int i0 = 0; i0 < N; i0++) {
            if (points[i0] == null) {
                throw new java.lang.IllegalArgumentException();
            }
            for (int i1 = i0 + 1; i1 < N; i1++) {
                if (points[i0] == points[i1]) {
                    throw new java.lang.IllegalArgumentException();
                }
                for (int i2 = i1 + 1; i2 < N; i2++) {
                    for (int i3 = i2 + 1; i3 < N; i3++) {
                        Point[] t = new Point[4];
                        t[0] = points[i0];
                        t[1] = points[i1];
                        t[2] = points[i2];
                        t[3] = points[i3];
                        Arrays.sort(t);
                        if (isCollinear(t)) {
                            lineSegments[count++] = new LineSegment(t[0], t[3]);
                        }
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

    private boolean isCollinear(Point[] p) {
        double s0 = p[0].slopeTo(p[1]);
        double s1 = p[0].slopeTo(p[2]);
        double s2 = p[0].slopeTo(p[3]);

        return s0 == s1 && s0 == s2;
    }
}
