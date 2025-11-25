import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {
    private Node goal;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        MinPQ<Node> minPQ = new MinPQ<>(compByManhattan());
        MinPQ<Node> minPQTwin = new MinPQ<>(compByManhattan());

        Node currNode = new Node(initial, 0, null);
        Node currNodeTwin = new Node(initial.twin(), 0, null);

        while (!currNode.board.isGoal() && !currNodeTwin.board.isGoal()) {
            for (Board neighbor: currNode.board.neighbors()) {
                if (currNode.preNode == null || !neighbor.equals(currNode.preNode.board)) {
                    Node childNode = new Node(neighbor, currNode.moves + 1, currNode);
                    minPQ.insert(childNode);
                }
            }
            for (Board neighbor: currNodeTwin.board.neighbors()) {
                if (currNode.preNode == null || !neighbor.equals(currNodeTwin.preNode.board)) {
                    Node childNode = new Node(neighbor, currNodeTwin.moves + 1, currNodeTwin);
                    minPQTwin.insert(childNode);
                }
            }
            currNode = minPQ.delMin();
            currNodeTwin = minPQTwin.delMin();
        }
        if (currNode.board.isGoal()) {
            goal = currNode;
        }
        if (currNodeTwin.board.isGoal()) {
            goal = null;
        }
    }

    private class Node {
        Board board;
        int manhattan;
        int moves;
        Node preNode;

        Node(Board board, int moves, Node preNode) {
            this.board = board;
            this.moves = moves;
            this.preNode = preNode;
            this.manhattan = board.manhattan();
        }
    }

    private Comparator<Node> compByManhattan() {
        return (Node node1, Node node2) ->
                node1.manhattan + node1.moves - node2.manhattan - node2.moves;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return goal != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (goal == null) {
            return -1;
        } else {
            return goal.moves;
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (goal == null) {
            return null;
        } else {
            Stack<Board> solutionStack = new Stack<>();
            Node currNode = goal;
            while (currNode != null) {
                solutionStack.push(currNode.board);
                currNode = currNode.preNode;
            }
            return solutionStack;
        }
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In("testCase\\puzzle04.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

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
