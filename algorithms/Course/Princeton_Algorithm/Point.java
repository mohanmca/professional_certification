/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point p = new Point(2, 2);
        Point q = new Point(3, 3);
        compare(p, q);
        compareSlope(p, q);
        q = new Point(1, 1);
        compare(p, q);
        compareSlope(p, q);
        compare(p, p);
        compareSlope(p, p);

    }

    private static void compare(Point p, Point q) {
        int comparision = p.compareTo(q);
        if (comparision > 0) {
            System.out
                    .printf("P is greater than q, P: %s, Q: %s, %d, %b\n", p.toString(),
                            q.toString(),
                            p.compareTo(q), p.compareTo(q) > 0);

        }
        else if (comparision < 0) {
            System.out
                    .printf("P is smaller than q, P: %s, Q: %s, %d, %b\n", p.toString(),
                            q.toString(),
                            comparision, comparision < 0);
        }
        else {
            System.out
                    .printf("P and q are same points, P: %s, Q: %s, %d, %b\n", p.toString(),
                            q.toString(),
                            comparision, comparision == 0);
        }
    }

    private static void compareSlope(Point p, Point q) {
        double slope = p.slopeTo(q);
        if (slope > 0) {
            System.out
                    .printf("P has positive slope with q, P: %s, Q: %s, %f, %b\n", p.toString(),
                            q.toString(),
                            slope, slope > 0);

        }
        else if (slope != Double.NEGATIVE_INFINITY && slope < 0) {
            System.out
                    .printf("P has negative slope with q, P: %s, Q: %s, %f, %b\n", p.toString(),
                            q.toString(),
                            slope, slope < 0);
        }
        else {
            System.out
                    .printf("P has degenerate point segment with q (same point has -ve infinity in slope), P: %s, Q: %s, %f, %b\n",
                            p.toString(),
                            q.toString(),
                            slope, slope == 0);
        }
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        int yDiff = this.y - that.y;
        int xDiff = this.x - that.x;
        if (yDiff != 0) {
            return yDiff;
        }
        return xDiff;
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        double yDiff = this.y - that.y;
        double xDiff = this.x - that.x;
        if (yDiff == 0 && xDiff == 0) {
            return Double.NEGATIVE_INFINITY;
        }
        else if (yDiff != 0 && xDiff == 0) { // vertical line
            return Double.POSITIVE_INFINITY;
        }
        else if (yDiff == 0 && xDiff != 0) { // horizontal line
            return +0.0;
        }
        double slope = (yDiff / xDiff);
        return slope;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {
            public int compare(Point p, Point q) {
                double s1 = slopeTo(p);
                double s2 = slopeTo(q);
                if (s1 - s2 > 0)
                    return 1;
                if (s1 - s2 < 0)
                    return -1;
                return 0;
            }
        };
    }
}
