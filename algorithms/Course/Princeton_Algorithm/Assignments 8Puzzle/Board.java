/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Comparator;

public class Board {

    private int[][] tiles;
    private int n;
    private int hamming = -9999;
    private int manhattan = -9999;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = tiles;
        n = tiles.length;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = new int[2][2];
        tiles[0][0] = 1;
        tiles[0][1] = 2;
        tiles[1][0] = 0;
        tiles[1][1] = 3;
        Board board = new Board(tiles);
        checkHamming(board, 2);
        checkManhattan(board, 2);
        System.out.printf("manhattan should be 2 %b\n", (board.manhattan() == 2));
        tiles = new int[2][2];
        tiles[0][0] = 0; // 2
        tiles[0][1] = 3; // 2
        tiles[1][0] = 1; // 1
        tiles[1][1] = 2; // 1       // System.out.println(board.toString());
        board = new Board(tiles);
        checkHamming(board, 4);
        checkManhattan(board, 6);
        testBasics();
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

    public static Comparator<Board> hammingComparator() {
        return new Comparator<Board>() {
            public int compare(Board b, Board c) {
                return b.hamming() - c.hamming();
            }
        };
    }

    // number of tiles out of place
    public int hamming() {
        if (hamming != -9999) return hamming;
        Board goalBoard = getGoalBoard(n);
        hamming = 0;
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length; col++) {
                if (findColumn(tiles[row][col]) != goalBoard.findColumn(tiles[row][col])
                        || findRow(tiles[row][col]) != goalBoard.findRow(tiles[row][col])) {
                    hamming++;
                }
            }
        }
        return hamming;
    }


    private static Board getGoalBoard(int n) {
        int[][] tiles = new int[n][n];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                tiles[row][col] = row * n + col + 1;
            }
        }
        tiles[n - 1][n - 1] = 0;
        return new Board(tiles);
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

    public static Comparator<Board> manhattanComparator() {
        return new Comparator<Board>() {
            public int compare(Board b, Board c) {
                return b.manhattan() - c.manhattan();
            }
        };
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (manhattan != 9999) return manhattan;
        manhattan = 0;
        Board goalBoard = getGoalBoard(n);
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length; col++) {
                manhattan += Math.abs(goalBoard.findColumn(tiles[row][col]) - col);
                manhattan += Math.abs(goalBoard.findRow(tiles[row][col]) - row);
            }
        }
        return manhattan;
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

    /*
     * index of the row
     */
    private int findModelRow(int k) {
        if (k == 0) return tiles.length - 1;
        return (k - 1) / tiles.length;
    }

    /*
     * index of the column
     */
    private int findModelColumn(int k) {
        if (k == 0) return tiles.length - 1;
        return (k - 1) % tiles.length;
    }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.equals(getGoalBoard(tiles.length));
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (!(y instanceof Board))
            return false;
        Board that = (Board) y;
        if (that.tiles.length != this.tiles.length) {
            return false;
        }
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length; col++) {
                if (tiles[row][col] != that.tiles[row][col]) {
                    return false;
                }
            }
        }
        return true;
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

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return null;
    }

}
