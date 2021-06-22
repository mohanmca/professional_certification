/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class SAP {

    private final BreadthFirstDirectedPaths[] bfsPaths;
    private final Digraph graph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph g) {
        graph = new Digraph(g);
        bfsPaths = new BreadthFirstDirectedPaths[graph.V()];
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= graph.V())
            throw new IllegalArgumentException(
                    "vertex " + v + " is not between 0 and " + (graph.V() - 1));
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        int parent = ancestor(v, w);
        if (parent != -1) {
            return bfsPaths[v].distTo(parent) + bfsPaths[w].distTo(parent);
        }
        return parent;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        if (bfsPaths[v] == null)
            bfsPaths[v] = new BreadthFirstDirectedPaths(graph, v);
        if (bfsPaths[w] == null)
            bfsPaths[w] = new BreadthFirstDirectedPaths(graph, w);

        int minDistance = Integer.MAX_VALUE;
        int ancestor = -1;

        // If they are in cycle, find the shortest one
        if (bfsPaths[v].hasPathTo(w)) {
            ancestor = w;
            minDistance = bfsPaths[v].distTo(w);
        }

        if (bfsPaths[w].hasPathTo(v) && bfsPaths[w].distTo(v) < minDistance) {
            ancestor = v;
            minDistance = bfsPaths[w].distTo(v);
        }

        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(graph, Arrays.asList(v, w));
        // if they have common ancestor
        for (int x = 0; x < graph.V(); x++) {
            if (x != v && x != w && bfs.hasPathTo(x)) {
                if (bfsPaths[v].hasPathTo(x) && bfsPaths[w].hasPathTo(x)
                        && bfsPaths[v].distTo(x) + bfsPaths[w].distTo(x) < minDistance) {
                    minDistance = bfsPaths[w].distTo(x) + bfsPaths[v].distTo(x);
                    ancestor = x;
                }
            }
        }

        return ancestor;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> vs, Iterable<Integer> ws) {
        int min = Integer.MAX_VALUE;
        int parent = -1;
        for (Integer v : vs) {
            for (Integer w : ws) {
                int len = length(v, w);
                if (len != -1 && len < min) {
                    parent = ancestor(v, w);
                    min = len;
                }
            }
        }
        return parent;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> vs, Iterable<Integer> ws) {
        int min = Integer.MAX_VALUE;
        for (Integer v : vs) {
            for (Integer w : ws) {
                if (v == null || w == null)
                    throw new IllegalArgumentException("Vertex should not be null!");
                min = Math.min(length(v, w), min);
            }
        }
        return min == Integer.MAX_VALUE ? -1 : min;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("vertex-v = %d, ancestor = %d\n", v, ancestor);
            StdOut.printf("vertex-w = %d, ancestor = %d\n", w, ancestor);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

}
