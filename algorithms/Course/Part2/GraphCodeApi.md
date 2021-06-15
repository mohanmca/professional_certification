## DiGraph API

```java
public class Digraph {
        public Digraph(int V) {
            if (V < 0) throw new IllegalArgumentException("Number of vertices in a Digraph must be non-negative");
                this.V = V;this.E = 0;
                indegree = new int[V];
                adj = (Bag<Integer>[]) new Bag[V];
                for (int v = 0; v < V; v++) {
                    adj[v] = new Bag<Integer>();
                }
        }
        public int V(); //Number of vertices
        public int E(); //Number of Edges
        public void addEdge(int v, int w);
        public Digraph reverse(); 
}    
```

## DepthFirstDirectedPaths - Determine reachability in a digraph from a given vertex using

```java
//Run depth-first search on an undirected graph.
public class DepthFirstDirectedPaths  {
    private boolean[] marked;    // marked[v] = is there an s-v path?
    private int[] edgeTo;        // edgeTo[v] = last edge on s-v path
    private final int s;         // source vertex

    public DepthFirstDirectedPaths (Digraph G, int s);
    private void dfs(Digraph G, int v);
    public boolean hasPathTo(int v);
    public Iterable<Integer> pathTo(int v) {
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }    
}
```

## DirectedDFS - Determine single-source or multiple-source reachability in a digraph  using depth first search.

```java
//  Determine single-source or multiple-source reachability in a digraph  using depth first search. //
public class DirectedDFS {
    public DirectedDFS(Digraph G, int s) {
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    public DirectedDFS(Digraph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        for (int v : sources) {
            if (!marked[v]) dfs(G, v);
        }
    }

    // * Returns the number of vertices reachable from the source(s) from vertex
    public int count();

    // * Is there a directed path from the source vertex (or any of the source vertices) and vertex {@code v}?
    public boolean marked(int v);
}
```

## TransitiveClosure -  Compute transitive closure of a digraph and support reachability queries.

```java
public class TransitiveClosure {
    private DirectedDFS[] tc;  // tc[v] = reachable from v

    // * Computes the transitive closure of the digraph {@code G}.
    public TransitiveClosure(Digraph G) {
        tc = new DirectedDFS[G.V()];
        for (int v = 0; v < G.V(); v++)
            tc[v] = new DirectedDFS(G, v);
    }

    // * Is there a directed path from vertex {@code v} to vertex {@code w} in the digraph?
    public boolean reachable(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return tc[v].marked(w);
    }
}    
```
## Finds a directed cycle in a digraph.

```java
public class DirectedCycle {
    private boolean[] marked;        // marked[v] = has vertex v been marked?
    private int[] edgeTo;            // edgeTo[v] = previous vertex on path to v
    private boolean[] onStack;       // onStack[v] = is vertex on the stack?
    private Stack<Integer> cycle;    // directed cycle (or null if no such cycle)

    /**
     * Determines whether the digraph {@code G} has a directed cycle and, if so,
     * finds such a cycle.
     * @param G the digraph
     */
    public DirectedCycle(Digraph G) {
        marked  = new boolean[G.V()];
        onStack = new boolean[G.V()];
        edgeTo  = new int[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!marked[v] && cycle == null) dfs(G, v);
    }

    // run DFS and find a directed cycle (if one exists)
    private void dfs(Digraph G, int v) {
        onStack[v] = true;
        marked[v] = true;
        for (int w : G.adj(v)) {

            // short circuit if directed cycle found
            if (cycle != null) return;

            // found new vertex, so recur
            else if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }

            // trace back directed cycle
            else if (onStack[w]) {
                cycle = new Stack<Integer>();
                for (int x = v; x != w; x = edgeTo[x]) {
                    cycle.push(x);
                }
                cycle.push(w);
                cycle.push(v);
                assert check();
            }
        }
        onStack[v] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }

}
```

## Find ToplogicalSort Order for Prcedence Scheduling Algorithm

```java
public class Topological
{
   private Iterable<Integer> order;       // topological order

   public Topological(Digraph G)
   {
      DirectedCycle cyclefinder = new DirectedCycle(G);
      if (!cyclefinder.hasCycle())
      {
         DepthFirstOrder dfs = new DepthFirstOrder(G);
         order = dfs.reversePostorder();
      }
   }

   public Iterable<Integer> order()
   {  return order;  }

   public boolean isDAG()
   {  return order != null;  }

   public static void main(String[] args)
   {
      String filename = args[0];
      String separator = args[1];
      SymbolDigraph sg = new SymbolDigraph(filename, separator);

      Topological top = new Topological(sg.G());

      for (int v : top.order())
         StdOut.println(sg.name(v));
   }
}
```

## Is BiPartitie Graph

```java
public class Bipartite {
    private boolean isBipartite;   // is the graph bipartite?
    private boolean[] color;       // color[v] gives vertices on one side of bipartition
    private boolean[] marked;      // marked[v] = true iff v has been visited in DFS
    private int[] edgeTo;          // edgeTo[v] = last edge on path to v
    private Stack<Integer> cycle;  // odd-length cycle

    /**
     * Determines whether an undirected graph is bipartite and finds either a
     * bipartition or an odd-length cycle.
     *
     * @param  G the graph
     */
    public Bipartite(Graph G) {
        isBipartite = true;
        color  = new boolean[G.V()];
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];

        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v);
            }
        }
        assert check(G);
    }

    private void dfs(Graph G, int v) { 
        marked[v] = true;
        for (int w : G.adj(v)) {

            // short circuit if odd-length cycle found
            if (cycle != null) return;

            // found uncolored vertex, so recur
            if (!marked[w]) {
                edgeTo[w] = v;
                color[w] = !color[v];
                dfs(G, w);
            } 

            // if v-w create an odd-length cycle, find it
            else if (color[w] == color[v]) {
                isBipartite = false;
                cycle = new Stack<Integer>();
                cycle.push(w);  // don't need this unless you want to include start vertex twice
                for (int x = v; x != w; x = edgeTo[x]) {
                    cycle.push(x);
                }
                cycle.push(w);
            }
        }
    }

    public boolean isBipartite() {
        return isBipartite;
    }
 
    /**
     * Returns the side of the bipartite that vertex {@code v} is on.
     *
     * @param  v the vertex
     * @return the side of the bipartition that vertex {@code v} is on; two vertices
     *         are in the same side of the bipartition if and only if they have the
     *         same color
     * @throws IllegalArgumentException unless {@code 0 <= v < V} 
     * @throws UnsupportedOperationException if this method is called when the graph
     *         is not bipartite
     */
    public boolean color(int v) {
        validateVertex(v);
        if (!isBipartite)
            throw new UnsupportedOperationException("graph is not bipartite");
        return color[v];
    }

    /**
     * Returns an odd-length cycle if the graph is not bipartite, and {@code null} otherwise.
     * @return an odd-length cycle if the graph is not bipartite (and hence has an odd-length cycle), and {@code null} otherwise
     */
    public Iterable<Integer> oddCycle() {
        return cycle; 
    }

    private boolean check(Graph G) {
        // graph is bipartite
        if (isBipartite) {
            for (int v = 0; v < G.V(); v++) {
                for (int w : G.adj(v)) {
                    if (color[v] == color[w]) {
                        System.err.printf("edge %d-%d with %d and %d in same side of bipartition\n", v, w, v, w);
                        return false;
                    }
                }
            }
        }

        // graph has an odd-length cycle
        else {
            // verify cycle
            int first = -1, last = -1;
            for (int v : oddCycle()) {
                if (first == -1) first = v;
                last = v;
            }
            if (first != last) {
                System.err.printf("cycle begins with %d and ends with %d\n", first, last);
                return false;
            }
        }

        return true;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }
}    
```

## How to ankify

```bash
cd D:\git\spring_professional_certification\algorithms\Course\Part2\
mdanki GraphCodeApi.md GraphCodeApi.apkg --deck "Sedgewick::Part2::GraphCodeApi"
```