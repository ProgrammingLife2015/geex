package nl.tudelft.context.model.graph;

import org.jgrapht.graph.DefaultEdge;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Jim
 * @since 6/2/2015
 */
public class VariationGraph extends StackGraph {

    /**
     * The graph that will be read.
     */
    StackGraph graph;

    /**
     * All the variations that are detected.
     */
    Map<Integer, DefaultNode> variations = new HashMap<>();

    public VariationGraph(final StackGraph graph) {

        this.graph = graph;
        checkMutations();

    }

    /**
     * The function that creates the mutations.
     */
    public void checkMutations() {

        Set<DefaultNode> nodeSet = graph.vertexSet().stream()
                .filter(node -> graph.outDegreeOf(node) > 1)
                .collect(Collectors.toSet());

        nodeSet.forEach(this::checkVariation);

    }

    /**
     * Recursively checks a variation from start till end.
     *
     * @param startNode the node where a variation starts.
     */
    private void checkVariation(final DefaultNode startNode) {

        List<DefaultNode> nextNodes =  getNextNodes(startNode);
        List<List<DefaultNode>> listSets = getFreshListSets(graph.outDegreeOf(startNode));

        int set = 0;

        while (!nextNodes.isEmpty()) {

            DefaultNode node = nextNodes.remove(0);

            if (!checkAllSets(listSets, node)) {

                variations.put(variations.size(), node);
                listSets.get(set).add(node);
                set = getNextSetInt(set, graph, startNode);

                Set<DefaultEdge> setOfEdges = graph.outgoingEdgesOf(node);

                if (!nextNodes.isEmpty()) {
                    setOfEdges.stream().map(graph::getEdgeTarget).collect(Collectors.toList()).forEach(nextNodes::add);
                } else {
                    variations.remove(variations.size());
                }

            } else {

                variations.remove(variations.size());
                break;

            }

        }

    }

    /**
     * Returns and int set that will determine which set to add the next node to.
     * @param set The int that will be incremented.
     * @param graph The graph this startNode is in.
     * @param startNode The startNote of the variation.
     * @return The int that says which set is next to add the node to.
     */
    private int getNextSetInt(int set, final StackGraph graph, final DefaultNode startNode) {

        set++;
        if (set >= graph.outDegreeOf(startNode)) {
            set = 0;
        }

        return set;

    }

    /**
     * This function returns a List of empty lists.
     *
     * @param amount How many lists you want in the list.
     * @return The list of lists.
     */
    private List<List<DefaultNode>> getFreshListSets(int amount) {

        List<List<DefaultNode>> list = new LinkedList<>();

        for (int i = 0; i < amount; i++) {

            list.add(new LinkedList<>());

        }

        return list;
    }

    /**
     * This function returns the next nodes of a node.
     *
     * @param startNode The node this function will return the next nodes from.
     * @return Return a list of nodes that is connected to the startNode.
     */
    private List<DefaultNode> getNextNodes(DefaultNode startNode) {

        List<DefaultNode> targetNodes =  new LinkedList<>();

        Set<DefaultEdge> listEdges = graph.outgoingEdgesOf(startNode);
        targetNodes.addAll(listEdges.stream().map(graph::getEdgeTarget).collect(Collectors.toList()));


        return targetNodes;

    }

    /**
     * Function that checks if the node is inside one of the sets that is given with the iterator.
     *
     * @param list The list of sets.
     * @param node The node that is to be found.
     * @return If the node is found it will return true and vice versa.
     */
    private boolean checkAllSets(List<List<DefaultNode>> list, DefaultNode node) {

        boolean res = false;
        for (List<DefaultNode> aList : list) {

            if (aList.contains(node)) {
                res = true;
                break;
            }

        }

        return res;

    }

}
