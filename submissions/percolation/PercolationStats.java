import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int nTrials;
    private double[] thresholdValues;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if( n<=0 || trials <= 0) {
            throw new IllegalArgumentException("n<=0 || trials <= 0");
        }
        nTrials = trials;
        thresholdValues = new double[nTrials];
        for(int i=0; i< nTrials;i++) {
            Percolation p=new Percolation(n);
            while(!p.percolates()) {
                int r;
                int c;
                do {
                    r=StdRandom.uniformInt(1, n+1);
                    c=StdRandom.uniformInt(1, n+1);
                } while (p.isOpen(r,c));
                p.open(r,c);
            }
            thresholdValues[i] =  (double) p.numberOfOpenSites() / (n*n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholdValues);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholdValues);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(nTrials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(nTrials));
    }

   // test client (see below)
   public static void main(String[] args) {
        int n = Integer.valueOf(args[0]);
        int T = Integer.valueOf(args[1]);
        PercolationStats ps=new PercolationStats(n, T);
        System.out.println("mean                    = "+ps.mean());
        System.out.println("stddev                  = "+ps.stddev());
        System.out.println("95% confidence interval = ["+ps.confidenceLo()+", "+ps.confidenceHi()+"]");
   }
}