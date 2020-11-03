/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class PointSET {
    private final SET<Point2D> pointSets;

    public PointSET() {
        pointSets = new SET<Point2D>();
    }

    public static void main(String[] args) {
        PointSET points = new PointSET();
        Point2D contains = new Point2D(0.7, 0.8);
        points.insert(contains);
        contains = new Point2D(0.6, 0.5);
        points.insert(contains);

        Iterator<Point2D> iter = points.range(new RectHV(0.4, 0.3, 0.8, 0.6)).iterator();
        System.out.println(iter.hasNext());
        System.out.println(iter.next());
        points.insert(new Point2D(0.44, 0.33));
        System.out.println(points.nearest(new Point2D(0.42, 0.31)));
    }

    public void insert(Point2D key) {
        if (key == null) throw new IllegalArgumentException("Null argument while inserting!");
        pointSets.add(key);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Null argument while range!");
        Point2D lowerLeft = new Point2D(rect.xmin(), rect.ymin());
        Point2D topRight = new Point2D(rect.xmax(), rect.ymax());
        return new Iterable<Point2D>() {
            public Iterator<Point2D> iterator() {
                return new Iterator<Point2D>() {
                    boolean isNextPoint = false;

                    public boolean hasNext() {
                        Point2D nextPoint = pointSets.ceiling(lowerLeft);
                        isNextPoint = (nextPoint.compareTo(topRight) <= 0);
                        return isNextPoint;
                    }

                    public Point2D next() {
                        if (isNextPoint) {
                            isNextPoint = false;
                            return pointSets.ceiling(lowerLeft);
                        }
                        else
                            throw new NoSuchElementException("No such element");
                    }
                };
            }
        };

    }

    public Point2D nearest(Point2D query) {
        if (query == null) throw new IllegalArgumentException("Null argument while nearest!");

        Point2D pointAbove = null;
        try {
            pointAbove = pointSets.ceiling(query);
        }
        catch (NoSuchElementException e) {
            //e.printStackTrace();
        }
        Point2D pointBelow = null;
        try {
            pointBelow = pointSets.floor(query);
        }
        catch (NoSuchElementException e) {
            //e.printStackTrace();
        }
        if (pointAbove == null && pointBelow != null)
            return pointBelow;
        if (pointAbove != null && pointBelow == null)
            return pointAbove;

        if (query.distanceTo(pointAbove) > query.distanceTo(pointBelow))
            return pointBelow;
        return pointAbove;
    }

    public void draw() {
        for (Point2D point : pointSets) {
            StdDraw.point(point.x(), point.y());
        }
    }

    public boolean isEmpty() {
        return pointSets.isEmpty();
    }

    public int size() {
        return pointSets.size();
    }

    public boolean contains(Point2D key) {
        if (key == null) throw new IllegalArgumentException("Null argument contains nearest!");

        return pointSets.contains(key);
    }


}
