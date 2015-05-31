package nl.tudelft.context.mutations;

import nl.tudelft.context.model.graph.Graph;
import nl.tudelft.context.model.graph.Node;
import org.jgrapht.graph.DefaultEdge;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Jim
 * @version 1.1
 * @since 22-5-2015
 */
public final class MutationParser {

    /**
     * The graph that will be checked.
     */
    Graph graph;

    /**
     * The set with nodes where a variation is detected.
     */
    List<Node> variations;

    /**
     * The constructor of the class.
     *
     * @param graph The graph that will be checked for mutations
     */
    public MutationParser(final Graph graph) {

        this.graph = graph;
        this.variations = new LinkedList<>();

    }

    /**
     * The function that creates the mutations.
     */
    public List<Node> checkMutations() {

        Set<Node> nodeSet = graph.vertexSet().stream()
                .filter(node -> graph.outDegreeOf(node) > 1)
                .collect(Collectors.toSet());

        System.out.println("nodeSet: " + nodeSet.stream().map(Node::getId).collect(Collectors.toList()));

        System.out.println(graph);

        nodeSet.forEach(this::checkVariation);

        System.out.println("Variations: " + variations.stream().map(Node::getId).collect(Collectors.toList()));

        return variations;

    }

    /**
     * Recursively checks a variation from start till end.
     *
     * @param startNode the node where a variation starts.
     */
    private void checkVariation(final Node startNode) {

        List<Node> nextNodes =  getNextNodes(startNode);
        List<List<Node>> listSets = getFreshListSets(graph.outDegreeOf(startNode));

        int set = 0;

        while (!nextNodes.isEmpty()) {

            Node node = nextNodes.remove(0);

//            if (graph.outDegreeOf(node) > 1) {
//
//                checkVariation(node);
//
//            }

            if (checkAllSets(listSets, node)) {

                break;

            } else {

                addToList(variations, node);
                listSets.get(set).add(node);
                set++;
                if (set >= graph.outDegreeOf(startNode)) {
                    set = 0;
                }

                Set<DefaultEdge> setOfEdges = graph.outgoingEdgesOf(node);

                if (!nextNodes.isEmpty()) {
                    setOfEdges.stream().map(graph::getEdgeTarget).collect(Collectors.toList()).forEach(n -> addToList(nextNodes, n));
                } else {
                    variations.remove(node);
                }

            }

        }

    }

    /**
     * This function checks if there is a duplicate in the list.
     *
     * @param node The node to be added.
     */
    private void addToList(List<Node> list, Node node) {

        if (!list.contains(node)) {
            list.add(node);
        }

    }

    /**
     * This function returns a List of empty lists.
     *
      * @param amount How many lists you want in the list.
     * @return The list of lists.
     */
    private List<List<Node>> getFreshListSets(int amount) {

        List<List<Node>> list = new LinkedList<>();

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
    private List<Node> getNextNodes(Node startNode) {

        List<Node> targetNodes =  new LinkedList<>();

        Set<DefaultEdge> listEdges = graph.outgoingEdgesOf(startNode);
        targetNodes.addAll(listEdges.stream().map(graph::getEdgeTarget).collect(Collectors.toList()));


        return targetNodes;

    }

    /**
     * Prints the obtained variations.
     */
    public void printVariations() {
        System.out.println("Amount of variations: " + variations.size());
        System.out.println(variations.toString());
    }

    /**
     * Function that checks if the node is inside one of the sets that is given with the iterator.
     *
     * @param list The list of sets.
     * @param node The node that is to be found.
     * @return If the node is found it will return true and vice versa.
     */
    private boolean checkAllSets(List<List<Node>> list, Node node) {

        boolean res = false;
        for (List<Node> aList : list) {

            if (aList.contains(node)) {
                res = true;
                break;
            }

        }

        return res;
//        return !iterator.hasNext() || ((List<Node>) iterator.next()).contains(node) && checkAllSets(list, node);

    }

}
