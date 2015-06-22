package nl.tudelft.context.model.graph;

import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author René Vennik
 * @version 1.0
 * @since 21-6-2015
 */
public class BubbleReduction {

    Set<DefaultNode> startNodes;
    Set<DefaultNode> endNodes;

    /**
     * Graph to reduce.
     */
    StackGraph stackGraph;

    /**
     * Init a bubble reduction filter.
     *
     * @param stackGraph Graph to filter
     */
    public BubbleReduction(final StackGraph stackGraph) {

        this.stackGraph = stackGraph;

        startNodes = getBubblePositions(node -> stackGraph.inDegreeOf(node) == 1);
        endNodes = getBubblePositions(node -> stackGraph.outDegreeOf(node) == 1);

    }

    public Set<DefaultNode> getBubblePositions(final Predicate<DefaultNode> bubbleNodeFilter) {

        return stackGraph.vertexSet().stream()
                .filter(bubbleNodeFilter)
                .collect(Collectors.toSet());

    }

    /**
     * Apply a filter function.
     *
     * @param filterFunction Function to determine if a node should be kept
     */
    public void applyFilter(final Predicate<DefaultNode> filterFunction) {

    }

    private Set<DefaultNode> getNodes() {

        return stackGraph.vertexSet();

    }

    private Set<DefaultWeightedEdge> getEdges() {

        return stackGraph.edgeSet();

    }

    public void fillGraph(StackGraph graph) {

        getNodes().stream()
                .forEach(graph::addVertex);

        getEdges().stream()
                .forEach(edge -> graph.setEdgeWeight(graph.addEdge(
                        stackGraph.getEdgeSource(edge),
                        stackGraph.getEdgeTarget(edge)
                ), stackGraph.getEdgeWeight(edge)));

    }
}
