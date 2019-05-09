import edu.princeton.cs.algs4.StdDraw;

public class TestStdDraw {
    public static void main(String[] args) {
        //StdDraw.setPenRadius(0.05);
        //StdDraw.setPenColor(StdDraw.BLUE);
        //StdDraw.point(0.5, 0.5);
        //StdDraw.setPenColor(StdDraw.MAGENTA);
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.line(0.2, 0.2, 0.8, 0.2);

        StdDraw.line(10000, 10000, 20000, 20000);
        StdDraw.line(14000, 10000, 32000, 10000);
        StdDraw.show();
    }
}
