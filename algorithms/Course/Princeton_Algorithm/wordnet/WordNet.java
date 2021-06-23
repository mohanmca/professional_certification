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
import java.util.Map;
import java.util.Set;


public class WordNet {
    private Map<String, Set<Integer>> synsetIdSetKeyedByNoun;
    private Map<Integer, String> nounsKeyedBySynsetId;

    private final SAP shortestAncestorPathFinder;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("Arguments should not be null!");
        populateSynsetMap(synsets);

        In hypIn = new In(hypernyms);
        String[] hypernymLinks = hypIn.readAllLines();
        Digraph graph = new Digraph(hypernymLinks.length);

        for (String relation : hypernymLinks) {
            int[] columns = Arrays.stream(relation.split(",")).map(Integer::parseInt)
                                  .mapToInt(Integer::intValue).toArray();
            for (int i = 1; i < columns.length; i++) {
                graph.addEdge(columns[0], columns[i]);
            }
        }
        if (new DirectedCycle(graph).hasCycle()) {
            throw new IllegalArgumentException("Graph has cycle!");
        }
        shortestAncestorPathFinder = new SAP(graph);
    }

    private void populateSynsetMap(String synsets) {
        In syntax = new In(synsets);
        synsetIdSetKeyedByNoun = new HashMap<String, Set<Integer>>();
        nounsKeyedBySynsetId = new HashMap<Integer, String>();
        while (syntax.hasNextLine()) {
            String[] columns = syntax.readLine().split(",");
            int synsetId = Integer.parseInt(columns[0]);
            nounsKeyedBySynsetId.put(synsetId, columns[1]);
            for (String noun : columns[1].split(" ")) {
                if (!synsetIdSetKeyedByNoun.containsKey(noun)) {
                    synsetIdSetKeyedByNoun.put(noun, new HashSet<Integer>());
                }
                synsetIdSetKeyedByNoun.get(noun).add(synsetId);
            }
        }
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
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException("word should not be null!");
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
        WordNet net = new WordNet("synsets8.txt", "hypernyms8ManyAncestors.txt");
        System.out.println(net.distance("a", "d"));
        net = new WordNet("synsets11.txt", "hypernyms11AmbiguousAncestor.txt");
        System.out.println(net.distance("a", "g"));
        net = new WordNet("synsets100-subgraph.txt", "hypernyms100-subgraph.txt");
        System.out.println(net.sap("supermolecule", "factor_X"));
    }

}
