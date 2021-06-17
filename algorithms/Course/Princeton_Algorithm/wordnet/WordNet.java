/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.DepthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.TransitiveClosure;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class WordNet {
    private Map<String, Integer> synsetMap;
    private Map<Integer, String> synsetReverseMap;
    private Digraph graph;
    TransitiveClosure tc;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        populateSynsetMap(synsets);
        graph = new Digraph(synsetReverseMap.size());
        In hypIn = new In(hypernyms);
        while (hypIn.hasNextLine()) {
            int[] columns = Arrays.stream(hypIn.readLine().split(",")).map(Integer::parseInt)
                                  .mapToInt(Integer::intValue).toArray();
            for (int i = 1; i < columns.length; i++) {
                graph.addEdge(columns[0], columns[1]);
            }
        }
        tc = new TransitiveClosure(graph);
    }

    private Map<String, Integer> populateSynsetMap(String synsets) {
        In syntax = new In(synsets);
        synsetMap = new HashMap<>();
        synsetReverseMap = new HashMap<>();
        int count = 0;
        while (syntax.hasNextLine()) {
            String[] columns = syntax.readLine().split(",");
            synsetMap.put(columns[1], Integer.parseInt(columns[0]));
            synsetReverseMap.put(count++, columns[1]);
        }
        return synsetMap;
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return synsetMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return synsetMap.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        DepthFirstDirectedPaths paths = new DepthFirstDirectedPaths(graph, synsetMap.get(nounA));

        int c = 0;
        for (Integer i : paths.pathTo(synsetMap.get(nounB)))
            c++;
        return c;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        DepthFirstDiGraph dfsPathA = new DepthFirstDiGraph(graph, synsetMap.get(nounA));
        DepthFirstDiGraph dfsPathB = new DepthFirstDiGraph(graph, synsetMap.get(nounB));
        for (Integer w : dfsPathA.pathToAncestor()) {
            if (dfsPathB.hasPathTo(w)) {
                return synsetReverseMap.get(w);
            }
        }
        throw new RuntimeException();
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet net = new WordNet("synsets15.txt", "hypernyms15Path.txt");
        System.out.println(net.isNoun("n"));
        System.out.println(net.isNoun("l"));
        System.out.println(net.sap("n", "m"));
        net = new WordNet("synsets100-subgraph.txt", "hypernyms100-subgraph.txt");
        System.out.println(net.sap("corn_gluten", "elastin"));
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
