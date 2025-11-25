import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class Board {
    private int[][] tiles;
    private int boardSize;
    private int hamming;
    private int manhattan;
    private boolean hasChanged;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        boardSize = tiles.length;
        this.tiles = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
        hasChanged = true;
    }

    // string representation of this board
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(dimension() + "\n");
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                result.append(String.format("%2d ", tiles[i][j]));
            }
            result.append("\n");
        }
        return result.toString();
    }

    // board dimension n
    public int dimension() {
        return boardSize;
    }

    // number of tiles out of place
    public int hamming() {
        if (!hasChanged) return hamming;
        hamming = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (tiles[i][j] != i * boardSize + j + 1) {
                    hamming++;
                }
            }
        }
        // reduce blank square
        hamming--;
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (!hasChanged) return manhattan;
        manhattan = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                int currTile = tiles[i][j];
                if (currTile == 0) continue;
                int correctRow = (currTile - 1) / boardSize;
                int correctCol = currTile - 1 - correctRow * boardSize;
                manhattan += Math.abs(correctRow - i);
                manhattan += Math.abs(correctCol - j);
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return that.boardSize == this.boardSize
                && Arrays.deepEquals(that.tiles, this.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Bag<Board> neighbors = new Bag<>();
        int blankRow = -1;
        int blankCol = -1;
        outer:
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (tiles[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                    break outer;
                }
            }
        }

        if (blankRow != 0) {
            Board moveDown = new Board(tiles);
            moveDown.exch(blankRow, blankCol, blankRow - 1, blankCol);
            neighbors.add(moveDown);
        }

        if (blankRow + 1 != boardSize) {
            Board moveUp = new Board(tiles);
            moveUp.exch(blankRow, blankCol, blankRow + 1, blankCol);
            neighbors.add(moveUp);
        }

        if (blankCol != 0) {
            Board moveRight = new Board(tiles);
            moveRight.exch(blankRow, blankCol, blankRow, blankCol - 1);
            neighbors.add(moveRight);
        }

        if (blankCol + 1 != boardSize) {
            Board moveLeft = new Board(tiles);
            moveLeft.exch(blankRow, blankCol, blankRow, blankCol + 1);
            neighbors.add(moveLeft);
        }
        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board newBoard = new Board(tiles);
        int[][] firstTwoExistTile = new int[2][2];
        int cnt = 0;
        outer:
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (newBoard.tiles[i][j] != 0) {
                    firstTwoExistTile[cnt][0] = i;
                    firstTwoExistTile[cnt][1] = j;
                    cnt++;
                    if (cnt == 2) break outer;
                }
            }
        }
        newBoard.exch(firstTwoExistTile[0][0], firstTwoExistTile[0][1],
                      firstTwoExistTile[1][0], firstTwoExistTile[1][1]);
        return newBoard;
    }

    private void exch(int rowA, int colA, int rowB, int colB) {
        int temp = tiles[rowA][colA];
        tiles[rowA][colA] = tiles[rowB][colB];
        tiles[rowB][colB] = temp;
        hasChanged = true;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // build board
        int n = 3;
        int[] oneDimTiles = new int[n*n];
        for (int i = 0; i < oneDimTiles.length; i++) {
            oneDimTiles[i] = i;
        }
        StdRandom.shuffle(oneDimTiles);

        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = oneDimTiles[i * n + j];
            }
        }
        Board board = new Board(tiles);

        StdOut.print(board);
        StdOut.println();

        StdOut.println("hamming: " + board.hamming());
        StdOut.println("manhattan: " + board.manhattan());
        StdOut.println();

        Board board1 = new Board(tiles);
        StdOut.println("A board equals to it's copy: " + board.equals(board1));

        Board board2 = board.twin();
        StdOut.print("twin:\n" + board2);

        StdOut.println("A board equals to it's twin: " + board.equals(board2));
        StdOut.println();

        StdOut.println("neighbors:");
        for (Board i: board.neighbors()) {
            StdOut.print(i);
        }
        StdOut.println();
    }
}