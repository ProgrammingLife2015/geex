package nl.tudelft.context.model.graph;

import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author René Vennik
 * @version 1.0
 * @since 12-6-2015
 */
public class UnknownGraph extends StackGraph {

    /**
     * Unknown nodes (75% or more).
     */
    Set<DefaultNode> unknown;

    /**
     * Minimum unknown ratio to remove.
     */
    public static final double REMOVE_RATIO = .75;

    /**
     * Clean graph.
     */
    StackGraph graph;

    /**
     * Create a graph with filtered unknown on an other graph.
     *
     * @param graph Graph to remove unkowns form.
     */
    public UnknownGraph(final StackGraph graph) {

        this.graph = graph;
        setGraph(graph);

        markUnknown();
        removeEasy();
        removeUnknown();

    }

    /**
     * Mark all the unknown nodes.
     */
    private void markUnknown() {

        unknown = vertexSet().stream()
                .filter(node -> node.getBaseCounter().getRatio('N') >= REMOVE_RATIO)
                .filter(node -> outDegreeOf(node) <= 1 && inDegreeOf(node) <= 1)
                .collect(Collectors.toSet());

    }

    /**
     * Remove nodes that can be remove without having to to anything else.
     */
    private void removeEasy() {

        unknown.removeAll(unknown.stream()
                .filter(node -> outDegreeOf(node) == 0 || inDegreeOf(node) == 0)
                .map(node -> {
                    removeVertex(node);
                    return node;
                }).collect(Collectors.toList()));

    }

    /**
     * Remove all the unknown nodes.
     */
    private void removeUnknown() {

        unknown.stream()
                .forEach(node -> {
                    DefaultNode start = getSources(node).get(0);
                    DefaultNode end = getTargets(node).get(0);
                    DefaultWeightedEdge edge = getEdge(start, end);
                    double weight = getEdgeWeight(getEdge(start, node));
                    if (edge != null) {
                        setEdgeWeight(edge, weight);
                    } else {
                        setEdgeWeight(addEdge(start, end), weight);
                    }
                    this.removeVertex(node);
                });

    }

    @Override
    public String getName() {
        return "Unknown bases (" + (REMOVE_RATIO * 100) + "%)";
    }

}
