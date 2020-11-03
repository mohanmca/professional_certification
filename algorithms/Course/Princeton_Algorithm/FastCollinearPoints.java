/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Spliterator;

public class FastCollinearPoints {
    private static Point[] points;
    private final LineSegment[] ls;

    public FastCollinearPoints(Point[] points) {   // finds all line segments containing 4 points
        ArrayList<LineSegment> lineSegments = new ArrayList<LineSegment>(6);
        points = points;
        Arrays.sort(points);


        for (int i = 0; i < points.length - 1; i++) {
            Arrays.sort(points);
            Arrays.sort(points, i + 1, points.length, points[i].slopeOrder());
            int k = i + 1;
            int j = k;
            double slope = points[i].slopeTo(points[k]);
            while (k < points.length) {
                double newSlope = points[i].slopeTo(points[k]);
                // StdOut.print("\nSlope between - " + i + " and " + k + " was / " + slope + "/ " + points[j].toString() + points[k].toString() );
                if (newSlope != slope) {
                    slope = newSlope;
                    //noOfPoints
                    if (k - j >= 3) {
                        lineSegments
                                .add(toLineSegment(points[i], Arrays.spliterator(points, j, k)));
                    }
                    j = k;
                }
                k++;
            }
            if (k - j >= 3) {
                lineSegments.add(toLineSegment(points[i], Arrays.spliterator(points, j, k)));
            }
        }
        ls = lineSegments.toArray(new LineSegment[0]);
    }

    private static LineSegment toLineSegment(Point p, Spliterator<Point> linePoints) {
        ArrayList<Point> sortedLinePoints = new ArrayList<Point>();
        sortedLinePoints.add(p);
        linePoints.forEachRemaining(sortedLinePoints::add);
        Collections.sort(sortedLinePoints);
        return new LineSegment(sortedLinePoints.get(0),
                               sortedLinePoints.get(sortedLinePoints.size() - 1));
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }

    public LineSegment[] segments() {             // the line segments
        return ls;
    }

    public int numberOfSegments() {       // the number of line segments
        return ls.length;
    }
}
