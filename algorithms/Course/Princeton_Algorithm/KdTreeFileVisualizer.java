/******************************************************************************
 *  Compilation:  javac RangeSearchVisualizer.java
 *  Execution:    java RangeSearchVisualizer input.txt
 *  Dependencies: PointSET.java KdTree3.java
 *
 *  Read points from a file (specified as a command-line argument) and
 *  draw to standard draw. Also draw all of the points in the rectangle
 *  the user selects by dragging the mouse.
 *
 *  The range search results using the brute-force algorithm are drawn
 *  in red; the results using the kd-tree algorithms are drawn in blue.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;

public class KdTreeFileVisualizer {

    double x0 = 0.0, y0 = 0.0;      // initial endpoint of rectangle
    double x1 = 0.0, y1 = 0.0;      // current location of mouse
    boolean isDragging = false;     // is the user dragging a rectangle

    public static void main(String[] args) {

        try {
            // initialize the data structures from file
            String filename = args[0];
            In in = new In(filename);
            PointSET brute = new PointSET();
            KdTree kdtree = new KdTree();
            StdDraw.show();
            char position = 'A' - 1;
            int pointCount = 0;

            while (!in.isEmpty()) {
                position++;
                String positionChar = in.readChar() + "";
                if (positionChar.equals("\n")) {
                    positionChar = in.readChar() + "";
                }
                if (positionChar.equals("0")) {
                    positionChar = position + "";
                }
                if (positionChar.equals("Z")) {
                    pointCount++;
                    double x = in.readDouble();
                    double y = in.readDouble();
                    double radius = StdDraw.getPenRadius();
                    StdDraw.setPenRadius(0.01);
                    StdDraw.point(x, y);
                    StdDraw.setPenRadius(radius);
                    plotChar("P" + pointCount, x, y);
                }
                else {
                    double x = in.readDouble();
                    double y = in.readDouble();
                    Point2D p = new Point2D(x, y);
                    kdtree.insert(p);
                    brute.insert(p);
                    kdtree.draw();
                    // double oldRadius = StdDraw.getPenRadius();
                    // StdDraw.setPenRadius(0.01);
                    plotChar(positionChar, x, y);

                    // StdDraw.setPenRadius(oldRadius);
                }
            }

            // draw the points
            // StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            // brute.draw();
            StdDraw.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
        }


    }

    private static void plotChar(String positionChar, double x, double y) {
        double xPosition = x;
        double yPosition = y;
        if (xPosition >= 0.95) {
            xPosition = xPosition - 0.05;
        }
        else if (xPosition < 0.05) {
            xPosition = xPosition + 0.05;
        }
        else {
            xPosition = xPosition - 0.025;
        }

        if (yPosition >= 0.95) {
            yPosition = yPosition - 0.05;
        }
        else if (yPosition < 0.05) {
            yPosition = yPosition + 0.05;
        }
        else {
            yPosition = yPosition - 0.025;
        }

        StdDraw.text(xPosition, yPosition, positionChar);
    }
}
