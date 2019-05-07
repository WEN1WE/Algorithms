import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double PARAMETER = 1.96;
    private final int trials;
    private final double mean;
    private final double s;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException("arguments must be positive");
        }
        this.trials = trials;
        double[] results = getResults(n, trials);
        mean = StdStats.mean(results);
        s = StdStats.stddev(results);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return s;
    }

    public double confidenceLo() {
        return mean - PARAMETER * s / Math.sqrt(this.trials);
    }

    public double confidenceHi() {
        return mean + PARAMETER * s / Math.sqrt(this.trials);
    }

    private static double computationExperiment(int n) {
        Percolation p = new Percolation(n);
        int row;
        int col;

        while (!p.percolates()) {
            row = StdRandom.uniform(1, n + 1);
            col = StdRandom.uniform(1, n + 1);
            p.open(row, col);
        }

        return p.numberOfOpenSites() / (double) (n * n);
    }

    private static double[] getResults(int n, int trials) {
        double[] results = new double[trials];
        for (int i = 0; i < trials; i++) {
            results[i] = computationExperiment(n);
        }
        return results;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats s = new PercolationStats(n, trials);
        System.out.println("mean                    = " + s.mean());
        System.out.println("stddev                  = " + s.stddev());
        System.out.println("95% confidence interval = [" + s.confidenceLo() + ", " + s.confidenceHi() + "]");

    }
}
