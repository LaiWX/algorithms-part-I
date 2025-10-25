import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] probability;
    private double totalprob;
    private int trials;
    private final double CONFIDENCE_95 = 1.96;
    private double mean;
    private double stddev;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        probability = new double[trials];
        this.trials = trials;
        Percolation percolation;
        for (int i = 0; i < trials; i++) {
            int[][] openList = initOpenList(n);
            int remains = n * n;
            percolation = new Percolation(n);
            for (int j = 0; j < n * n; j++) {
                int[] toOpen = randomNode(openList, remains);
                remains--;
                percolation.open(toOpen[0], toOpen[1]);
                if (percolation.percolates()) {
                    probability[i] = 1.0 * (j + 1) / (n * n);
                    totalprob += probability[i];
                    break;
                }
            }
        }
        mean = StdStats.mean(probability);
        stddev = StdStats.stddev(probability);
    }

    private int[] randomNode(int[][] openList, int remain) {
        int randomIndex = StdRandom.uniformInt(remain);
        int[] res = openList[randomIndex];
        openList[randomIndex] = openList[remain - 1];
        return res;
    }

    private int[][] initOpenList(int n) {
        int[][] list = new int[n * n][2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                list[i * n + j][0] = i + 1;
                list[i * n + j][1] = j + 1;
            }
        }
        return list;
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - CONFIDENCE_95 * stddev / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + CONFIDENCE_95 * stddev / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.printf("%-25s = %f\n", "mean", stats.mean());
        StdOut.printf("%-25s = %f\n", "stddev", stats.stddev());
        StdOut.printf("%-25s = [%f, %f]\n", "95% confidence interval", stats.confidenceLo(),
                      stats.confidenceHi());
    }
}
