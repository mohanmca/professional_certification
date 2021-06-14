/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.DepthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
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
        Integer ancestor = DepthFirstDiGraph.findAncestor(graph, synsetMap.get(nounA));
        DepthFirstDirectedPaths dfsPaths = new DepthFirstDirectedPaths(graph, synsetMap.get(nounA));
        Iterable<Integer> ancestors = dfsPaths.pathTo(ancestor);
        DepthFirstDirectedPaths dfsPathsOfB = new DepthFirstDirectedPaths(graph,
                                                                          synsetMap.get(nounB));
        for (Integer w : ancestors) {
            if (dfsPathsOfB.hasPathTo(w)) {
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
        private int anscestor = -99;

        private static Integer findAncestor(Digraph g, int s) {
            return new DepthFirstDiGraph(g, s).getAnscestor();
        }

        public DepthFirstDiGraph(Digraph g, int s) {
            marked = new boolean[g.V()];
            graph = g;
            dfs(s);
        }

        private void dfs(int v) {
            marked[v] = true;
            for (int w : graph.adj(v)) {
                if (!marked[w])
                    dfs(w);
            }
            if (anscestor == -99) anscestor = v;
        }

        private int getAnscestor() {
            return anscestor;
        }
    }

}
