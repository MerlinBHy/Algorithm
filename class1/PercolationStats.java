import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by merlin on 17/1/15.
 */
public class PercolationStats {

    private double[] threshold;
    //perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n,int trials){
        if(n<=0||trials<=0)
            throw new IllegalArgumentException();
        threshold = new double[trials];
        for(int i = 0;i < trials;i++){
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()){
                int row = StdRandom.uniform(1,n+1);
                int col = StdRandom.uniform(1,n+1);
                percolation.open(row,col);
            }
            threshold[i] = (double)percolation.numberOfOpenSites()/(n*n);
        }
    }
    // sample mean of percolation threshold 平均数
    public double mean()  {
        return StdStats.mean(threshold);
    }
    // sample standard deviation of percolation threshold 标准差
    public double stddev(){
        return StdStats.stddev(threshold);
    }
    // low  endpoint of 95% confidence interval 最低置信度
    public double confidenceLo()  {
        return mean() - 1.96*stddev()/Math.sqrt(threshold.length);
    }
    // high endpoint of 95% confidence interval 最高置信度
    public double confidenceHi() {
        return mean() + 1.96*stddev()/Math.sqrt(threshold.length);
    }
    // test client (described below)
    public static void main(String[] args)  {
        PercolationStats percolationStats = new PercolationStats(20,100);

        System.out.println("mean="+ percolationStats.mean());
        System.out.println("stddev="+ percolationStats.stddev());
        System.out.println("95%% confidence Interval="+percolationStats.confidenceLo()+"  "+ percolationStats.confidenceHi());
    }
}

