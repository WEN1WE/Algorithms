import java.util.ArrayList;

public class Board {
    private final int n;
    private final int[][] blocks;


    /**
     * construct a board from an n-by-n array of blocks
     */
    public Board(int[][] blocks) {
        if (blocks == null) {
            throw new java.lang.IllegalArgumentException();
        }

        this.n = blocks.length;
        this.blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
    }

    /**
     * board dimension n
     */
    public int dimension() {
        return n;
    }

    /**
     * number of blocks out of place
     */
    public int hamming() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != i * n + j + 1) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != 0) {
                    count += distance(i, j, blocks[i][j]);
                }
            }
        }
        return count;
    }

    /**
     * is this board the goal board?
     */
    public boolean isGoal() {
        return manhattan() == 0;
    }

    /* Node : If the member or constructor is declared private,
    then access is permitted if and only if it occurs within
    he body of the top level class (§7.6) that encloses the
    declaration of the member or constructor. */

    /**
     * a board that is obtained by exchanging any pair of blocks
     */
    public Board twin() {
        // can access newBoard's private method
        Board newBoard = new Board(blocks);
        // change the first row and the second row in the same column
        for (int j = 0; j < n; j++) {
            if (blocks[0][j] != 0 && blocks[1][j] != 0) {
                newBoard.blocks[0][j] = blocks[1][j];
                newBoard.blocks[1][j] = blocks[0][j];
                return newBoard;
            }
        }
        return null;
    }

    /**
     * does this board equal y?
     */
    @Override
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board that = (Board) y;
        if (that.n != n) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.blocks[i][j] != that.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * all neighboring boards
     */
    public Iterable<Board> neighbors() {
        int iSpace = 0;
        int jSpace = 0;
        ArrayList<Board> neighbors = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    iSpace = i;
                    jSpace = j;
                }
            }
        }

        if ((iSpace - 1) >= 0) {
            neighbors.add(genNeighbor(iSpace, jSpace, iSpace - 1, jSpace));
        }
        if ((iSpace + 1) < n) {
            neighbors.add(genNeighbor(iSpace, jSpace, iSpace + 1, jSpace));
        }
        if ((jSpace - 1) >= 0) {
            neighbors.add(genNeighbor(iSpace, jSpace, iSpace, jSpace - 1));
        }
        if ((jSpace + 1) < n) {
            neighbors.add(genNeighbor(iSpace, jSpace, iSpace, jSpace + 1));
        }

        return neighbors;
    }

    /**
     * string representation of this board
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    /**
     * distances from the blocks to their goal positions
     */
    private int distance(int i, int j, int value) {
        int goalOfI = (value - 1) / n;
        int goalOfJ = (value - 1) % n;
        return Math.abs(goalOfI - i) + Math.abs(goalOfJ - j);
    }

    /**
     * generate a neighbor
     */
    private Board genNeighbor(int i, int j, int p, int q) {
        Board neighbor = new Board(blocks);
        neighbor.blocks[i][j] = blocks[p][q];
        neighbor.blocks[p][q] = blocks[i][j];
        return neighbor;
    }

    public static void main(String[] args) {
        int[][] t = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board b = new Board(t);
        /*
        System.out.println(b.hamming());
        System.out.println(b.manhattan());
        System.out.println(b);
        Iterable<Board> neighbors = b.neighbors();
        for (Board board : neighbors) {
            System.out.println(board);
        }
        System.out.println(b.manhattan()); */
        System.out.println(Integer.compare(3, 1));
    }
}
