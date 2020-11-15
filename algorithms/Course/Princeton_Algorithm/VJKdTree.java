/* ***************************
 *  Name:
 *  Date:
 *  Description:
 ************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class VJKdTree {

    private int size;

    private class Node {
        private int index;
        private final Point2D value;
        private final boolean horizontal;
        private double xMin;
        private double xMax;
        private double yMin;
        private double yMax;

        private Node parent;
        private Node right;
        private Node left;

        public Node(Point2D p, boolean orientationHorizontal) {
            index = size;
            this.value = p;
            this.horizontal = orientationHorizontal;
            size++;
        }

        private void setParent(Node parent) {
            this.parent = parent;
            if (horizontal) {
                if (this.parent.right == this) {
                    setQuardinates(parent.value.x(), parent.yMin, parent.xMax, parent.yMax);
                }
                else {
                    setQuardinates(parent.xMin, parent.yMin, parent.value.x(), parent.yMax);
                }
            }
            else {
                if (this.parent.right == this) {
                    setQuardinates(parent.xMin, parent.value.y(), parent.xMax, parent.yMax);
                }
                else {
                    setQuardinates(parent.xMin, parent.yMin, parent.xMax, parent.value.y());
                }
            }
        }

        private void setQuardinates(double xMin, double yMin, double xMax, double yMax) {
            this.xMax = xMax;
            this.yMax = yMax;
            this.xMin = xMin;
            this.yMin = yMin;
        }

        public void add(Point2D p) {
            int compVal = 0;
            if (!horizontal) {
                compVal = Point2D.X_ORDER.compare(p, this.value);
            }
            else {
                compVal = Point2D.Y_ORDER.compare(p, this.value);
            }
            if (compVal == 0 && p.equals(this.value)) {
                return;
            }
            if (compVal >= 0) {
                if (right == null) {
                    right = new Node(p, !this.horizontal);
                    right.setParent(this);
                }
                else {
                    right.add(p);
                }
            }
            else {
                if (left == null) {
                    left = new Node(p, !this.horizontal);
                    left.setParent(this);
                }
                else {
                    left.add(p);
                }
            }
        }

        public Node find(Point2D p) {
            int compVal = !horizontal ? Point2D.X_ORDER.compare(p, this.value) :
                          Point2D.Y_ORDER.compare(p, this.value);
            if (compVal == 0 && this.value.equals(p)) {
                return this;
            }
            if (compVal >= 0) {
                if (right == null) {
                    return null;
                }
                else {
                    return right.find(p);
                }
            }
            else {
                if (left == null) {
                    return null;
                }
                else {
                    return left.find(p);
                }
            }
        }

        public void draw() {
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.setPenRadius(0.001);
            if (this.horizontal) {
                StdDraw.line(xMin, value.y(), xMax, value.y());
                //StdDraw.text(value.x() + 0.01, value.y() + 0.01, "" + ((char) (index + 65)));
            }
            else {
                StdDraw.line(value.x(), yMin, value.x(), yMax);
                //StdDraw.text(value.x() + 0.01, value.y() + 0.01, "" + ((char) (index + 65)));
            }
            StdDraw.setPenColor(Color.RED);
            StdDraw.setPenRadius(0.009);
            StdDraw.point(value.x(), value.y());
            //   StdDraw.text(value.x(), value.y(),value.toString());
            if (right != null) {
                right.draw();
            }
            if (left != null) {
                left.draw();
            }
        }
    }

    private Node root = null;


    public VJKdTree() {
        size = 0;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            root = new Node(p, false);
            root.setQuardinates(0, 0, 1, 1);
        }
        else {
            root.add(p);
        }

    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            return false;
        }
        Node node = root.find(p);
        return node != null;
    }

    public void draw() {
        root.draw();
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        List<Point2D> list = new ArrayList<Point2D>();
        if (root != null) {
            find(list, root, rect);
        }

        return list;
    }

    private void find(List<Point2D> point2DS,
                      Node node, RectHV rect) {
        if (rect.contains(node.value)) {
            point2DS.add(node.value);
        }
        if (node.right != null && rect.intersects(
                new RectHV(node.right.xMin, node.right.yMin, node.right.xMax, node.right.yMax))) {
            find(point2DS, node.right, rect);
        }
        if (node.left != null && rect.intersects(
                new RectHV(node.left.xMin, node.left.yMin, node.left.xMax, node.left.yMax))) {
            find(point2DS, node.left, rect);
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            return null;
        }
        return findNearest(Double.MAX_VALUE, null, root, p);
    }

    private Point2D findNearest(double distanceSqauredToNearest, Point2D nearest, Node node, Point2D queryPoint) {
        double tempDist = node.value.distanceTo(queryPoint);
        if (tempDist < distanceSqauredToNearest) {
            distanceSqauredToNearest = tempDist;
            nearest = node.value;
        }
        if (node.right == null && node.left == null) {
            return nearest;
        }
        double distToRight = getDistance(node.right, queryPoint);
        double distToleft = getDistance(node.left, queryPoint);
        Node fPoint = distToRight == 0 ? node.right : distToleft == 0 ? node.left :
                                                      distToleft < distToRight ? node.left :
                                                      node.right;
        Node sPoint = node.right != null && fPoint == node.right ? node.left : node.right;

        double fDist = fPoint == null ? Double.MAX_VALUE : fPoint == node.right ? distToRight :
                                                           fPoint == node.left ? distToleft :
                                                           Double.MAX_VALUE;
        double sDist = sPoint == null ? Double.MAX_VALUE : sPoint == node.right ? distToRight :
                                                           sPoint == node.left ? distToleft :
                                                           Double.MAX_VALUE;

        if (fDist < distanceSqauredToNearest) {
            Point2D pointR = findNearest(distanceSqauredToNearest, nearest, fPoint, queryPoint);
            double dist = pointR.distanceTo(queryPoint);
            if (dist < distanceSqauredToNearest) {
                nearest = pointR;
                distanceSqauredToNearest = dist;
            }
        }
        if (sDist < distanceSqauredToNearest) {
            Point2D pointR = findNearest(distanceSqauredToNearest, nearest, sPoint, queryPoint);
            double dist = pointR.distanceTo(queryPoint);
            if (dist < distanceSqauredToNearest) {
                nearest = pointR;
            }
        }
        return nearest;
    }

    private double getDistance(Node node, Point2D p) {
        if (node == null) {
            return Double.MAX_VALUE;
        }
        else {
            return new RectHV(node.xMin, node.yMin, node.xMax, node.yMax).distanceTo(p);
        }
    }

    public static void main(String[] args) {
        VJKdTree tree = new VJKdTree();
        tree.insert(new Point2D(0.75, 0.25));
        tree.insert(new Point2D(1.0, 0.0));
        tree.insert(new Point2D(0.25, 0.0));
        tree.insert(new Point2D(0.0, 0.0));
        tree.insert(new Point2D(0.75, 1.0));
        tree.insert(new Point2D(0.25, 0.75));
        tree.insert(new Point2D(0.5, 0.0));
        tree.insert(new Point2D(0.5, 0.5));
        tree.insert(new Point2D(1.0, 0.25));
        tree.insert(new Point2D(0.25, 0.25));
        StdOut.println(tree.nearest(new Point2D(0.0, 0.1)));
    }
}