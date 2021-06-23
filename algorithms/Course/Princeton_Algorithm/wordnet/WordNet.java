/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class WordNet {
    private final Map<String, Set<Integer>> synsetIdSetKeyedByNoun;
    private final Map<Integer, String> nounsKeyedBySynsetId;

    private final SAP shortestAncestorPathFinder;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("Arguments should not be null!");
        List<Map> maps = populateSynsetMap(synsets);
        synsetIdSetKeyedByNoun = (Map<String, Set<Integer>>) maps.get(0);
        nounsKeyedBySynsetId = (Map<Integer, String>) maps.get(0);

        In hypIn = new In(hypernyms);
        String[] hypernymLinks = hypIn.readAllLines();
        Digraph graph = new Digraph(hypernymLinks.length);

        for (String relation : hypernymLinks) {
            int[] columns = Arrays.stream(relation.split(",")).map(Integer::parseInt)
                                  .mapToInt(Integer::intValue).toArray();
            for (int i = 1; i < columns.length; i++) {
                graph.addEdge(columns[0], columns[1]);
            }
        }
        if (new DirectedCycle(graph).hasCycle()) {
            throw new IllegalArgumentException("Graph has cycle!");
        }
        if (!isRootedGraph(graph)) {
            throw new IllegalArgumentException("Graph is not DAG!");
        }
        shortestAncestorPathFinder = new SAP(graph);
    }

    private boolean isRootedGraph(Digraph graph) {
        int zeroOutDegree = 0;
        for (int i = 0; i < graph.V(); i++) {
            int count = 0;
            for (int v : graph.adj(i))
                count++;
            if (count == 1) zeroOutDegree++;
        }
        return zeroOutDegree == 1;
    }

    private List<Map> populateSynsetMap(String synsets) {
        In syntax = new In(synsets);
        Map<String, Set<Integer>> synsetMap = new HashMap<>();
        Map<Integer, String> synsetReverseMap = new HashMap<>();
        while (syntax.hasNextLine()) {
            String[] columns = syntax.readLine().split(",");
            int synsetId = Integer.parseInt(columns[0]);
            for (String noun : columns[1].split(" ")) {
                if (!synsetMap.containsKey(noun)) {
                    synsetMap.put(noun, new HashSet<Integer>());
                }
                synsetMap.get(noun).add(synsetId);
                synsetReverseMap.put(synsetId, noun);
            }
        }
        return Arrays.asList(synsetMap, synsetReverseMap);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return synsetIdSetKeyedByNoun.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException("word should not be null!");
        return synsetIdSetKeyedByNoun.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException("nouns are not allowed to be null!");
        if (!isNoun(nounA) || !isNoun(nounB)) return Integer.MAX_VALUE;
        if (nounA.equals(nounB)) return 0;
        return shortestAncestorPathFinder
                .length(synsetIdSetKeyedByNoun.get(nounA), synsetIdSetKeyedByNoun
                        .get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException("nouns are not allowed to be null!");

        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException(
                    "Illegal Noun parameter which is not a Wordnet Noun.");

        int parent = shortestAncestorPathFinder
                .ancestor(synsetIdSetKeyedByNoun.get(nounA), synsetIdSetKeyedByNoun.get(nounB));
        if (parent != -1) {
            return nounsKeyedBySynsetId.get(parent);
        }

        throw new IllegalStateException("We should not reach this state!");
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
