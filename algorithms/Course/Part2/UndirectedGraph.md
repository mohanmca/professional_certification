## Graph terminology

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