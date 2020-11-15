import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

/**
 * KdTree uses a 2d-tree to implement the same API (but replace PointSET with KdTree). 
 * A 2d-tree is a generalization of a BST to two-dimensional keys. 
 * The idea is to build a BST with points in the nodes, using the 
 * x- and y-coordinates of the points as keys in strictly alternating sequence.
 *
 * @author andrew
 *
 */
public class RefKdTree {
    private Node root;

    public RefKdTree() {}

    // is the set empty? 
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set 
    public int size() {
        return size(root);
    }

    private int size(Node n) {
        if (null == n) return 0;
        return n.size();
    }

    public void insert(Point2D point) {
        if (point == null) throw new IllegalArgumentException("argument to insert() is null");
        // add the point to the set (if it is not already in the set)
        root = insert(root, point, null);
    }

    private Node insert(Node x, Point2D point, Node parentNode)
    {
        if (x == null) return new Node(point, 1, parentNode);
        if (x.p.equals(point)) return x;
        double diff = x.comparePoint(point);
        if      (diff < 0) x.leftOrbottom = insert(x.leftOrbottom, point, x);
        else if (diff >= 0) x.rightOrtop = insert(x.rightOrtop, point, x);
        x.size = size(x.leftOrbottom) + size(x.rightOrtop) + 1;
        return x;
    }

    public boolean contains(Point2D key) {
        // does the set contain point p? 
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    private RectHV get(Point2D key) {
        return get(root, key);
    }

    private RectHV get(Node x, Point2D key) {
        if (x == null) return null;
        if (x.p.equals(key)) return x.rect;
        double diff = x.comparePoint(key);
        if (diff < 0) return get(x.leftOrbottom, key);
        else return get(x.rightOrtop, key);
    }

    public void draw() {
        // draw all points to standard draw 
        draw(root, null);
    }

    private void draw(Node x, Node parentNode) {
        if (null == x) return;
        draw(x.leftOrbottom, x);
        draw(x.rightOrtop, x);
        x.draw(parentNode);
    }

    /**
     * Range search. 
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("argument to range() is null");
        Queue<Point2D> queue = new Queue<Point2D>();
        keys(root, queue, rect);
        return queue;
    }

    private void keys(Node item, Queue<Point2D> queue, RectHV rect) {
        if (item == null) return;
        if (!item.rect.intersects(rect)) return;
        keys(item.leftOrbottom, queue, rect);
        keys(item.rightOrtop, queue, rect);
        if (rect.contains(item.p)) queue.enqueue(item.p);
    }

    /**
     * Nearest neighbor search. 
     *
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        if (null == root) return null; // exception?
        // set initial node
        MinPointCache mp = new MinPointCache(root.p, root.p.distanceSquaredTo(p));
        mp = nearest(p, root, mp);
        return mp.currentMin;
    }

    private MinPointCache nearest(Point2D p, Node x, MinPointCache minPoint) {
        if (null == x) return minPoint;

        // pruning
        double pruneDist = x.rect.distanceSquaredTo(p);
        if (minPoint.currentMinDist < pruneDist)
            return minPoint;

        // update currentMin if necessary
        if (!minPoint.currentMin.equals(x.p)) {
            double currentDist = x.p.distanceSquaredTo(p);
            if (currentDist < minPoint.currentMinDist) {
                minPoint.currentMin = x.p;
                minPoint.currentMinDist = currentDist;
            }
        }

        // get preferred search route: only compute left diff is there's a right to compare against
        Node first = x.leftOrbottom;
        Node second = x.rightOrtop;
        if (null != first  && null != second &&
                second.p.distanceSquaredTo(p) < first.p.distanceSquaredTo(p)) {
            first = x.rightOrtop;
            second = x.leftOrbottom;
        }

        minPoint = nearest(p, first, minPoint);
        minPoint = nearest(p, second, minPoint);

        return minPoint;
    }

    /**
     * ==This is strictly an optimization==
     * Not needed for sets <1000000.
     */
    private static class MinPointCache {
        Point2D currentMin;
        double currentMinDist;
        MinPointCache(Point2D cm, double cmd) {
            currentMin = cm;
            currentMinDist = cmd;
        }
    }

    public static void main(String[] args) {
        // @see KdTreeTest
    }

    /**=========== Private ===============**/

    // The Node class for this 2D Tree
    private static class Node implements Comparable<Node> {
        private Point2D p; // the point
        private RectHV rect; // the axis-aligned rectangle corresponding to this node
        private Node leftOrbottom; // the left/bottom subtree
        private Node rightOrtop; // the right/top subtree
        private int size;
        private boolean isVertical;

        public Node(Point2D point, int sz, Node parent) {
            p = point;
            size = sz;
            isVertical = (null != parent) ? !parent.isVertical : true;
            rect = createEnclosingRect(point, parent);
            // note I would have stored "parent" instead of dragging along.
            // this was strictly a memory optimization.
        }

        public int size() {
            return size; //number of points in the set 
        }

        // compare a node to a point
        public double comparePoint(Point2D targetPoint) {
            return (isVertical) ?
                   targetPoint.x() - p.x():
                   targetPoint.y() - p.y();
        }

        // compare a node to a node
        public int compareTo(Node that) {
            double diff = comparePoint(that.p);
            if (diff > 0) return 1;
            else if (diff < 0) return -1;
            else return 0;
        }

        public void draw(Node parentNode) {
            // draw the point 
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            StdDraw.point(p.x(), p.y());
            StdDraw.setPenRadius();

            // draw the line
            if (null == parentNode) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(p.x(), 0, p.x(), 1);
                return;
            }
            RectHV rect = Node.generateRectHV(p, parentNode, false);
            if (parentNode.isVertical) {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(rect.xmin(), rect.ymax(), rect.xmax(), rect.ymax());
            } else {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(rect.xmax(), rect.ymin(), rect.xmax(), rect.ymax());
            }
        }

        private RectHV createEnclosingRect(Point2D point, Node parentNode) {
            return Node.generateRectHV(point, parentNode, true);
        }

        /**
         *
         * @param point
         * @param parent
         * @param enclosing whether it should be a node-type enclosing rect, or a boundary-type line rect
         * @return
         */
        private static RectHV generateRectHV(Point2D point, Node parent, boolean enclosing) {
            if (null == parent) return new RectHV(0, 0, 1, 1);
            double xmin, xmax, ymin, ymax;
            Point2D parentPoint = parent.p;
            if (parent.isVertical) {
                ymin = parent.rect.ymin();
                ymax = (enclosing) ? parent.rect.ymax() : point.y();
                if (point.x() < parent.p.x())  {
                    xmin = parent.rect.xmin();
                    xmax = parentPoint.x();
                } else {
                    xmin = parentPoint.x();
                    xmax = parent.rect.xmax();
                }
            } else {
                xmin = parent.rect.xmin();
                xmax = (enclosing) ? parent.rect.xmax() : point.x();
                if (point.y() < parent.p.y()) {
                    ymin = parent.rect.ymin();
                    ymax = parentPoint.y();
                } else {
                    ymin = parentPoint.y();
                    ymax = parent.rect.ymax();
                }
            }
            return new RectHV(xmin, ymin, xmax, ymax);
        }

    }

}