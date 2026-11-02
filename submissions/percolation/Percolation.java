import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf = null;
    private int dim;
    private int openSites;
    private int virtualStart;
    private int virtualEnd;
    private int[] sitesStatus;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if(n <= 0) {
            throw new IllegalArgumentException("n <= 0");
        }
        dim =n;
        int size = n * n;
        int sizeIncludingVirtualSlots = size +2;
        sitesStatus = new int[size];        
        uf = new WeightedQuickUnionUF(sizeIncludingVirtualSlots);
        openSites=0;
        virtualStart = sizeIncludingVirtualSlots -2;
        virtualEnd = sizeIncludingVirtualSlots -1;
    }

    private int getIndex(int row, int col) {
        return (dim*(row-1))+(col-1);
    }
    
    private void unionAdjSiteIfOpen(int sitePos, int adjSiteRow, int adjSiteCol) {
        if(isOpen(adjSiteRow, adjSiteCol)) {
            uf.union(sitePos, getIndex(adjSiteRow, adjSiteCol));
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if(row < 0 || row > dim || col < 0 || col > dim) {
            throw new IllegalArgumentException ("row < 0 || row > dim || col < 0 || col > dim");
        }
        int pos = getIndex(row, col);
        if(sitesStatus[pos] ==1) {
            // site already open
            return;
        }
        sitesStatus[pos]=1;
        openSites++;
        if(col == 1) {
            unionAdjSiteIfOpen(pos, row, col+1);
        }
        else if(col == dim) {
            unionAdjSiteIfOpen(pos, row, col-1);
        }
        else {
            unionAdjSiteIfOpen(pos, row, col+1);
            unionAdjSiteIfOpen(pos, row, col-1);
        }
        if(row ==1) {
            // connect with virtualStart
            uf.union(pos, virtualStart);
            unionAdjSiteIfOpen(pos, row+1, col);
        }
        else if(row ==dim) {
            // connect with virtualEnd
            uf.union(pos, virtualEnd);
            unionAdjSiteIfOpen(pos, row-1, col);
        }
        else {
            unionAdjSiteIfOpen(pos, row+1, col);
            unionAdjSiteIfOpen(pos, row-1, col);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if(row < 0 || row > dim || col < 0 || col > dim) {
            throw new IllegalArgumentException ("row < 0 || row > dim || col < 0 || col > dim");
        }
        return sitesStatus[getIndex(row, col)] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        if(row < 0 || row > dim || col < 0 || col > dim) {
            throw new IllegalArgumentException ("row < 0 || row > dim || col < 0 || col > dim");
        }
        return uf.find(virtualStart) == uf.find(getIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(virtualStart) == uf.find(virtualEnd);
    }

    // test client (optional)
    public static void main(String[] args) {
        int n=4;
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
        System.out.println(p.numberOfOpenSites());
        double t= (double) p.numberOfOpenSites() / (n*n);
        System.out.println("Threshold: "+t);
    }
}
