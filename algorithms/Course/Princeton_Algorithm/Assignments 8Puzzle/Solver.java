/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Solver {
    private Board board;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        this.board = initial;
    }

    // test client (see below)
    public static void main(String[] args) {


        // for each command-line argument
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            Board initial = new Board(tiles);
            Solver solver = new Solver(initial);
            int i = 0;
            Iterable<Board> solution = solver.solution();
            if (solution != null) {
                for (Board board2 : solution)
                    StdOut.println("Moves : " + (i++) + ", \t\t " + board2.toString());
            } else {
                StdOut.println("No solutions!");
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return false;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return board.hamming();
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        ArrayList<Board> moves = new ArrayList<Board>();
        MinPQ<Board> pq = new MinPQ<Board>(10, Board.hammingComparator());
        pq.insert(board);
        while (!pq.isEmpty()) {
            Board nearGoal = pq.delMin();
            System.out.println(nearGoal);
            moves.add(nearGoal);
            if (nearGoal.isGoal()) {
                return moves;
            }
            else {
                Iterable<Board> boards = nearGoal.neighbors();
                pq = new MinPQ<Board>(10, Board.hammingComparator());
                for (Board board : boards) {
                    if (!moves.contains(board))
                        pq.insert(board);
                }
            }
        }
        return null;
    }
}