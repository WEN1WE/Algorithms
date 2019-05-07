import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private int openSites;
    private final int virtualTop;
    private final int virtualBottom;
    private boolean[] grids;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF uf1;

    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException("n must be bigger than 0");
        }

        this.n = n;
        this.openSites = 0;
        this.virtualTop = n * n;
        this.virtualBottom = n * n + 1;
        grids = new boolean[n * n + 2];
        grids[n * n] = true;
        grids[n * n + 1] = true;

        uf = new WeightedQuickUnionUF(n * n + 2);
        uf1 = new WeightedQuickUnionUF(n * n + 1);
    }

    public void open(int row, int col) {
        validRowAndCol(row, col);

        int index = getIndex(row, col);
        if (!grids[index]) {
            grids[index] = true;
            this.openSites += 1;
        }

        connected(index, row, col - 1);
        connected(index, row, col + 1);
        connected(index, row - 1, col);
        connected(index, row + 1, col);

        if (isTop(row, col)) {
            uf.union(virtualTop, index);
            uf1.union(virtualTop, index);
        }

        if (isBottom(row, col)) {
            uf.union(virtualBottom, index);
        }

    }

    public boolean isOpen(int row, int col) {
        validRowAndCol(row, col);
        return grids[getIndex(row, col)];
    }

    public boolean isFull(int row, int col) {
        validRowAndCol(row, col);
        return uf1.connected(getIndex(row, col), virtualTop);
    }

    public int numberOfOpenSites() {
        return this.openSites;
    }

    public boolean percolates() {
        return uf.connected(virtualTop, virtualBottom);
    }

    private boolean isLegal(int row, int col) {
        return row >= 1 && row <= this.n && col >= 1 && col <= this.n;
    }

    private int getIndex(int row, int col) {
        return this.n * (row - 1) + col - 1;
    }

    private void connected(int index, int row, int col) {
        if (isLegal(row, col) && isOpen(row, col)) {
            uf1.union(index, getIndex(row, col));
            uf.union(index, getIndex(row, col));
        }
    }

    private boolean isTop(int row, int col) {
        return isLegal(row, col) && row == 1;
    }

    private boolean isBottom(int row, int col) {
        return isLegal(row, col) && row == this.n;
    }

    private void validRowAndCol(int row, int col) {
        if (!isLegal(row, col)) {
            throw new java.lang.IllegalArgumentException("invalid range of row or col");
        }
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        p.open(1, 3);
        p.open(2, 3);
        p.open(3, 3);
        p.open(3, 1);
    }
}
