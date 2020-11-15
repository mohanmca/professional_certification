/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;

import java.util.Arrays;
import java.util.List;

public class KdTreeTest {
    public static void main(String[] args) {
        KdTree kdTree = new KdTree();

        List<Point2D> points = Arrays
                .asList(of(0.7, 0.2),
                        of(0.5, 0.4),
                        of(0.2, 0.3),
                        of(0.4, 0.7),
                        of(0.9, 0.6));
        points.forEach(kdTree::insert);
        Point2D queryPoint = of(0.301, 0.405);
        Point2D expectedNearest = of(0.2, 0.3);
        assertNearest(kdTree, queryPoint, expectedNearest);

        queryPoint = of(0.637, 0.936);
        expectedNearest = of(0.4, 0.7);
        assertNearest(kdTree, queryPoint, expectedNearest);

        queryPoint = of(0.999, 0.911);
        expectedNearest = of(0.9, 0.6);
        assertNearest(kdTree, queryPoint, expectedNearest);


        points = Arrays
                .asList(of(0.5, 0.75),
                        of(0.875, 0.0),
                        of(0.625, 0.125),
                        of(0.375, 0.875),
                        of(0.125, 0.625));


        points.forEach(kdTree::insert);
        System.out.println(kdTree.contains(of(0.125, 0.625)));

        kdTree = new KdTree();
        points = Arrays
                .asList(of(0.0625, 0.0),
                        of(0.375, 0.25),
                        of(0.3125, 0.5625),
                        of(0.5625, 0.125),
                        of(0.625, 0.1875),
                        of(0.125, 0.6875),
                        of(0.8125, 0.875),
                        of(0.0, 0.8125),
                        of(0.25, 1.0),
                        of(0.6875, 0.375));

        points.forEach(kdTree::insert);
        System.out.println(kdTree.contains(of(0.6875, 0.375)));
    }

    private static Point2D of(double x, double y) {
        return new Point2D(x, y);
    }

    private static void assertNearest(KdTree kdTree, Point2D queryPoint, Point2D expectedNearest) {
        Point2D nearest = kdTree.nearest(queryPoint);
        System.out.println("Nearest point  for - " + queryPoint.toString()
                                   + ", supposed to be " + expectedNearest.toString()
                                   + ",  but was " + nearest.toString()
                                   + ", Test result " + nearest.equals(expectedNearest));

    }

    private static void assertExpression(String message, boolean expression) {
        if (!expression)
            throw new IllegalArgumentException(message);
    }


}
