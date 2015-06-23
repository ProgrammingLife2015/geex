package nl.tudelft.context.model.graph.filter;

import nl.tudelft.context.model.graph.DefaultNode;
import nl.tudelft.context.model.graph.StackGraph;
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

    /**
     * Default edge weight for bubble reduction graphs.
     */
    private static final double DEFAULT_WEIGHT = 0.25d;
    /**
     * Graph to reduce.
     */
    StackGraph stackGraph;
    /**
     * Graph to fill.
     */
    StackGraph graph;

    /**
     * Init a bubble reduction filter.
     *
     * @param stackGraph Graph to filter
     * @param graph      Graph to fill
     */
    public BubbleReduction(final StackGraph stackGraph, final StackGraph graph) {

        this.stackGraph = stackGraph;
        this.graph = graph;

        stackGraph.vertexSet().stream()
                .forEach(graph::addVertex);

        stackGraph.edgeSet().stream()
                .forEach(edge -> graph.setEdgeWeight(graph.addEdge(
                        stackGraph.getEdgeSource(edge),
                        stackGraph.getEdgeTarget(edge)
                ), DEFAULT_WEIGHT));

    }

    /**
     * Apply a filter function.
     *
     * @param filterFunction Function to determine if a node should be kept
     */
    public void markBubbles(final Predicate<DefaultNode> filterFunction) {

        Set<DefaultNode> remove = stackGraph.vertexSet().stream()
                .filter(filterFunction)
                .collect(Collectors.toSet());

        remove.stream()
                .forEach(node -> {

                    graph.getSources(node).stream().forEach(source ->
                            graph.getTargets(node).stream().forEach(target -> {
                                DefaultWeightedEdge edge = graph.addEdge(source, target);
                                if (edge != null) {
                                    graph.setEdgeWeight(edge, DEFAULT_WEIGHT);
                                }
                            }));
                    graph.removeVertex(node);

                });

    }

}
