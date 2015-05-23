package nl.tudelft.context.mutations;

import nl.tudelft.context.graph.Graph;
import nl.tudelft.context.graph.Node;
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
     * @param graph The graph that will be checked for mutations
     */
    public MutationParser(final Graph graph) {

        this.graph = graph;
        this.variations = new LinkedList<>();

    }

    /**
     * The function that creates the mutations.
     */
    public void checkMutations() {

        Set<Node> nodeSet = graph.vertexSet().stream()
                .filter(node -> graph.outDegreeOf(node) > 1)
                .collect(Collectors.toSet());

        System.out.println("nodeSet: " + nodeSet.toString());

        nodeSet.forEach(this::checkVariation);

        System.out.println("Variations: " + variations.stream().map(Node::getId).collect(Collectors.toList()));

    }

    /**
     * Recursively checks a variation from start till end.
     * @param startNode the node where a variation starts.
     */
    public void checkVariation(final Node startNode) {

        List<Node> nextNodes =  new LinkedList<>();

        Set<DefaultEdge> listEdges = graph.outgoingEdgesOf(startNode);
        nextNodes.addAll(listEdges.stream().map(graph::getEdgeTarget).collect(Collectors.toList()));

        variations.addAll(nextNodes.stream().collect(Collectors.toList()));

        System.out.println("nextNodes: " + nextNodes);
        System.out.println("variations: "+ variations);

        Iterator iterator = nextNodes.iterator();

        List<List<Node>> listSets = new LinkedList();
        for(int i = 0; i < graph.outDegreeOf(startNode); i++) {

            listSets.add(new LinkedList<>());

        }

        int set = 0;

        while (iterator.hasNext()) {

            Node node = (Node) iterator.next();
            if (graph.outDegreeOf(node) > 1) {

                checkVariation(node);

            }

            if (checkAllSets(listSets.iterator(), node)) {

                break;

            } else {

                listSets.get(set).add(node);
                set++;
                if (set > graph.outDegreeOf(startNode)) {
                    set = 0;
                }

                List<Node> listInsert = new LinkedList();
                listInsert.add(node);
                nextNodes.addAll(graph.nextColumn(listInsert));

            }

        }

    }

    public void printVariations() {
        System.out.println("Amount of variations: " + variations.size());
        System.out.println(variations.toString());
    }

    public boolean checkAllSets(Iterator iterator, Node node) {

        if (iterator.hasNext()) {

           if (((List<Node>) iterator.next()).contains(node)) {

               return checkAllSets(iterator, node);

           } else {

               return false;

           }

        } else {

            return true;

        }
    }

}
