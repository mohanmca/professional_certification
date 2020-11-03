/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Comparator;

public class OtherComparator {
    public static void main(String[] args) {

    }
    private static Comparator<Board> manhattanComparator() {
        return (Board b, Board c) -> (b.manhattan() - c.manhattan());
    }

    private static Comparator<Board> hammingComparator() {
        return new Comparator<Board>() {
            public int compare(Board b, Board c) {
                int manhattonDiff = b.manhattan() - c.manhattan();
                if (manhattonDiff != 0)
                    return manhattonDiff;
                else
                    return b.hamming() - c.hamming();
            }
        };
    }

}
