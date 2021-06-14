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