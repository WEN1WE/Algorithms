import edu.princeton.cs.algs4.MinPQ;
import java.util.LinkedList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private Node goalNode;
    private boolean isSolvable;

    /**
     * find a solution to the initial board (using the A* algorithm)
     */
    public Solver(Board initial) {
        if (initial == null) {
            throw new java.lang.IllegalArgumentException();
        }

        Board swapBoard = initial.twin();
        MinPQ<Node> pq = new MinPQ<>();
        pq.insert(new Node(initial, 0, null));
        pq.insert(new Node(swapBoard, 0, null));

        while (true) {
            Node current = pq.delMin();
            if (current.board.isGoal()) {
                goalNode = current;
                if (getInit(current).board.equals(initial)) {
                    isSolvable = true;
                }
                break;
            } else {
                for (Board neighbor : current.board.neighbors()) {
                    if (current.predecessor != null && neighbor.equals(current.predecessor.board)) {
                        continue;
                    }
                    pq.insert(new Node(neighbor, current.move + 1, current));
                }
            }
        }

    }


    /**
     * is the initial board solvable?
     */
    public boolean isSolvable() {
        return isSolvable;
    }

    /**
     * min number of moves to solve initial board; -1 if unsolvable
     */
    public int moves() {
        return isSolvable() ? goalNode.move : -1;
    }

    /**
     * sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }

        LinkedList<Board> path = new LinkedList<>();
        Node current = goalNode;
        while (current != null) {
            path.addFirst(current.board);
            current = current.predecessor;
        }
        return path;
    }

    /**
     * construct search node
     */
    private class Node implements Comparable<Node> {
        private final Board board;
        private final int move;
        private final int manhattan;
        private final Node predecessor;

        Node(Board board, int move, Node predecessor) {
            this.board = board;
            this.move = move;
            this.manhattan = board.manhattan();
            this.predecessor = predecessor;
        }

        @Override
        public int compareTo(Node other) {
            int d = (manhattan + move) - (other.manhattan + other.move);
            if (d == 0) {
                return manhattan - other.manhattan;
            } else {
                return d;
            }
        }
    }

    private Node getInit(Node node) {
        while (node.predecessor != null) {
            node = node.predecessor;
        }
        return node;
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
