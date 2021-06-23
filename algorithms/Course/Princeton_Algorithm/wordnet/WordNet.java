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
    private final Map<String, List<Integer>> synsetMap;
    private final Map<Integer, String> synsetReverseMap;
    private final SAP shortestAncestorPathFinder;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("Arguments should not be null!");
        List<Map> maps = populateSynsetMap(synsets);
        synsetMap = (Map<String, List<Integer>>) maps.get(0);
        synsetReverseMap = (Map<Integer, String>) maps.get(0);

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

    private List<Map> populateSynsetMap(String synsets) {
        In syntax = new In(synsets);
        Map<String, List<Integer>> synsetMap = new HashMap<>();
        Map<Integer, String> synsetReverseMap = new HashMap<>();
        while (syntax.hasNextLine()) {
            String[] columns = syntax.readLine().split(",");
            int synsetId = Integer.parseInt(columns[0]);
            for (String noun : columns[1].split(" ")) {
                if (!synsetMap.containsKey(noun)) {
                    synsetMap.put(noun, new ArrayList<Integer>());
                }
                synsetMap.get(noun).add(synsetId);
                synsetReverseMap.put(synsetId, noun);
            }
        }
        return Arrays.asList(synsetMap, synsetReverseMap);
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
        WordNet net = new WordNet("synsets.txt", "hypernyms.txt");
        String v = "basidiomycetous_fungi";
        String w = "Battle_of_Pydna";
        System.out.println(net.isNoun(v));
        System.out.println(net.isNoun(w));
        System.out.println(net.distance(v, w));
    }

}
