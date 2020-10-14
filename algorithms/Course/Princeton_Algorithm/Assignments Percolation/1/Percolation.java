/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF quF;
    private final boolean[][] sites;
    private final int size;
    private int openSitesCount;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1)
            throw new IllegalArgumentException("n should be more than or equal to 1");
        sites = new boolean[n][n];
        size = n;
        openSitesCount = 0;
        quF = new WeightedQuickUnionUF((n * n) + 2);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(6);
        p.open(1, 6);
        System.out.println(p.isOpen(1, 6));
        System.out.println(p.numberOfOpenSites());
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkIndices(row, col);
        if (sites[row - 1][col - 1])
            return;
        sites[row - 1][col - 1] = true;
        System.out.printf("Current row, col :: %d, %d", row,col);
        openSitesCount++;
        int currentPosition = xyTo1D(row, col);
        if (isFirstRow(row)) {
            union(currentPosition, 0);
        }
        if (isLastRow(row)) {
            union(currentPosition, getLastPosition());
        }
        if (ifTopPositionConnected(row, col)) {
            int fromPosition = xyTo1D(row - 1, col);
            union(currentPosition, fromPosition);
        }
        if (ifBottomPositionConnected(row, col)) {
            int fromPosition = xyTo1D(row + 1, col);
            union(currentPosition, fromPosition);
        }
        if (ifLeftConnected(row, col)) {
            int fromPosition = xyTo1D(row, col - 1);
            union(currentPosition, fromPosition);
        }
        if (ifRightConnected(row, col)) {
            int fromPosition = xyTo1D(row, col + 1);
            union(currentPosition, fromPosition);
        }
    }

    private boolean ifRightConnected(int row, int col) {
        return col < size && isOpen(row, col + 1);
    }

    private boolean ifLeftConnected(int row, int col) {
        return col > 1 && isOpen(row, col - 1);
    }

    private boolean ifTopPositionConnected(int row, int col) {
        return (row > 1) && isOpen(row - 1, col);
    }

    private boolean ifBottomPositionConnected(int row, int col) {
        return (row < size) && isOpen(row + 1, col);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkIndices(row, col);
        return sites[row - 1][col - 1];
    }

    private boolean isFirstRow(int row) {
        return row == 1;
    }

    private boolean isLastRow(int row) {
        return row == size;
    }

    // does the system percolate?
    public boolean percolates() {
        return quF.find(0) == quF.find(getLastPosition());
    }

    private int getLastPosition() {
        return size * size + 1;
    }

    private void union(int fromPosition, int toPosition) {
        if (fromPosition > -1 && fromPosition < getLastPosition() + 1 && toPosition > -1
                && toPosition < getLastPosition() + 1) {
            quF.union(fromPosition, toPosition);
            System.out.printf(", Linear positions from, col :: %d, %d\n", fromPosition, toPosition);
        }
        else {
            System.out.printf("\t\tIllegal output\n");
            throw new IllegalArgumentException(
                    "Position we are trying to set is illegal - " + fromPosition + ", "
                            + toPosition);

        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkIndices(row, col);
        return quF.find(0) == quF.find(xyTo1D(row, col));
    }

    private void checkIndices(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size)
            throw new IllegalArgumentException("row/column index i out of bounds");
    }

    private int xyTo1D(int row, int col) {
        return size * (row - 1) + col;
    }

}
