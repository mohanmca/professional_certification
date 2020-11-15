/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * Todo - 1
 * 1. Method to partition whole rectangle into two
 * 2. Method to find right rectangle, given left rectangle
 * 3. Method to find right rectangle
 */
public class KdTree {

    private static final RectHV CONTAINER = new RectHV(0, 0, 1, 1);

    private Node root = null;

    public KdTree() {

    }

    /**
     * A  0.0 0.75
     * B  0.25 1.0
     * C  0.5 0.5
     * D  1.0 1.0
     * E  1.0 0.25
     * F  0.25 1.0
     * G  0.25 1.0
     * H  0.75 0.75
     * I  0.25 1.0
     * J  1.0 1.0
     * - query point                   = (0.0, 0.0)
     * - student   nearest()           = (1.0, 0.25)
     * - reference nearest()           = (0.5, 0.5)
     * - student   distanceSquaredTo() = 1.0625
     * - reference distanceSquaredTo() = 0.5
     * <p>
     * A  0.5 0.75
     * B  1.0 1.0
     * C  0.75 0.75
     * D  0.25 0.75
     * E  1.0 0.75
     * F  1.0 0.0
     * G  1.0 0.25
     * H  0.5 0.5
     * I  0.25 1.0
     * J  0.0 0.5
     * - query point                   = (0.0, 0.0)
     * - student   nearest()           = (1.0, 0.0)
     * - reference nearest()           = (0.0, 0.5)
     * - student   distanceSquaredTo() = 1
     * - reference distanceSquaredTo() = 0.25     * @param args
     */

    public static void main(String[] args) {
        KdTree kdTree = new KdTree();


        List<Point2D> points = Arrays
                .asList(of(0.2, 0.75), of(0.25, 1.0),
                        of(0.5, 0.5), of(1.0, 1.0),
                        of(1.0, 0.25), of(0.75, 0.75));


        points.forEach(kdTree::insert);
        System.out.println(kdTree.nearest(of(0.0, 0.0)).equals(of(0.5, 0.5)));
        assertExpression("Size of the kdTree", kdTree.size() == 6);

        kdTree = new KdTree();

        points = Arrays
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
        points.forEach(kdTree::insert);

        Iterable<Point2D> availablePoints = kdTree.range(new RectHV(0.25, 0.25, 0.75, 0.75));
        for (Point2D point : availablePoints) {
            System.out.println("Available points :: " + point.toString());
        }

        kdTree = new KdTree();
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
        points.forEach(kdTree::insert);
        System.out.println(kdTree.nearest(of(0.625, 1.0)));
        System.out.println(
                "Nearest point : " + kdTree.nearest(of(0.625, 1.0)).equals(of(0.625, 0.75)));


        points = Arrays
                .asList(of(0.0, 0.5), of(1.0, 0.5), of(0.25, 0.75), of(0.75, 0.0),
                        of(0.0, 0.25), of(0.25, 0.25), of(0.25, 0.25), of(0.75, 0.0),
                        of(0.25, 0.5), of(0.5, 1.0));

        kdTree = new KdTree();
        points.forEach(kdTree::insert);
        assertExpression("Size of the kdTree", kdTree.size() == 8);
        System.out.println(kdTree.nearest(of(1.0, 0.25)).equals(of(1.0, 0.5)));
        Point2D queryPoint = of(0.1, 0.5);
        System.out.println(kdTree.nearest(of(1.0, 0.25)).distanceSquaredTo(queryPoint));
        points = Arrays
                .asList(of(0.05, 0.05), of(0.1, 0.1), of(0.2, 0.2), of(0.3, 0.3),
                        of(0.4, 0.4), of(0.5, 0.5), of(0.6, 0.6), of(0.7, 0.7),
                        of(0.8, 0.8), of(0.9, 0.9), of(1.0, 1.0));

        kdTree = new KdTree();
        points.forEach(kdTree::insert);
        assertExpression("Size of the kdTree", kdTree.size() == 11);
        System.out.println(kdTree.nearest(of(1.0, 0.0)));
        System.out.println(kdTree.nearest(of(0.06, 0.06)));
    }

    private static Point2D of(double x, double y) {
        return new Point2D(x, y);
    }

    private static void assertExpression(String message, boolean expression) {
        if (!expression)
            throw new IllegalArgumentException(message);
    }

    public boolean contains(Point2D point2D) {
        return contains(root, point2D);
    }


    private boolean contains(Node node, Point2D point2d) {
        if (point2d == null) throw new IllegalArgumentException("Null argument while contains!");

        if (node == null)
            return false;
        if (node.point.equals(point2d))
            return true;

        if (node.compareToPoint(point2d) < 0) {
            return contains(node.lb, point2d);
        }
        return contains(node.rt, point2d);

    }


    public void insert(Point2D point) {
        if (point == null) throw new IllegalArgumentException("Null argument while insert!");
        // System.out.printf("\nBefore inserting Point %s", point.toString());
        if (root == null) {
            Node node = new Node(point, CONTAINER, 1);
            root = node;
            return;
        }
        root.insert(point);
        // System.out.printf("\nAfter inserting Point %s - Current state of root \n[ %s \n]", point.toString(), root.toString());
    }


    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Null argument while range!");
        return range(rect, root);
    }

    private List<Point2D> range(RectHV queryRect, Node node) {
        if (node == null || !queryRect.intersects(node.rect))
            return Collections.emptyList();
        // System.out.println(queryRect.toString() + " - " + node.toString()  + " - " + depth);
        ArrayList<Point2D> listOfPoints = new ArrayList<Point2D>();
        if (queryRect.contains(node.point)) {
            listOfPoints.add(node.point);
        }
        List<Point2D> subPoints = range(queryRect, node.lb);
        listOfPoints.addAll(subPoints);
        subPoints = range(queryRect, node.rt);
        listOfPoints.addAll(subPoints);
        return listOfPoints;
    }

    public Point2D nearest(Point2D point) {
        if (point == null)
            throw new IllegalArgumentException(
                    "Should try to search into root and point shouldn't be null!");
        if (root == null)
            return null;

        return nearest(root, point, null, Double.MAX_VALUE);
    }

    private Point2D nearest(Node node, Point2D queryPoint, Point2D nearest,
                            double nearestDistanceSquared) {
        if (node == null) return nearest;
        double dsFromNode = node.point.distanceSquaredTo(queryPoint);
        if (nearest == null || dsFromNode < nearestDistanceSquared) {
            nearest = node.point;
            nearestDistanceSquared = dsFromNode;
        }

        double leftNearest = node.lb != null ? node.lb.rect.distanceSquaredTo(queryPoint) :
                             Double.MAX_VALUE;
        double rightNearest = node.rt != null ? node.rt.rect.distanceSquaredTo(queryPoint) :
                              Double.MAX_VALUE;

        // Here line is split equally on both the sides
        if (leftNearest < nearestDistanceSquared && Math.min(leftNearest, rightNearest) == leftNearest) {
            nearest = nearest(node.lb, queryPoint, nearest, nearestDistanceSquared);
            nearestDistanceSquared = queryPoint.distanceSquaredTo(nearest);
            if (rightNearest < nearestDistanceSquared)
                return nearest(node.rt, queryPoint, nearest, nearestDistanceSquared);
            else
                return nearest;
        }
        else if (rightNearest < nearestDistanceSquared) {
            nearest = nearest(node.rt, queryPoint, nearest, nearestDistanceSquared);
            nearestDistanceSquared = queryPoint.distanceSquaredTo(nearest);
            if (leftNearest < nearestDistanceSquared)
                return nearest(node.lb, queryPoint, nearest, nearestDistanceSquared);
            else
                return nearest;
        }

        return nearest;
    }

    public void draw() {
        draw(root);
    }


    private void draw(Node node) {
        if (root == null)
            return;
        drawPoint(node.point);
        drawLine(node);
        if (node.lb != null)
            draw(node.lb);
        if (node.rt != null)
            draw(node.rt);


    }

    private void drawLine(Node node) {
        StdDraw.setPenRadius(0.001);
        if (node.isHorizontalLine()) {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
        }
        else {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
        }
    }


    private void drawPoint(Point2D key) {
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.point(key.x(), key.y());
    }

    public String toString() {
        return "KdTree{" +
                "root=" + (root != null ? root.toString() : '-') +
                '}';
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        if (isEmpty())
            return 0;
        return root.size;
    }

    private int size(Node node) {
        if (node != null) {
            return root.size;
        }
        return 0;
    }

    private static class Node {
        private final Point2D point;      // the point
        private final RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private final int depth;
        private int size;
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        Node(Point2D point, RectHV rect, int depth) {
            this.point = point;
            this.rect = rect;
            this.depth = depth;
            this.size = 1;
        }

        private boolean isHorizontalLine() {
            return depth % 2 == 0;
        }

        private RectHV createLeftBottom() {
            if (isHorizontalLine()) {
                // horizontal split - divide the space using y co-ordinate, and retain x as it is
                return new RectHV(rect.xmin(), rect.ymin(),
                                  rect.xmax(),
                                  point.y());
            }
            // Vertical split - divide the space using x co-ordinate, and retain y as it is
            return new RectHV(rect.xmin(), rect.ymin(),
                              point.x(),
                              rect.ymax());
        }

        private RectHV createTopRight() {

            // By now depth is already increased hence logic is swapped
            if (isHorizontalLine()) {
                // horizontal split - divide the space using x co-ordinate, and retain y as it is
                return new RectHV(rect.xmin(), point.y(), rect.xmax(), rect.ymax());
            }
            // vertical split - divide the space using y co-ordinate, and retain x as it is
            return new RectHV(point.x(), rect.ymin(), rect.xmax(), rect.ymax());
        }


        private Node createLeft(Point2D newPoint) {
            return new Node(newPoint, createLeftBottom(), depth + 1);
        }

        private Node createRight(Point2D newPoint) {
            return new Node(newPoint, createTopRight(), depth + 1);
        }

        private void insert(Point2D newPoint) {
            // odd -- horizontal split
            if (point.equals(newPoint)) {
                return;
            }
            double diffToPoint = compareToPoint(newPoint);
            if (diffToPoint < 0) {
                if (lb == null) {
                    lb = createLeft(newPoint);
                    size = size + 1;
                }
                else {
                    lb.insert(newPoint);
                }
            }
            else if (diffToPoint >= 0) {
                if (rt == null) {
                    rt = createRight(newPoint);
                    size = size + 1;
                }
                else {
                    rt.insert(newPoint);
                }
            }
            this.size = 1 + (lb == null ? 0 : lb.size) + (rt == null ? 0 : rt.size);
        }

        public double compareToPoint(Point2D queryPoint) {
            if (isHorizontalLine()) {
                return Point2D.Y_ORDER.compare(queryPoint, point);
            }
            return Point2D.X_ORDER.compare(queryPoint, point);
        }

        public String toString() {
            return "\nNode{" +
                    "key=" + (point != null ? point.toString() : '-') +
                    ", rect=" + (rect != null ? rect.toString() : '-')
                    + toLbString(1)
                    + toRtString(1) +
                    ", depth = " + 1 +
                    " }";
        }

        public String toString(int levelDepth) {
            String tab = new String(new char[levelDepth]).replace("\0", "\t");
            return tab + "\nNode {\n" +
                    "key=" + (point != null ? point.toString() : '-') + ", depth = "
                    + levelDepth +
                    ", rect=" + (rect != null ? rect.toString() : '-')
                    + toLbString(levelDepth + 1)
                    + toRtString(levelDepth + 1)
                    + "}";
        }

        public String toLbString(int levelDepth) {
            if (lb == null) return ", lb = - ";
            return "\nlb=" + lb.toString(levelDepth + 1);
        }

        public String toRtString(int levelDepth) {
            if (rt == null) return ", rt = - ";
            return "\nrt =" + rt.toString(levelDepth + 1);
        }
    }
}

