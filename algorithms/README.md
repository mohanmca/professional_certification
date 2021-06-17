## Graph terminology

* Connections vs Edges (UnDigraph it is connections vs Digraph Edges)
  * Edges has direction from to to, whereas conenctions are bothways and direction less (applicaplble to both the direction)
* Path - Sequences of vertices connected by edges
* Cycle - Path whose first and last vertices are same
* Degree of vertices - Number of edges connected to it (Undirected)
* Connected components - Set of all vertices connected togehter
* Self loop - edge from vertice to itself
* Parallel edge - More than one edges from vertice-v to another vertex-w

## Simple graph-processing problems

1. Number of degree of a vertex
1. Max degree of a graph
1. Avg degred of a graph
1. Compute self loop of a graph

## Three types reprsentation of Graph

* Set of edges (of a graph)
  * List of edges (between vertex) (Memory efficient)
* Adjacency matrix int[][] vertices
* Adjacency List of graph
  * Vertex-index based array of list of edges

## Analysis of Graph datastructure

* Space
  * O(V**2) vs O(E) vs O(V+E)
* Add edge
  * O(1) vs O(1) vs O(V)
* Path between v-and-w
  * O(1) vs O(E) vs O(degree(V))
* Iterate over vertices adjacent to V.
  * O(V) vs O(E) vs O(degree(V))


## Some graph-processing problems

1. Path. Is there a path between s and t?
1. Shortest path. What is the shortest path between s and t?
1. Cycle. Is there a cycle in the graph?
1. Euler tour. Is there a cycle that uses each edge exactly once?
1. Hamilton tour. Is there a cycle that uses each vertex exactly once.
1. Connectivity. Is there a way to connect all of the vertices?
1. MST. What is the best way to connect all of the vertices?
1. Biconnectivity. Is there a vertex whose removal disconnects the graph?
1. Planarity. Can you draw the graph in the plane with no crossing edges
1. Graph isomorphism. Do two adjacency lists represent the same graph?
1. Challenge. Which of these problems are easy? difficult? intractable?


## Why to decouple Graph with Graph-Procesing routine

* There 100s or graph processing routine, we can't merge them into Graph
* All Graph processing algorithm iterates and checks vertices in certain way (but unique)
* If we split, Graph-processing routing can be queried for additional details after processing the graph

## DFS algorithm

*
```python
DFS(Vertx v)
    mark[v] = visited
    for w in adjacent(v) and mark[w]!=visited:
        DFS(w)
        edgeTo[w] =v //optional
```

## Example of GraphProcessing Routines (using DFS)
*  
```java
    DepthFirstPaths(Graph G, int v) {
        bollean hasPathTo(int w);

        public Iterable<Integer> pathTo(int v) {
            validateVertex(v);
            if (!hasPathTo(v)) return null;
            Stack<Integer> path = new Stack<Integer>();
            /** Here s is starting vertex passed in constructor **/
            for (int x = v; x != s; x = edgeTo[x])
                path.push(x);
            path.push(s);
            return path;
        }
    } 
```

## DFS Properties

1. DFS marks all vertices connnected to s in time proportional to the sum of their degress
1. After DFS, can find vertices connected to s in constant time and can find a path to s(if one exists) in time proportional to its length


## BFS algorithm

*
```python
BFS(Vertx v)
    queue.enque(v)
    mark[v] = visited
    while !queue.isEmpty()
        int v = dequeue()
        for w in adjacent(v) and mark[w]!=visited:
            mark[w] = visited
            queue.enque(w)
            edgeTo[w] =v //optional
```

## BFS Properties

1. BFS computes shortes paths (fewest number of edges) from s to all other vertices in a graph in time proportional to E+V
1. Proof.. in queue set of vertices with distance k will be always ahead, and followed by vertices whose distance is k+1;

## Example of Connected-Component GraphProcessing Routines (using DFS)

*
```python
CC(Graph g)
    mark[v] = visited
    ccid[] = -1;
    int count;
    for(int v=0; v < G.V(); v++):
        if(!marked[v]):
            dfs(G, v);
                count++;

    findCC_Count():Intger => return count
    dfs(Graph g, int v) {
        ccid[v] = count;
        for(int w=0; w < G.adj(v);):
            if(!marked[w]):
                dfs(G, w);                        
    }
    ccid ==> has all the connected component ids
```



## Exercise for first Video lectures

1. BiPartite Graph exercise : Solvable by student
2. Euerlarian Cycle Exercise : Solvable by student
3. Diameter and center of a tree. Given a connected graph with no cycles
   * Diameter: design a linear-time algorithm to find the longest simple path in the graph.
   * Center: design a linear-time algorithm to find a vertex such that its maximum distance from any other vertex is minimized.
4. Find cycle that uses ever edge in the graph :: solvable by student (Euelrian Grpah)
5. Intractable - Nobody knows efficient solution (TSP) for large graph
  * Hamiltonian Cycle
  * The Hamilton cycle problem is NP-complete—it is unlikely to have a polynomial-time algorithm. You can't find O(E+V)
6. Graph Isomorphism
   *  Not easy, non-one knows - how to solve this proble? we do't know how to classify problem?
7. Layout a graph in the plane without crssoing edge.
   * Tarjan - 1970 algorithm, DFS based (too complicated)

Question 3

## Euler cycle. 
1. An Euler cycle in a graph is a cycle (not necessarily simple) that uses every edge in the graph exactly one.
1. Show that a connected graph has an Euler cycle if and only if every vertex has even degree.
1. Design a linear-time algorithm to determine whether a graph has an Euler cycle, and if so, find one.

## Legend of Graph algorithms - Tarjan

1. Tarjan
2. Shortest path - Dijkstra

## How to create anki

```bash
mdanki UndirectedGraph.md UndirectedGraph.apkg --deck "Sedgewick::Part2::UndirectedGraph"
```
## DiGraph terminology

* Connections vs Edges (UnDigraph it is connections vs Digraph Edges)
  * Edges has direction from to to, whereas conenctions are bothways and direction less (applicaplble to both the direction)
* How many differetn digraph possible with Vertices V
  * There are V**2 possible edges
  * Every edge is in the graph or not, so 2**(V**2)
* Most of the algorithm learnt for Undirected Graph would just work for DirectedGraph

## Some Digraph problems

1. Is there a directed path from s to t.
1. What is the shortest directed path from s to t.
1. Topological-Sort - Can you draw a digram, so that all edges point upwards
1. SCC - Strong Connectivity - Is there a directed between all pairs of vertices?
1. Transitive-Closure: For which vertices v and w, is there a path from v to w?
1. Page Rank: What is the importance of a web-page?
1. Most of the di-graph problems can be solved by DFS



## DigraphAPI

```python
class DiGraph(Vertx v)
      addEdge(int v, int w)
      adjacent(v) => Iterator<Integer>
      reverse() => Graph
```

## DFS of Digraph

1. Refer DFS of Undirected graph, it should just work for DiGraph (from single source vertex)
1. ```python
    DepthFirstSearch(Graph G, int s)
        invoke dfs(g, s)

        def dfs(g, v)
          mark[v] = visited
          for w in adjacent(v) and mark[w]!=visited:
              DFS(w)
              edgeTo[w] =v //optional
    ```

## Problems solved by DFS

1. Program control flow analysis - Find and remove (unreachable code)
1. mark-sweep garbage collector - starting at a root and following a chain of pointers - Uses 1 extra mark bit per object (plus DFS stack).
  1. Mark: mark all reachable objects.
  1. Sweep: if object is unmarked, it is garbage (so add to free list).


##  Depth-first search in digraphs summary

* DFS enables direct solution of simple digraph problems.
  1. Reachability.
  1. Path finding.
  1. Topological sort.
  1. Directed cycle detection.
* Basis for solving difficult digraph problems
  1. 2-satisfiability.
  1. Directed Euler path.
  1. Strongly-connected components.

## Barebone's web-crawler in Java

```Java
Queue<String> queue = new Queue<String>();
SET<String> marked = new SET<String>();

String root = "http://www.princeton.edu";
queue.enqueue(root);
marked.add(root);
while (!queue.isEmpty())
{
  String v = queue.dequeue();
  StdOut.println(v);
  In in = new In(v);
  String input = in.readAll();
  String linkRegex = "http://(\\w+\\.)*(\\w+)";
  Matcher matcher = Pattern.compile(linkRegex).matcher(input);
  while (matcher.find())
  {
    String w = matcher.group();
    if (!marked.contains(w))  { 
      marked.add(w);
      queue.enqueue(w);
    }
  }
}
```

## Toplogical Sort (in DAG)

* DFS + ReversePostOrder
* DFS Reverse postorder for Topological sort was simple, but it was not discovered for many years
* ReversePostOrder : Use simple DFS, and when there were no-were adjacent vertices to navigate, it should goes to Stack
1. 

```java
public class DepthFirstOrder
{

private boolean[] marked;
private Stack<Integer> reversePost;

public DepthFirstOrder(Digraph G){
  reversePost = new Stack<Integer>();
  marked = new boolean[G.V()];
  for (int v = 0; v < G.V(); v++)
    if (!marked[v]) dfs(G, v);
}

private void dfs(Digraph G, int v){
  marked[v] = true;
  for (int w : G.adj(v))
  if (!marked[w]) dfs(G, w);
  reversePost.push(v);
}
 public Iterable<Integer> reversePost(){ return reversePost; }
}
```

## Topological sort in a DAG: correctness proof

* Proposition. Reverse DFS postorder of a DAG is a topological order.
* Pf. Consider any edge v→w. When dfs(v) is called:
1. Case 1: dfs(w) has already been called and returned. Thus, w was done before v.
1. Case 2: dfs(w) has not yet been called.
    1. dfs(w) will get called directly or indirectly by dfs(v) and will finish before dfs(v).
    1. Thus, w will be done before v.
1. Case 3: dfs(w) has already been called, but has not yet returned.
   1. Can’t happen in a DAG: function call stack contains path from w to v, so v→w would complete a cycle.

## Proposition. A digraph has a topological order iff no directed cycle.

* Proof 
  1. If directed cycle, topological order impossible.
  1. If no directed cycle, DFS-based algorithm finds a topological order.

## Directed cycle detection application: precedence scheduling

1. in what order should we schedule the tasks?
    1. In topological sort order
1. A directed cycle implies scheduling problem is infeasible


## Strongly-connected components

* SCC is maximal set of mutually reachable nodes
* Vertices v and w are strongly connected
  1. if there is both a directed path from v to w 
  1. And a directed path from w to v.
* Key property. Strong connectivity is an equivalence relation:
  1. v is strongly connected to v.
  1. If v is strongly connected to w, then w is strongly connected to v.
  1. If v is strongly connected to w and w to x, then v is strongly connected to x.  
* A strong component is a maximal subset of strongly-connected vertices.
* 
```java
  public int connected(int v, int w){ return cc[v] == cc[w]; }
  public int stronglyConnected(int v, int w){ return scc[v] == scc[w]; }
```

## Kosaraju-Sharir algorithm: intuition

1. Kernel DAG. Contract each strong component into a single vertex.
1. Compute topological order (reverse postorder) in kernel DAG.
1. Run DFS, considering vertices in reverse topological order.
* Simple (but mysterious) algorithm for computing strong components.
  1. Phase 1: run DFS on G^R to compute reverse postorder.
  1. Phase 2: run DFS on G, considering vertices in order given by first DFS-of-(G^R).
*

```java
public CC(Graph G) {                    |   public SCC(Graph G) {                    
    marked = new boolean[G.V()];        |      marked = new boolean[G.V()];        
    id = new int[G.V()];                |      id = new int[G.V()];                
    for (int v = 0; v < G.V(); v++) {   |      for (int v : new DepthFirstOrder(G.reverse()).reversePostOrder()) {   
        if (!marked[v]) {               |          if (!marked[v]) {               
            dfs(G, v);                  |              dfs(G, v);                  
            count++;                    |              count++;                    
        }                               |          }                               
    }                                   |      }                                   
}                                       |                                          
```


## Application of Strongly-connected components

1. Ecological Food Web
1. Software Modules

## Strong components algorithms: brief history

1. 1960s: Core OR problem.
1. 1972: linear-time DFS algorithm (Tarjan).
1. 1980s: easy two-pass linear-time algorithm (Kosaraju-Sharir).
1. 1990s: more easy linear-time algorithms (Gabow SCC)


## Suppose that a digraph G is represented using the adjacency-lists representation. What is the order of growth of the running time to find all vertices that point to a given vertex v?

* You must scan through each of the V adjacency lists and each of the EEE edges. 
* If this were a common operation in digraph-processing problems, you could associate two adjacency lists with each vertex—one containing all of the vertices pointing from v (as usual) and one containing all of the vertices pointing to v.


## Suppose that during an execution of depth-first search in a digraph G, dfs(v) is called after dfs(w) is called but before dfs(w) returns. Which of the following must be true of graph G?

* There exists a directed path from w to v.
* The function call stack between when w and v are called contains a directed path from w to v.

## DAG Topological sort order question - Consider an execution of depth-first search on a directed acyclic graph $DAG$ which contains the edge v ---> w. Which one of the following is impossible at the time dfs(v) is called?

* If dfs(v) is called before dfs(w) returns, then the function-call stack contains a directed path from w to v (as in the previous in-video quiz). 
* Combining this path with the edge v --> w yields a directed cycle, which is impossible since G is acyclic.

## How many strong components does a DAG on VVV vertices and EEE edges have?

* In a DAG, no two vertices vvv and www can be in the same strong component—if there were, there would be both a directed path from vvv to www and one from www to vvv, which implies that the digraph has a directed cycle. Thus, each vertex is its own strong component.


## The Kosaraju–Sharir algorithm runs DFS in G^R followed by DFS in G. Which of the following modifications will not find the strong components?

1. The DFS in the first phase (to compute the reverse postorder) is crucial; in the second phase, any algorithm that marks the set of vertices reachable from a given vertex will do.
1. Running DFS in G followed by DFS in G^R computes the strong components in G^R, which are the same as those in G.
1. Run BFS in G^R followed by DFS in G. (Won't work)
1. Run DFS in G^R followed by BFS in G.
1. Run DFS in G followed by BFS in G^R.
1. Run DFS in G followed by DFS in G^R.


## Exercise

* Given a digraph, find a directed cycle. (refer: Book)
* [Learn Gabow SCC Algorithm](https://algs4.cs.princeton.edu/42digraph/GabowSCC.java.html)

```bash
mdanki DirectedGraph.md DirectedGraph.apkg --deck "Sedgewick::Part2::DirectedGraph"
```
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
