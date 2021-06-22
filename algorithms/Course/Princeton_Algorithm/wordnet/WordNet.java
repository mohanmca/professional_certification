/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WordNet {
    private Map<String, List<Integer>> synsetMap;
    private Map<Integer, String> synsetReverseMap;
    private final SAP shortestAncestorPathFinder;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("Arguments should not be null!");
        populateSynsetMap(synsets);

        Digraph graph = new Digraph(synsetReverseMap.size());

        In hypIn = new In(hypernyms);
        while (hypIn.hasNextLine()) {
            int[] columns = Arrays.stream(hypIn.readLine().split(",")).map(Integer::parseInt)
                                  .mapToInt(Integer::intValue).toArray();
            for (int i = 1; i < columns.length; i++) {
                graph.addEdge(columns[0], columns[1]);
            }
        }

        DirectedCycle cycleFinder = new DirectedCycle(graph);
        if (cycleFinder.hasCycle()) {
            throw new IllegalArgumentException("Graph is not DAG, It has cycle!");
        }
        shortestAncestorPathFinder = new SAP(graph);
    }

    private void populateSynsetMap(String synsets) {
        In syntax = new In(synsets);
        synsetMap = new HashMap<>();
        synsetReverseMap = new HashMap<>();
        while (syntax.hasNextLine()) {
            String[] columns = syntax.readLine().split(",");
            int synsetId = Integer.parseInt(columns[0]);
            for (String noun : columns[1].split(" ")) {
                List<Integer> synsetIds = synsetMap.getOrDefault(noun, new ArrayList<Integer>());
                synsetIds.add(synsetId);
                synsetMap.put(noun, synsetIds);
                synsetReverseMap.put(synsetId, noun);
            }
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return synsetMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException("word should not be null!");
        return synsetMap.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException("nouns are not allowed to be null!");
        if (!isNoun(nounA) || !isNoun(nounB)) return Integer.MAX_VALUE;
        if (nounA.equals(nounB)) return 0;
        return shortestAncestorPathFinder.length(synsetMap.get(nounA), synsetMap.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException("nouns are not allowed to be null!");

        int parent = shortestAncestorPathFinder
                .ancestor(synsetMap.get(nounA), synsetMap.get(nounB));
        if (parent != -1) {
            return synsetReverseMap.get(parent);
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


}
