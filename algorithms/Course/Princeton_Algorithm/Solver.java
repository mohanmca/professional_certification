/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class Solver {
    private Board board;
    private List<Board> solution;

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
            StdOut.printf("Minimum number of moves = %d\n", solver.moves());
            Iterable<Board> solution = solver.solution();
            if (solution != null) {
                for (Board board2 : solution)
                    StdOut.println(board2.toString());
            }
            else {
                StdOut.println("No solutions!");
            }
        }
    }


    private boolean isTwinSolvable() {
        Solver twinSolver = new Solver(this.board.twin());
        return twinSolver.moves() != -1;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (solution == null)
            solution();
        else if (solution.isEmpty())
            return -1;
        return solution.size() - 1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (solution == null) {
            solution = searchAStart();
        }
        return solution;
    }

    private List<Board> searchAStart() {
        Comparator<SearchNode> nodeComparator = new SearchNodeComparator();
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>(10, nodeComparator);
        pq.insert(new SearchNode(board));
        while (!pq.isEmpty()) {
            SearchNode currentNode = pq.delMin();
            if (currentNode.priority() > Math.pow(this.board.dimension(), 4)) {
                return Arrays.asList();
            }
            else if (currentNode.getBoard().isGoal()) {
                return Arrays.asList(fromSearchNodes(currentNode));
            }
            else {
                Iterable<Board> boards = currentNode.board.neighbors();
                for (Board instanceBoard : boards) {
                    SearchNode childNode = currentNode.nextNode(instanceBoard);
                    if (childNode != null)
                        pq.insert(childNode);
                }
            }
        }
        return null;
    }

    private Board[] fromSearchNodes(SearchNode node) {
        if (node == null)
            return null;
        Board[] boards = new Board[node.getMoves() + 1];
        int j = node.getMoves() + 1;
        while (--j > -1) {
            boards[j] = node.getBoard();
            node = node.getPreviousNode();
        }
        return boards;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        if (moves() != -1)
            return true;
        return !(moves() == -1 && isTwinSolvable());
    }

    private static class SearchNodeComparator implements Comparator<SearchNode> {
        public int compare(SearchNode p, SearchNode q) {
            return (p.priority() - q.priority());
        }

        public Comparator<SearchNode> reversed() {
            return (SearchNode p, SearchNode q) -> new SearchNodeComparator().compare(q, p);
        }
    }

    private static class SearchNode {
        private int moves;
        private SearchNode previousSearchNode;
        private Board board;

        public SearchNode(Board board) {
            this.previousSearchNode = null;
            this.board = board;
            this.moves = 0;
        }

        public SearchNode(SearchNode previousNode, Board board, int moves) {
            this.previousSearchNode = previousNode;
            this.board = board;
            this.moves = moves;
        }

        public SearchNode nextNode(Board nextBoard) {
            if (this.previousSearchNode != null && this.previousSearchNode.board.equals(nextBoard))
                return null;

            return new SearchNode(this, nextBoard, moves + 1);
        }

        public String toString() {
            return (previousSearchNode != null ? previousSearchNode.toString() : "Origin")
                    + " -- > " + moves
                    + " :  " + board
                    .toString();
        }

        public SearchNode getPreviousNode() {
            return previousSearchNode;
        }

        public Board getBoard() {
            return board;
        }

        public int priority() {
            return this.board.manhattan() + this.getMoves();
        }

        public int getMoves() {
            return moves;
        }
    }
}
