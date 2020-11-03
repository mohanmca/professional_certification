/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * Todo - 1
 * 1. Method to partition whole rectangle into two
 * 2. Method to find right rectangle, given left rectangle
 * 3. Method to find right rectangle
 */
public class KdTree {

    private Node root = null;


    public KdTree() {

    }

    private static void assertExpression(String message, boolean expression) {
        if (!expression)
            throw new IllegalArgumentException(message);
    }


    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        assertExpression("1. Size should have been zero!", kdTree.size() == 0);
        Point2D point2D = new Point2D(0.5, 0.5);
        kdTree.insert(point2D);
        point2D = new Point2D(0.4, 0.4);
        kdTree.insert(point2D);
        point2D = new Point2D(0.3, 0.3);
        kdTree.insert(point2D);
        point2D = new Point2D(0.36, 0.36);
        kdTree.insert(point2D);
        point2D = new Point2D(0.45, 0.45);
        kdTree.insert(point2D);
        assertExpression("2. Size should be 5!", kdTree.size() == 5);

        point2D = new Point2D(0.42, 0.43);
        kdTree.insert(point2D);

        point2D = new Point2D(0.6, 0.4);
        kdTree.insert(point2D);
        assertExpression("3. Size should be 5!", kdTree.size() == 7);
        point2D = new Point2D(0.7, 0.3);
        kdTree.insert(point2D);
        point2D = new Point2D(0.7, 0.8);
        kdTree.insert(point2D);

        point2D = new Point2D(0.3, 0.2);
        kdTree.insert(point2D);
        point2D = new Point2D(0.2, 0.2);
        kdTree.insert(point2D);

        assertExpression("2. Point should be there! = " + kdTree.toString(),
                         kdTree.contains(point2D));
        assertExpression("3. Point shouldn't exist!", !kdTree.contains(new Point2D(0.9, 0.9)));
        assertExpression("4. Size should be 4!", kdTree.size() == 11);

        Node ceiling = kdTree.getCeilingOf(null, new Point2D(0.42, 0.31));
        System.out.println(ceiling.key);

        Iterator<Point2D> iter = kdTree.range(new RectHV(0.4, 0.3, 0.8, 0.6)).iterator();
        System.out.println(iter.hasNext());
        System.out.println(iter.next());
        kdTree.insert(new Point2D(0.44, 0.33));
        System.out.println(kdTree.nearest(new Point2D(0.42, 0.31)));
    }

    public boolean contains(Point2D point2D) {
        return contains(root, point2D);
    }


    private boolean contains(Node node, Point2D point2D) {
        if (node.key.equals(point2D))
            return true;
        else if (node.rect.contains(point2D))
            return contains(node.lb, point2D);
        else if (node.rt != null)
            contains(node.rt, point2D);
        return false;
    }

    public Point2D nearest(Point2D point2D) {
        Node ceiling = getCeilingOf(null, point2D);
        Node floor = getFloorOf(null, point2D);
        return shortestPoint(point2D, ceiling.key, floor.key);
    }


    private Point2D shortestPoint(Point2D basePoint, Point2D ceiling, Point2D floor) {
        double distanceSqauredToCeil = ceiling.distanceSquaredTo(basePoint);
        double distanceSqauredToFloor = floor.distanceSquaredTo(basePoint);
        if (distanceSqauredToCeil < distanceSqauredToFloor)
            return ceiling;
        return floor;
    }

    private Node shortestNode(Point2D basePoint, Node ceilingNode, Node floorNode) {
        double distanceSqauredToCeil = ceilingNode.key.distanceSquaredTo(basePoint);
        double distanceSqauredToFloor = floorNode.key.distanceSquaredTo(basePoint);
        if (distanceSqauredToCeil < distanceSqauredToFloor)
            return ceilingNode;
        return floorNode;
    }

    public void insert(Point2D point) {
        if (root == null) {
            root = new Node(point);
            return;
        }
        insert(root, point, 1);
        System.out.printf("\nAfter inserting Point %s - Current state of root \n[ %s \n]",
                          point.toString(), root.toString());
    }

    private void insert(Node node, Point2D point, int depth) {
        if (node == null || point == null)
            throw new IllegalArgumentException(
                    "Should try to search into root and point shouldn't be null!");
        if (node.key.equals(point)) {
            node.key = point;
        }
        if (node.rect.contains(point)) {
            if (node.lb != null) {
                insert(node.lb, point, ++depth);
            }
            else {
                node.lb = createNewNode(node, point, ++depth);
            }
        }
        else {
            if (node.rt != null) {
                insert(node.rt, point, ++depth);
            }
            else {
                ++depth;
                RectHV rectHV = node.findRightRectangle(depth);
                node.rt = createNewNode(rectHV, point, depth);
            }
        }
    }

    private Node createNewNode(RectHV rectHV, Point2D point, int depth) {
        if (depth % 2
                == 0) { //vertical partitioning - height adjusted to current point-y, width retains to existing rectangle (X<-R) (Y<-P)
            RectHV newPartition = partitionV(rectHV, point)[0];
            return Node.create(point, newPartition, depth);
        }
        else {  //horizontal partitioning - width adjusted to current point-x, height retains to existing rectangle (X<-P)(Y<-R)
            RectHV newPartition = partitionH(rectHV, point)[0];
            return Node.create(point, newPartition, depth);
        }
    }

    private Node createNewNode(Node node, Point2D point, int depth) {
        if (depth % 2
                == 0) { //vertical partitioning - height adjusted to current point-y, width retains to existing rectangle (X<-R) (Y<-P)
            RectHV newPartition = partitionV(node.rect, point)[0];
            return Node.create(point, newPartition, depth);
        }
        else {  //horizontal partitioning - width adjusted to current point-x, height retains to existing rectangle (X<-P)(Y<-R)
            RectHV newPartition = partitionH(node.rect, point)[0];
            return Node.create(point, newPartition, depth);
        }
    }

    private RectHV[] partitionV(RectHV rectHV, Point2D point2D) {
        return new RectHV[] {
                new RectHV(rectHV.xmin(), rectHV.ymin(), rectHV.xmax(), point2D.y())
                //,new RectHV(rectHV.xmin(), point2D.y(), rectHV.xmax(), 1),
        };
    }

    private RectHV[] partitionH(RectHV rectHV, Point2D point2D) {
        return new RectHV[] {
                new RectHV(rectHV.xmin(), rectHV.ymin(), point2D.x(), rectHV.ymax())
                //,new RectHV(point2D.x(), rectHV.ymin(), rectHV.xmax(), rectHV.ymax()),
        };
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Null argument while range!");
        return new Iterable<Point2D>() {
            public Iterator<Point2D> iterator() {
                return new Iterator<Point2D>() {
                    Point2D lowerLeft = new Point2D(rect.xmin(), rect.ymin());
                    Point2D topRight = new Point2D(rect.xmax(), rect.ymax());
                    Node pointerNode = null;
                    boolean isNextPoint = false;
                    boolean isXAxis = true;

                    public boolean hasNext() {
                        pointerNode = getCeilingOf(pointerNode, lowerLeft);
                        isNextPoint = (pointerNode.key.compareTo(topRight) <= 0);
                        return isNextPoint;
                    }


                    public Point2D next() {
                        if (isNextPoint) {
                            isNextPoint = false;
                            lowerLeft = pointerNode.key;
                            return pointerNode.key;
                        }
                        else
                            throw new NoSuchElementException("No such element");
                    }
                };
            }
        };
    }


    private Node search(Node node, Point2D key) {
        if (node == null)
            throw new IllegalArgumentException("Should try to search into root!");
        else if (node.key.equals(key)) {
            return node;
        }
        else {
            if (node.rect.contains(key)) {
                if (node.key.y() < key.y()) {
                    return search(node.rt, key);
                }
                else {
                    return search(node.lb, key);
                }
            }
            else {
                //hroizontal
                if (node.key.x() < key.x()) {
                    return search(node.lb, key);
                }
                else {
                    return search(node.rt, key);
                }
            }
        }
    }

    private Node getCeilingOf(Node pointerNode, Point2D point) {
        if (pointerNode == null)
            pointerNode = root;
        if (pointerNode.key.x() == point.x() && pointerNode.key.y() == point.y())
            return pointerNode;
        while (pointerNode.key.x() >= point.x()
                && pointerNode.key.y() >= point.y()
                && pointerNode.lb != null
                && pointerNode.lb.key.x() >= point.x()
                && pointerNode.lb.key.y() >= point.y()
        ) {
            pointerNode = pointerNode.lb;
        }
        Node leftMax = pointerNode;
        if (leftMax.rt == null) {
            return leftMax;
        }

        pointerNode = leftMax.rt;
        Node rightMax = getCeilingOf(pointerNode, point);
        return shortestNode(point, leftMax, rightMax);

    }

    private Node getFloorOf(Node pointerNode, Point2D point) {
        if (pointerNode == null)
            pointerNode = root;
        if (pointerNode.key.x() == point.x() && pointerNode.key.y() == point.y())
            return pointerNode;
        while (pointerNode.key.x() <= point.x()
                && pointerNode.key.y() <= point.y()
                && pointerNode.rt != null
                && pointerNode.rt.key.x() <= point.x()
                && pointerNode.rt.key.y() <= point.y()
        ) {
            pointerNode = pointerNode.rt;
        }
        Node rightMin = pointerNode;
        if (rightMin.lb == null) {
            return rightMin;
        }
        pointerNode = rightMin.lb;
        Node leftMin = getFloorOf(pointerNode, point);
        return shortestNode(point, rightMin, leftMin);
    }


    public void draw() {
        draw(root, 1);
    }

    private void draw(Node node, int depth) {
        if (root == null)
            return;
        drawPoint(node.key);
        drawLine(node.rect, depth);
        if (node.lb != null)
            draw(node.lb, depth + 1);
        if (node.rt != null)
            draw(node.rt, depth + 1);


    }

    private void drawLine(RectHV key, int depth) {
        if (depth % 2 == 0)
            StdDraw.setPenColor(StdDraw.RED);
        else
            StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius(0.01);
        // bottom line
        StdDraw.line(key.xmin(), key.ymin(), key.xmax(), key.ymin());
        // top line
        StdDraw.line(key.xmin(), key.ymax(), key.xmax(), key.ymax());
        // left line
        StdDraw.line(key.xmin(), key.ymin(), key.xmin(), key.ymax());
        // right line
        StdDraw.line(key.xmax(), key.ymin(), key.xmax(), key.ymax());
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
        if (!isEmpty()) {
            return 1 + size(root.lb) + size(root.rt);
        }
        return 0;
    }

    private int size(Node node) {
        if (node != null) {
            return 1 + size(node.lb) + size(node.rt);
        }
        return 0;
    }


    private static class Node {
        private Point2D key;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private int depth;        // the right/top subtree

        Node(Point2D key) {
            this.key = key;
            this.depth = 1;
            //rect = new RectHV(key.x(), 0, key.x(), 1);
            rect = new RectHV(0, 0, key.x(), 1);
        }

        private Node(Point2D key, RectHV partitionedRect, int depth) {
            this.key = key;
            this.rect = partitionedRect;
        }

        private static Node create(Point2D newPoint, RectHV space, int depth) {
            return new Node(newPoint, space, depth);
        }

        /** Use only when lb!=null **/
        private RectHV findRightRectangle(int depth) {
            if (lb == null) {
                if (depth % 2 == 0)
                    return new RectHV(rect.xmax(), rect.ymin(), 1, rect.ymax());
                else
                    return new RectHV(rect.xmax(), rect.ymin(), 1, rect.ymax());
            }
            if (depth % 2 == 0)
                return new RectHV(lb.rect.xmax(), rect.ymin(), rect.xmax(), rect.ymax());
            else
                return new RectHV(rect.xmin(), lb.rect.ymax(), rect.xmax(), 1);
        }


        public String toString() {
            return "\nNode{" +
                    "key=" + (key != null ? key.toString() : '-') +
                    ", rect=" + (rect != null ? rect.toString() : '-')
                    + toLbString(depth)
                    + toRtString(depth) +
                    ", depth = " + depth +
                    " }";
        }


        public String toString(int depth) {
            String tab = new String(new char[depth]).replace("\0", "\t");
            return tab + "\nNode {\n" +
                    "key=" + (key != null ? key.toString() : '-') + ", depth = " + depth +
                    ", rect=" + (rect != null ? rect.toString() : '-')
                    + toLbString(depth)
                    + toRtString(depth)
                    + "}";
        }

        public String toLbString(int depth) {
            if (lb == null) return ", lb = - ";
            return "\nlb=" + lb.toString(depth + 1);
        }

        public String toRtString(int depth) {
            if (rt == null) return ", rt = - ";
            return "\nrt =" + rt.toString(depth + 1);
        }

    }

}
