/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int n;
    private int[] results;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException(
                    "both n and trials must be positive");
        }

        this.n = n;
        results = new int[trials];
        for (int i = 0; i < trials; i++) {
            results[i] = runTrial();
        }
    }

    private int runTrial() {
        Percolation p = new Percolation(n);
        while (!p.percolates()) {
            int row = StdRandom.uniformInt(n) + 1;
            int col = StdRandom.uniformInt(n) + 1;
            p.open(row, col);
        }
        return p.numberOfOpenSites();
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results) / (n * n);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results) / (n * n);
    }

    private double confidenceRadius() {
        return 1.96 * stddev() / Math.sqrt(results.length);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - confidenceRadius();
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + confidenceRadius();
    }

    // test client (see below)
    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);

        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = ["
                                   + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }

}