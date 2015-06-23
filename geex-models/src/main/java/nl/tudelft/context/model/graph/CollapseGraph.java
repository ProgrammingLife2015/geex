package nl.tudelft.context.model.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author René Vennik
 * @version 1.0
 * @since 11-6-2015
 */
public class CollapseGraph extends StackGraph {

    /**
     * Parts of a collapse.
     */
    transient Set<DefaultNode> collapsePart = new HashSet<>();

    /**
     * Map with start and end of a collapse.
     */
    transient Map<DefaultNode, DefaultNode> collapse = new HashMap<>();

    /**
     * Clean graph.
     */
    StackGraph graph;

    /**
     * Create a graph with collapses based on an other graph.
     *
     * @param graph Graph to calculate collapses on
     */
    public CollapseGraph(final StackGraph graph) {

        setGraph(graph);
        this.graph = graph;

        markCollapses();
        filterCollapses();
        replaceCollapses();

    }

    /**
     * Mark all the collapses.
     */
    private void markCollapses() {

        vertexSet().stream()
                .filter(node -> outDegreeOf(node) == 1)
                .forEach(start -> getEnd(start).ifPresent(end -> collapse.put(start, end)));

    }

    /**
     * Get the end node of a collapse if the end node had an in degree of 1.
     *
     * @param start Start node of a collapse
     * @return Optional end node of a collapse
     */
    private Optional<DefaultNode> getEnd(final DefaultNode start) {

        return getTargets(start).stream()
                .filter(node -> inDegreeOf(node) == 1)
                .findFirst();

    }

    /**
     * Remove duplicates in collapses start & and.
     */
    private void filterCollapses() {

        Map<DefaultNode, DefaultNode> newSingle = new HashMap<>();

        Set<DefaultNode> overlay = new HashSet<>(collapse.keySet());
        overlay.retainAll(collapse.values());

        collapse.keySet().stream()
                .filter(node -> !overlay.contains(node))
                .forEach(startNode -> {
                    DefaultNode endNode = startNode;
                    while (collapse.containsKey(endNode)) {
                        endNode = collapse.get(endNode);
                    }
                    newSingle.put(startNode, endNode);
                });

        collapse = newSingle;
        collapsePart.addAll(overlay);

    }

    /**
     * Replace all single parts with a graph node.
     */
    private void replaceCollapses() {

        collapsePart.forEach(this::removeVertex);
        collapse.forEach((start, end) -> {
            getTargets(end).stream()
                    .forEach(node -> setEdgeWeight(
                            addEdge(start, node),
                            getEdgeWeight(getEdge(end, node))
                    ));
            removeVertex(end);
            GraphNode graphNode = new GraphNode(graph, start, end, "collapse");
            graphNode.addNode(end);
            replace(start, graphNode);
        });

    }

}
