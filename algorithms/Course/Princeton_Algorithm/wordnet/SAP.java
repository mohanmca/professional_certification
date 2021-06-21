/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;

public class SAP {


    private final BreathFirstDigraph[] graphPaths;
    private final Digraph graph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph g) {
        graph = g;
        graphPaths = new BreathFirstDigraph[g.V()];
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        int parent = ancestor(v, w);
        if (parent != -1) {
            return graphPaths[v].distTo[parent] + graphPaths[w].distTo[parent];
        }
        return parent;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v >= graph.V() || w >= graph.V()) throw new IllegalArgumentException();
        if (v < 0 || w < 0) throw new IllegalArgumentException();

        if (graphPaths[v] == null)
            graphPaths[v] = new BreathFirstDigraph(graph, v);
        if (graphPaths[w] == null)
            graphPaths[w] = new BreathFirstDigraph(graph, w);
        if (graphPaths[v].hasPathTo(w)
                && graphPaths[v].distTo[w] <= graphPaths[v].distTo[graphPaths[v].getAncestor()])
            return w;
        if (graphPaths[w].hasPathTo(v)
                && graphPaths[w].distTo[v] <= graphPaths[w].distTo[graphPaths[w].getAncestor()])
            return v;
        if (graphPaths[v].getAncestor() != -1 && graphPaths[v].getAncestor() == graphPaths[w]
                .getAncestor() && graphPaths[v]
                .hasPathTo(graphPaths[w].getAncestor()))
            return graphPaths[v].getAncestor();
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
        private boolean[] marked;
        private Integer[] edgeTo;
        private Integer[] distTo;
        private Integer ancestor = -1;


        public BreathFirstDigraph(Digraph g, Integer s) {
            this(g, Arrays.asList(s));
        }

        public BreathFirstDigraph(Digraph g, Iterable<Integer> sources) {
            marked = new boolean[g.V()];
            edgeTo = new Integer[g.V()];
            distTo = new Integer[g.V()];
            for (Integer v : sources)
                bfs(g, v);
        }

        private void bfs(Digraph graph, Integer source) {
            Iterable<Integer> s = Collections.singletonList(source);
            bfs(graph, s);
        }

        // breadth-first search from multiple sources
        private void bfs(Digraph G, Iterable<Integer> sources) {
            Queue<Integer> q = new Queue<Integer>();
            for (int s : sources) {
                marked[s] = true;
                distTo[s] = 0;
                q.enqueue(s);
            }
            while (!q.isEmpty()) {
                int v = q.dequeue();
                for (int w : G.adj(v)) {
                    if (!marked[w]) {
                        edgeTo[w] = v;
                        distTo[w] = distTo[v] + 1;
                        marked[w] = true;
                        q.enqueue(w);
                    }
                    else if (marked[w] && distTo[w] != 0 && ancestor == -1) {
                        ancestor = w;
                    }
                }
                if (!G.adj(v).iterator().hasNext() && ancestor == -1) {
                    ancestor = v;
                }
            }
        }

        private boolean hasPathTo(int v) {
            return marked[v];
        }

        private Deque<Integer> pathTo(int v) {
            ArrayDeque<Integer> trace = new ArrayDeque<Integer>();
            int x;
            for (x = v; distTo[x] != 0; x = edgeTo[x]) {
                trace.push(x);
            }
            trace.push(x);
            return trace;
        }

        public int getAncestor() {
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
            StdOut.printf("vertex-v = %d, ancestor = %d\n", v, sap.graphPaths[v].getAncestor());
            StdOut.printf("vertex-w = %d, ancestor = %d\n", w, sap.graphPaths[w].getAncestor());
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

}
