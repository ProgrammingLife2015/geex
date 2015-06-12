package nl.tudelft.context.model.graph;

import java.util.HashSet;
import java.util.Set;

/**
 * @author René Vennik
 * @version 1.0
 * @since 12-6-2015
 */
public class UnknownGraph extends StackGraph {

    /**
     * Unknown nodes (75% or more).
     */
    Set<DefaultNode> unknown = new HashSet<>();

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
        removeUnknown();

    }

    /**
     * Mark all the unknown nodes.
     */
    private void markUnknown() {

        vertexSet().stream()
                .forEach(node -> {

                });

    }

    /**
     * Remove all the unknown nodes.
     */
    private void removeUnknown() {

    }

}
