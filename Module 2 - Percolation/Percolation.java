import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF percolationUF;
    private WeightedQuickUnionUF isFullUF;
    private int gridSize;
    private boolean[][] opened;
    private int openedNumber = 0;
    private int virtualTop;
    private int virtualBottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        percolationUF = new WeightedQuickUnionUF(n * n + 2);
        isFullUF = new WeightedQuickUnionUF(n * n + 2);
        opened = new boolean[n + 1][n + 1];
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                opened[i][j] = false;
            }
        }
        gridSize = n;
        virtualTop = calculateIndex(1, 1) - 1;
        virtualBottom = calculateIndex(n, n) + 1;
    }

    private int calculateIndex(int row, int col) {
        return (row - 1) * gridSize + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || col <= 0 || row >= gridSize + 1 || col >= gridSize + 1) {
            throw new IllegalArgumentException();
        }
        if (!opened[row][col]) {
            opened[row][col] = true;
            openedNumber++;
            unionAfterOpen(row, col);
        }
    }

    private void unionAfterOpen(int row, int col) {
        int nodeIndex = calculateIndex(row, col);

        // union up
        int upNodeRow = row - 1;
        int upNodeIndex = calculateIndex(upNodeRow, col);
        if (row != 1 && isOpen(upNodeRow, col)) {
            percolationUF.union(upNodeIndex, nodeIndex);
            isFullUF.union(upNodeIndex, nodeIndex);
        }
        else if (row == 1) {
            percolationUF.union(nodeIndex, virtualTop);
            isFullUF.union(nodeIndex, virtualTop);
        }

        // union down
        int downNodeRow = row + 1;
        int downNodeIndex = calculateIndex(downNodeRow, col);
        if (row != gridSize && isOpen(downNodeRow, col)) {
            percolationUF.union(downNodeIndex, nodeIndex);
            isFullUF.union(downNodeIndex, nodeIndex);
        }
        else if (row == gridSize) {
            percolationUF.union(nodeIndex, virtualBottom);
        }

        // union left
        int leftNodeCol = col - 1;
        int leftNodeIndex = calculateIndex(row, leftNodeCol);
        if (col != 1 && isOpen(row, leftNodeCol)) {
            percolationUF.union(leftNodeIndex, nodeIndex);
            isFullUF.union(leftNodeIndex, nodeIndex);
        }

        // union right
        int rightNodeCol = col + 1;
        int rightNodeIndex = calculateIndex(row, rightNodeCol);
        if (col != gridSize && isOpen(row, rightNodeCol)) {
            percolationUF.union(rightNodeIndex, nodeIndex);
            isFullUF.union(rightNodeIndex, nodeIndex);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || col <= 0 || row >= gridSize + 1 || col >= gridSize + 1) {
            throw new IllegalArgumentException();
        }
        return opened[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0 || row >= gridSize + 1 || col >= gridSize + 1) {
            throw new IllegalArgumentException();
        }
        int nodeIndex = calculateIndex(row, col);
        return isFullUF.find(nodeIndex) == isFullUF.find(virtualTop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openedNumber;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolationUF.find(virtualTop) == percolationUF.find(virtualBottom);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation perc = new Percolation(4);
        perc.openAndPrint(1, 1);
        System.out.printf("Percolation: %s\n", perc.percolates());
        perc.openAndPrint(4, 2);
        System.out.printf("Percolation: %s\n", perc.percolates());
        perc.openAndPrint(2, 2);
        System.out.printf("Percolation: %s\n", perc.percolates());
        perc.openAndPrint(2, 1);
        System.out.printf("Percolation: %s\n", perc.percolates());
        perc.openAndPrint(3, 2);
        System.out.printf("Percolation: %s\n", perc.percolates());

    }

    private void openAndPrint(int row, int col) {
        open(row, col);
        System.out.printf("open %d, %d\n", row, col);
        printGrid();
    }

    private void printGrid() {
        String ANSI_HL;
        final String ANSI_RESET = "\u001B[0m";

        for (int i = 1; i < gridSize + 1; i++) {
            for (int j = 1; j < gridSize + 1; j++) {
                if (isOpen(i, j) && isFull(i, j)) {
                    ANSI_HL = "\u001B[34m";
                }
                else if (isOpen(i, j)) {
                    ANSI_HL = "\u001B[32m";
                }
                else {
                    ANSI_HL = "";
                }
                System.out.print("test");
            }
            System.out.println();
        }
        System.out.println();
    }

}
