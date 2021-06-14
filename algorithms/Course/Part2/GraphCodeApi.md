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

## How to ankify

```bash
mdanki GraphCodeApi.md GraphCodeApi.apkg --deck "Sedgewick::Part2::GraphCodeApi"
```