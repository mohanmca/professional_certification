/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private static final int INVALID = -9999;
    private final int hamming;
    private final int manhattan;
    private final int n;
    private int[][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = tiles;
        n = tiles.length;
        this.hamming = calcHamming();
        this.manhattan = calcManhattan();
    }

    // number of tiles out of place
    private int calcHamming() {
        int localHamming = 0;
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length; col++) {
                if (tiles[row][col] != 0) {
                    if (findColumn(tiles[row][col]) != findGoalColumn(tiles[row][col])
                            || findRow(tiles[row][col]) != findGoalRow(tiles[row][col])) {
                        localHamming++;
                    }

                }
            }
        }
        return localHamming;
    }

    // sum of Manhattan distances between tiles and goal
    private int calcManhattan() {
        int localManhattan = 0;
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length; col++) {
                if (tiles[row][col] != 0) {
                    localManhattan += Math.abs(findGoalColumn(tiles[row][col]) - col);
                    localManhattan += Math.abs(findGoalRow(tiles[row][col]) - row);
                }
            }
        }
        return localManhattan;
    }

    /*
     * index of the row
     */
    private int findColumn(int k) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] == k) return j;
            }
        }
        return -1;
    }

    /*
     * index of the column
     */
    private int findGoalColumn(int value) {
        return (value - 1) % n;
    }

    /*
     * index of the column
     */
    private int findGoalRow(int value) {
        return (value - 1) / n;
    }

    /*
     * index of the row
     */
    private int findRow(int k) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] == k) return i;
            }
        }
        return -1;
    }



    private Board(int[][] tiles, int hamming, int manhattan) {
        this.tiles = tiles.clone();
        n = tiles.length;
        this.hamming = hamming;
        this.manhattan = manhattan;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = new int[2][2];
        tiles[0][0] = 1;
        tiles[0][1] = 2;
        tiles[1][0] = 0;
        tiles[1][1] = 3;
        Board board = new Board(tiles);
        while (!board.twin().equals(board))

            checkHamming(board, 2);
        checkManhattan(board, 2);
        System.out.printf("manhattan should be 2 %b\n", (board.manhattan() == 2));
        tiles = new int[2][2];
        tiles[0][0] = 0; // 2
        tiles[0][1] = 3; // 2
        tiles[1][0] = 1; // 1
        tiles[1][1] = 2; // 1       // System.out.println(board.toString());
        board = new Board(tiles);
        System.out.printf("Board %s vs Its Twin %s \n", board.toString(), board.twin().toString());
        checkHamming(board, 4);
        checkManhattan(board, 6);
        testBasics();
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y.getClass() != Board.class)
            return false;
        Board that = (Board) y;
        if (that.tiles.length != this.tiles.length) {
            return false;
        }
        return Arrays.deepEquals(this.tiles, that.tiles);
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twinTiles = new int[tiles.length][tiles.length];
        for (int i = 0; i < twinTiles.length; i++) {
            for (int j = 0; j < twinTiles.length; j++) {
                twinTiles[i][j] = tiles[i][j];
            }
        }

        int pos = findNonZeroIndex(1);
        int pos2 = findNonZeroIndex(pos + 1);
        swap(twinTiles, pos, pos2);
        return new Board(twinTiles);
    }

    private static void checkHamming(Board b, int i) {
        System.out.printf("Hamming for board %s should be %d - %b\n", b.toString(), i,
                          (b.hamming() == i));
    }


    private static void checkManhattan(Board b, int i) {
        int man = b.manhattan();
        System.out.printf("Manhattan for board %s should be %d - %b\n", b.toString(), i,
                          (man == i));
    }

    public int manhattan() {
        return this.manhattan;
    }

    private static void testBasics() {
        System.out.printf("Goal board + rotated t times should be same as original :: %s\n",
                          (Board.getGoalBoard(2)
                                .equals(Board.getGoalBoard(2).right().down().left().up())));
        System.out.printf("You can't use up in Goal board :: %b\n",
                          (Board.getGoalBoard(2).up() == null));
        System.out.printf("Goal board + down is :: %s\n",
                          (Board.getGoalBoard(2).down().toString()));
        System.out.printf("Goal board + right is :: %s\n",
                          (Board.getGoalBoard(2).right().toString()));

        System.out.println(Board.getGoalBoard(2).toString());
        System.out.println(Board.getGoalBoard(2).manhattan());
        System.out.println(Board.getGoalBoard(2).hamming());
        for (Board b : Board.getGoalBoard(2).neighbors()) {
            System.out.println(b.toString());
        }
    }

    private int findNonZeroIndex(int begin) {
        if (begin > n * n)
            throw new IllegalArgumentException("Argument should be less than dimension^2");
        for (int i = begin; i < (n * n); i++) {
            int col = findGoalColumn(i);
            int row = findGoalRow(i);
            if (tiles[row][col] != 0) {
                return i;
            }
        }
        return -1;
    }

    private void swap(int[][] twinTiles, int from, int to) {
        int fromRow = findGoalRow(from);
        int fromCol = findGoalColumn(from);
        int toRow = findGoalRow(to);
        int toCol = findGoalColumn(to);

        int temp = twinTiles[fromRow][fromCol];
        twinTiles[fromRow][fromCol] = twinTiles[toRow][toCol];
        twinTiles[toRow][toCol] = temp;
    }

    public int hamming() {
        return this.hamming;
    }

    private static Board getGoalBoard(int n) {
        int[][] tiles = new int[n][n];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                tiles[row][col] = row * n + col + 1;
            }
        }
        tiles[n - 1][n - 1] = 0;
        return new Board(tiles, 0, 0);
    }

    /*
     Zero - empty position goes down
     */
    private Board up() {
        if (findRow(0) == n - 1) return null;

        int row = findRow(0);
        int col = findColumn(0);
        Board newBoard = this.copy();
        newBoard.tiles[row][col] = newBoard.tiles[row + 1][col];
        newBoard.tiles[row + 1][col] = 0;
        return newBoard;
    }

    /*
     Slider number moves left, zero moves right
     Zero - empty column goes higher
     */
    private Board left() {
        if (findColumn(0) == n - 1) return null;
        int row = findRow(0);
        int col = findColumn(0);
        Board newBoard = this.copy();
        newBoard.tiles[row][col] = newBoard.tiles[row][col + 1];
        newBoard.tiles[row][col + 1] = 0;
        return newBoard;
    }

    /*
     Zero - empty position goes up
     */
    private Board down() {
        if (findRow(0) == 0) return null;
        int row = findRow(0);
        int col = findColumn(0);
        Board newBoard = this.copy();
        newBoard.tiles[row][col] = newBoard.tiles[row - 1][col];
        newBoard.tiles[row - 1][col] = 0;
        return newBoard;
    }

    /*
     Zero - empty column goes lower
     */
    private Board right() {
        if (findColumn(0) == 0) return null;
        int row = findRow(0);
        int col = findColumn(0);
        Board newBoard = this.copy();
        newBoard.tiles[row][col] = newBoard.tiles[row][col - 1];
        newBoard.tiles[row][col - 1] = 0;
        return newBoard;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> boards = new ArrayList<Board>(n);
        Board newBoard = null;
        newBoard = up();
        if (newBoard != null) boards.add(newBoard);
        newBoard = down();
        if (newBoard != null) boards.add(newBoard);
        newBoard = left();
        if (newBoard != null) boards.add(newBoard);
        newBoard = right();
        if (newBoard != null) boards.add(newBoard);
        return boards;
    }

    // sum of Manhattan distances between tiles and goal
    private Board copy() {
        int[][] newTiles = new int[tiles.length][tiles.length];
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length; col++) {
                newTiles[row][col] = tiles[row][col];
            }
        }
        return new Board(newTiles);
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(tiles.length + "\n");
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {
                sb.append(tiles[row][col]);
                if (col <= tiles[row].length - 1) {
                    sb.append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /*
     * index of the row
     */
    private int findModelValue(int row, int col) {
        return row * n + (col + 1);
    }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.equals(getGoalBoard(tiles.length));
    }

}
