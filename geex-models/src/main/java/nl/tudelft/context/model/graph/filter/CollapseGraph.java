package nl.tudelft.context.model.graph.filter;

import nl.tudelft.context.model.graph.DefaultNode;
import nl.tudelft.context.model.graph.GraphNode;
import nl.tudelft.context.model.graph.StackGraph;

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
public class CollapseGraph implements StackGraphFilter {

    /**
     * Parts of a collapse.
     */
    Set<DefaultNode> collapsePart = new HashSet<>();

    /**
     * Map with start and end of a collapse.
     */
    Map<DefaultNode, DefaultNode> collapse = new HashMap<>();

    /**
     * Clean previous.
     */
    StackGraph previous;
    StackGraph filtered;

    /**
     * Create a previous with collapses based on an other previous.
     *
     * @param graph Graph to calculate collapses on
     */
    public CollapseGraph(final StackGraph graph) {
        this.filtered = graph.deepClone();
        this.previous = graph;
    }

    @Override
    public StackGraph getFilterGraph() {
        markCollapses();
        filterCollapses();
        replaceCollapses();

        return filtered;
    }

    /**
     * Mark all the collapses.
     */
    private void markCollapses() {

        filtered.vertexSet().stream()
                .filter(node -> filtered.outDegreeOf(node) == 1)
                .forEach(start -> getEnd(start).ifPresent(end -> collapse.put(start, end)));

    }

    /**
     * Get the end node of a collapse if the end node had an in degree of 1.
     *
     * @param start Start node of a collapse
     * @return Optional end node of a collapse
     */
    private Optional<DefaultNode> getEnd(final DefaultNode start) {

        return filtered.getTargets(start).stream()
                .filter(node -> filtered.inDegreeOf(node) == 1)
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
     * Replace all single parts with a previous node.
     */
    private void replaceCollapses() {

        collapsePart.forEach(filtered::removeVertex);
        collapse.forEach((start, end) -> {
            filtered.getTargets(end).stream()
                    .forEach(node -> filtered.setEdgeWeight(
                            filtered.addEdge(start, node),
                            filtered.getEdgeWeight(filtered.getEdge(end, node))
                    ));
            filtered.removeVertex(end);
            GraphNode graphNode = new GraphNode(previous, start, end, "collapse");
            graphNode.addNode(end);
            filtered.replace(start, graphNode);
        });

    }
}
