import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int opensites;
    private int num;
    private boolean[] status;
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private WeightedQuickUnionUF tempWeightedQuickUnionUF;
    // create n-by-n grid, with all sites blocked
    public Percolation(int n){
        if (n <= 0)
            throw new IllegalArgumentException("n can't be less than 1");
        num = n;
        opensites = 0;
        status = new boolean[n*n+2];
        for (int i = 0; i <= n*n; i ++) {
            status[i] = false;
        }
        weightedQuickUnionUF = new WeightedQuickUnionUF(n*n+2);
        tempWeightedQuickUnionUF = new WeightedQuickUnionUF(n*n+1);
        status[0] = true;
        status[n*n+1] = true;
    }
    // open site (row, col) if it is not open already
    public void open(int row,int col){
        if (!validate(row,col))
            throw new IndexOutOfBoundsException();
        if (status[(row-1) * num + col])
            return;
        status[(row-1) * num + col] = true;
        opensites++;
        if (row == 1) {
            weightedQuickUnionUF.union(col, 0);
            tempWeightedQuickUnionUF.union(col, 0);
        }
        if (row > 1 && status[(row-2) * num + col]) {
            weightedQuickUnionUF.union((row - 1) * num + col, (row - 2) * num + col);
            tempWeightedQuickUnionUF.union((row - 1) * num + col, (row - 2) * num + col);
        }
        if (row < num && status[row * num + col]) {
            weightedQuickUnionUF.union((row - 1) * num + col, row * num + col);
            tempWeightedQuickUnionUF.union((row - 1) * num + col, row * num + col);
        }
        if (col > 1 && status[(row-1) * num + col -1]) {
            weightedQuickUnionUF.union((row - 1) * num + col, (row - 1) * num + col - 1);
            tempWeightedQuickUnionUF.union((row - 1) * num + col, (row - 1) * num + col - 1);
        }
        if (col < num && status[(row-1) * num + col +1]) {
            weightedQuickUnionUF.union((row - 1) * num + col, (row - 1) * num + col + 1);
            tempWeightedQuickUnionUF.union((row - 1) * num + col, (row - 1) * num + col + 1);
        }
        if(row == num)
            weightedQuickUnionUF.union((row - 1) * num + col, num*num+1);
    }
    // is site (row, col) open?
    public boolean isOpen(int row,int col){
        if (!validate(row,col))
            throw new IndexOutOfBoundsException();
        return status[(row-1) * num + col];
    }
    // is site (row, col) full?
    public boolean isFull(int row,int col){
        if (!validate(row,col))
            throw new IndexOutOfBoundsException("row"+row+",col"+col+"is out of bound");
        if (row == 1 && isOpen(row,col))
            return true;
        if (tempWeightedQuickUnionUF.connected(0, (row-1) * num + col) && isOpen(row,col))
            return true;
        return false;
    }

    private boolean validate(int row,int col){
        return row > 0 && row <= num && col > 0 && col <= num;
    }
    //num of open sites
    public int numberOfOpenSites(){
        return opensites;
    }
    //does the system percolate?
    public boolean percolates(){
        return weightedQuickUnionUF.connected(0, num*num+1);
    }

    public static void main(String[] args){
        int n = StdIn.readInt();
        Percolation percolation = new Percolation(n);
        while (!percolation.percolates()) {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            percolation.open(row,col);
            StdOut.println(percolation.numberOfOpenSites());
        }
        StdOut.println(percolation.numberOfOpenSites() / (n*n));
    }
}
