/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class KdTreeNearest {

    public static void main(String[] args) {
        KdTree pointSet = new KdTree();

        List<Point2D> points = Arrays
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
        pointSet = new KdTree();

        points = Arrays
                .asList(of(0.0, 0.25), of(1.0, 1.0), of(0.75, 0.0), of(0.75, 0.5),
                        of(0.0, 0.5), of(0.75, 0.0), of(0.5, 0.0), of(1.0, 1.0),
                        of(0.25, 0.5), of(1.0, 1.0));
        points.forEach(pointSet::insert);
        System.out.println(pointSet.nearest(of(0.25, 1.0)));
        System.out.println(pointSet.nearest(of(0.25, 1.0)).equals(of(0.25, 0.5)));


        pointSet = new KdTree();

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
        if (iter.hasNext())
            System.out.println(iter.next());
        pointSet.insert(new Point2D(0.44, 0.33));
        System.out.println(pointSet.nearest(new Point2D(0.42, 0.31)));
    }

    private static Point2D of(double x, double y) {
        return new Point2D(x, y);
    }

    private static void assertExpression(String message, boolean expression) {
        if (!expression)
            throw new IllegalArgumentException(message);
    }

}
