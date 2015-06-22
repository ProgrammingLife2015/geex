package nl.tudelft.context.model.graph;

import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.Set;
import java.util.function.Function;

/**
 * @author René Vennik
 * @version 1.0
 * @since 21-6-2015
 */
public class BubbleReduction {

    /**
     * Graph to reduce.
     */
    StackGraph stackGraph;

    /**
     * Init a bubble reduction filter.
     *
     * @param stackGraph Graph to filter
     */
    public BubbleReduction(StackGraph stackGraph) {

        this.stackGraph = stackGraph;

    }

    /**
     * Apply a filter function.
     *
     * @param filterFunction Function to determine if a node should be kept
     */
    public void applyFilter(Function<DefaultNode, Boolean> filterFunction) {

    }

    public Set<DefaultNode> getNodes() {

        return stackGraph.vertexSet();

    }

    public Set<DefaultWeightedEdge> getEdges() {

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
