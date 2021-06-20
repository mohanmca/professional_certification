/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

    private BreathFirstDigraph[] graphPaths;
    private Digraph graph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph g) {
        this.graph = g;
        graphPaths = new BreathFirstDigraph[g.V()];
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        int parent = ancestor(v, w);
        if (parent != -1)
            return graphPaths[v].pathTo(parent).size() + graphPaths[w].pathTo(parent).size() - 2;
        return parent;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v >= graph.V() || w >= graph.V()) return -1;
        if (graphPaths[v] == null)
            graphPaths[v] = new BreathFirstDigraph(graph, v);
        if (graphPaths[w] == null)
            graphPaths[w] = new BreathFirstDigraph(graph, w);
        if (graphPaths[v].getAncestor() != -1 && graphPaths[w].getAncestor() != -1) {
            for (Integer x : graphPaths[v].pathToAncestor()) {
                if (graphPaths[w].hasPathTo(x)) {
                    return x;
                }
            }
        }
        return -1;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> vs, Iterable<Integer> ws) {
        int min = 99999;
        for (int v : vs) {
            for (int w : ws) {
                min = Math.min(length(v, w), min);
            }
        }
        return min == 99999 ? -1 : min;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> vs, Iterable<Integer> ws) {
        int min = 99999;
        int parent = -1;
        for (int v : vs) {
            for (int w : ws) {
                int len = length(v, w);
                if (len != -1 && len < min) {
                    parent = ancestor(v, w);
                }
            }
        }
        return parent;
    }


    private static class BreathFirstDigraph {
        private Digraph graph;
        private boolean[] marked;
        private Integer[] edgeTo;
        private int ancestor = -1;
        private int s;

        public BreathFirstDigraph(Digraph g, int s) {
            marked = new boolean[g.V()];
            edgeTo = new Integer[g.V()];
            graph = g;
            this.s = s;
            bfs(s);
        }

        private void bfs(int s) {
            java.util.ArrayDeque<Integer> deque = new java.util.ArrayDeque<Integer>();
            marked[s] = true;
            deque.add(s);
            while (!deque.isEmpty()) {
                int v = deque.poll();
                if (graph.outdegree(v) == 0 && ancestor == -1) {
                    ancestor = v;
                }
                else {
                    for (int w : graph.adj(v)) {
                        if (!marked[w]) {
                            marked[w] = true;
                            edgeTo[w] = v;
                            deque.add(w);
                        }
                    }
                }
            }
        }

        private Iterable<Integer> pathToAncestor() {
            return pathTo(ancestor);
        }

        private boolean hasPathTo(int v) {
            return marked[v];
        }

        private Stack<Integer> pathTo(int v) {
            Stack<Integer> trace = new Stack<>();
            for (int x = v; x != s; x = edgeTo[x]) {
                trace.push(x);
            }
            trace.push(s);
            return trace;
        }

        private int getAncestor() {
            return ancestor;
        }
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
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

}
