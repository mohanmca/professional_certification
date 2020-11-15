/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class PointSET {
    private final TreeSet<Point2D> pointSets;

    public PointSET() {
        pointSets = new TreeSet<Point2D>();
    }

    public static void main(String[] args) {
        PointSET pointSet = new PointSET();

        List<Point2D> points = Arrays
                .asList(
                        of(0.1, 0.1),
                        of(0.2, 0.2),
                        of(0.3, 0.3),
                        of(0.4, 0.4),
                        of(0.5, 0.5),
                        of(0.6, 0.6),
                        of(0.7, 0.7),
                        of(0.8, 0.8),
                        of(0.9, 0.9),
                        of(1.0, 1.0)
                );
        points.forEach(pointSet::insert);

        Iterable<Point2D> availablePoints = pointSet.range(new RectHV(0.25, 0.25, 0.75, 0.75));
        // for (Point2D point : availablePoints) {
        //     System.out.println("Available points :: " + point.toString());
        // }
        pointSet = new PointSET();

        points = Arrays
                .asList(of(0.5, 1.0),
                        of(0.75, 0.0),
                        of(0.5, 1.0),
                        of(0.5, 0.25),
                        of(0.5, 1.0),
                        of(0.5, 0.25),
                        of(0.0, 0.25),
                        of(0.25, 0.7),
                        of(1.0, 0.0),
                        of(0.75, 1.0));
        //[0.625, 0.875] x [0.0, 0.125]
        points.forEach(pointSet::insert);
        availablePoints = pointSet.range(new RectHV(0.625, 0.0, 0.875, 0.125));
        for (Point2D point : availablePoints) {
            System.out.println("Available points :: " + point.toString());
        }

        pointSet = new PointSET();

        points = Arrays
                .asList(
                        of(0.125, 0.625),
                        of(0.375, 0.5),
                        of(0.0, 0.375),
                        of(0.625, 0.5),
                        of(0.125, 0.0),
                        of(0.25, 0.5),
                        of(0.25, 0.125),
                        of(0.875, 0.0),
                        of(0.875, 0.625),
                        of(0.5, 0.5),
                        of(1.0, 0.0),
                        of(0.625, 0.75),
                        of(0.25, 0.25),
                        of(0.375, 1.0),
                        of(0.0, 1.0)

                );
        points.forEach(pointSet::insert);
        System.out.println(pointSet.nearest(of(0.625, 1.0)));
        System.out.println(pointSet.nearest(of(0.625, 1.0)).equals(of(0.625, 0.75)));
        pointSet = new PointSET();

        points = Arrays
                .asList(of(0.0, 0.25), of(1.0, 1.0), of(0.75, 0.0), of(0.75, 0.5),
                        of(0.0, 0.5), of(0.75, 0.0), of(0.5, 0.0), of(1.0, 1.0),
                        of(0.25, 0.5), of(1.0, 1.0));
        points.forEach(pointSet::insert);
        System.out.println(pointSet.nearest(of(0.25, 1.0)));
        System.out.println(pointSet.nearest(of(0.25, 1.0)).equals(of(0.25, 0.5)));


        pointSet = new PointSET();

        points = Arrays
                .asList(of(0.0, 0.75), of(0.25, 1.0),
                        of(0.5, 0.5), of(1.0, 1.0),
                        of(1.0, 0.25), of(0.75, 0.75));

        points.forEach(pointSet::insert);
        System.out.println(pointSet.nearest(of(0.0, 0.0)));
        System.out.println(pointSet.nearest(of(0.0, 0.0)).equals(of(0.5, 0.5)));
        assertExpression("Size of the PointSET", pointSet.size() == 6);


        Point2D contains = new Point2D(0.7, 0.8);
        pointSet.insert(contains);
        contains = new Point2D(0.6, 0.5);
        pointSet.insert(contains);


        Iterator<Point2D> iter = pointSet.range(new RectHV(0.4, 0.3, 0.8, 0.6)).iterator();
        System.out.println(iter.hasNext());
        System.out.println(iter.next());
        pointSet.insert(new Point2D(0.44, 0.33));
        System.out.println(pointSet.nearest(new Point2D(0.42, 0.31)));
    }

    private static Point2D of(double x, double y) {
        return new Point2D(x, y);
    }

    public void insert(Point2D key) {
        if (key == null) throw new IllegalArgumentException("Null argument while inserting!");
        pointSets.add(key);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Null argument while range!");
        Point2D lowerLeft = new Point2D(rect.xmin(), rect.ymin());
        Point2D topRight = new Point2D(rect.xmax(), rect.ymax());

        return pointSets.subSet(lowerLeft, true, topRight, true).stream().filter(rect::contains)
                        .collect(
                                Collectors.toList());
    }

    public Point2D nearest(Point2D query) {
        if (query == null) throw new IllegalArgumentException("Null argument while nearest!");

        if (pointSets.isEmpty()) return null;

        Point2D nearest = null;
        Double nearestDistance = Double.MAX_VALUE;
        for (Point2D point : pointSets) {
            if (point.distanceSquaredTo(query) < nearestDistance) {
                nearest = point;
                nearestDistance = point.distanceSquaredTo(query);
            }
        }

        return nearest;
    }

    private static void assertExpression(String message, boolean expression) {
        if (!expression)
            throw new IllegalArgumentException(message);
    }

    public int size() {
        return pointSets.size();
    }

    public void draw() {
        for (Point2D point : pointSets) {
            StdDraw.point(point.x(), point.y());
        }
    }

    public boolean isEmpty() {
        return pointSets.isEmpty();
    }

    public boolean contains(Point2D key) {
        if (key == null) throw new IllegalArgumentException("Null argument contains nearest!");

        return pointSets.contains(key);
    }


}
