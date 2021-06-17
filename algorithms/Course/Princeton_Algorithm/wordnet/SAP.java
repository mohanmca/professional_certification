/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Stack;

public class SAP {
    Digraph graph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph g) {
        graph = g;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {

    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        DepthFirstDiGraph digraphV = new DepthFirstDiGraph(graph, v);
        DepthFirstDiGraph dfsPathsOfB = new DepthFirstDiGraph(graph, w);
        for (Integer x : digraphV.pathToAncestor()) {
            if (dfsPathsOfB.hasPathTo(x)) {
                return x;
            }
        }
        return -1;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w)

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w)

    // do unit testing of this class

    public static void main(String[] args) {

    }


    private static class DepthFirstDiGraph {
        private Digraph graph;
        private boolean[] marked;
        private Integer[] edgeTo;
        private int ancestor = -99;
        private int s;

        public DepthFirstDiGraph(Digraph g, int s) {
            marked = new boolean[g.V()];
            edgeTo = new Integer[g.V()];
            graph = g;
            this.s = s;
            dfs(s);
        }

        private void dfs(int v) {
            marked[v] = true;
            for (int w : graph.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    dfs(w);
                }
            }
            if (ancestor == -99) ancestor = v;
        }

        private Iterable<Integer> pathToAncestor() {
            return pathTo(ancestor);
        }

        private boolean hasPathTo(int v) {
            return marked[v];
        }

        private Iterable<Integer> pathTo(int v) {
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

}
