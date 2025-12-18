import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size;
    private WeightedQuickUnionUF uf;
    private boolean[] open;
    private int virtualTop;
    private int virtualBottom;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than zero");
        }

        size = n;
        uf = new WeightedQuickUnionUF(n * n + 2);
        virtualTop = n * n;
        virtualBottom = n * n + 1;

        open = new boolean[n * n];
        for (int i = 1; i < n * n; i++) {
            open[i] = false;
        }

    }

    private int index(int row, int col) {
        return (row - 1) * size + (col - 1);
    }

    private boolean withinGrid(int row, int col) {
        return (1 <= row) && (row <= size) && (1 <= col) && (col <= size);
    }

    private void validate(int row, int col) {
        if (!withinGrid(row, col)) {
            throw new IllegalArgumentException(
                    "Site (" + row + "," + col + ") is outside the grid"
            );
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);

        if (isOpen(row, col)) {
            return;
        }

        // mark site as open
        open[index(row, col)] = true;

        // connect to all adjacent open sites
        int[] shifts = { -1, 1 };
        int[] axes = { 0, 1 };

        for (int dx : shifts) {
            for (int axis : axes) {
                int row2 = row + dx * (1 - axis);
                int col2 = col + dx * (axis);
                if (withinGrid(row2, col2) && isOpen(row2, col2)) {
                    uf.union(index(row, col), index(row2, col2));
                }
            }
        }

        // connect sites in the top row to the virtual top site
        if (row == 1) {
            uf.union(index(row, col), virtualTop);
        }

        // connect sites in the bottom row to the virtual bottom site
        if (row == size) {
            uf.union(index(row, col), virtualBottom);
        }


    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return open[index(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return uf.find(index(row, col)) == uf.find(virtualTop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for (boolean b : open) {
            if (b) count++;
        }
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(virtualTop) == uf.find(virtualBottom);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(10);

        System.out.println("Opening (1, 1)");
        p.open(1, 1);
        System.out.println("# of open sites: " + p.numberOfOpenSites());

        System.out.println("(1, 1) is full: " + p.isFull(1, 1));

        System.out.println("Root of 0: " + p.uf.find(0));
        System.out.println("Root of the virtual top: " + p.uf.find(p.virtualTop));

        for (int i = 1; i < 10; i++) {
            System.out.println(
                    "Opening (" + i + ", " + i + ") and (" + (i + 1) + ","
                            + i + ")");
            p.open(i, i);
            p.open(i + 1, i);
            System.out.println("# of open sites: " + p.numberOfOpenSites());
            System.out.println("Percolates: " + p.percolates());
        }

        System.out.println(p.uf.find(p.virtualBottom) + " "
                                   + p.uf.find(p.virtualTop));
        System.out.println("Root of (10, 9): " + p.uf.find(98));

        // print all roots
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                int index = (i - 1) * 10 + (j - 1);
                System.out.print(p.uf.find(index) + " ");
            }
            System.out.println();
        }

    }
}
