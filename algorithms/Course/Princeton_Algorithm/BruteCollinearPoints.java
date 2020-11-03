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
import java.util.List;

public class BruteCollinearPoints {
    private static Point[] points;
    private final LineSegment[] ls;

    public BruteCollinearPoints(Point[] points) {   // finds all line segments containing 4 points
        ArrayList<LineSegment> lineSegments = new ArrayList<LineSegment>(6);
        BruteCollinearPoints.points = points;
        Arrays.sort(points);


        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                double slopeIJ = points[i].slopeTo(points[j]);
                for (int k = j + 1; k < points.length - 1; k++) {
                    double slopekJK = points[j].slopeTo(points[k]);
                    if (slopeIJ == slopekJK) {
                        for (int l = k + 1; l < points.length; l++) {
                            double slopeKL = points[k].slopeTo(points[l]);
                            if (slopeKL == slopekJK) {
                                List<Point> linePoints = Arrays
                                        .asList(points[i], points[j], points[k], points[l]);
                                lineSegments.add(toLineSegment(linePoints));
                            }
                        }
                    }
                }
            }
        }
        ls = lineSegments.toArray(new LineSegment[0]);
    }

    private LineSegment toLineSegment(List<Point> linePoints) {
        Collections.sort(linePoints);
        return new LineSegment(linePoints.get(0), linePoints                .get(linePoints.size() - 1));
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
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
