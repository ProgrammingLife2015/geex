package nl.tudelft.context.model.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author René Vennik
 * @version 1.0
 * @since 11-6-2015
 */
public class InsertDeleteGraph extends StackGraph {

    /**
     * Parts of a insert deletion.
     */
    transient Set<DefaultNode> inDelPart = new HashSet<>();

    /**
     * Map with start and end of insert deletions.
     */
    transient Map<DefaultNode, DefaultNode> inDel = new HashMap<>();

    /**
     * Clean graph.
     */
    StackGraph graph;

    /**
     * Create a graph with insert deletes single point mutations based on an other graph.
     *
     * @param graph Graph to calculate insert deletes on
     */
    public InsertDeleteGraph(final StackGraph graph) {

        this.graph = graph;
        setGraph(graph);

        markInDel();
        shift();
        replaceInsertDeletes();

    }

    /**
     * Mark the insert deletes.
     */
    private void markInDel() {

        vertexSet().stream()
                .forEach(startNode -> {

                    List<DefaultNode> targets = getTargets(startNode);

                    if (targets.size() != 2) {
                        return;
                    }

                    isInsertDelete(startNode, targets.get(1), targets.get(0));
                    isInsertDelete(startNode, targets.get(0), targets.get(1));

                });

    }

    /**
     * Check if nodes are part of an insert delete and add it to map if so.
     *
     * @param start Possible start node
     * @param end   Possible end node
     * @param part  Possible node between start and end
     */
    public void isInsertDelete(final DefaultNode start, final DefaultNode end, final DefaultNode part) {

        List<DefaultNode> ends = getTargets(part);
        if (ends.size() == 1 && ends.get(0).equals(end) && inDegreeOf(part) == 1 && inDegreeOf(end) == 2) {
            inDelPart.add(part);
            inDel.put(start, end);
        }

    }

    /**
     * Shift in del parts parts.
     */
    private void shift() {

        inDelPart.stream().forEach(DefaultNode::shift);

    }

    /**
     * Replace all insert deletes parts with a graph node.
     */
    private void replaceInsertDeletes() {

        inDelPart.forEach(this::removeVertex);
        inDel.forEach((start, end) -> {
            setEdgeWeight(
                    getEdge(start, getTargets(start).get(0)),
                    graph.outgoingEdgesOf(end).stream().mapToDouble(graph::getEdgeWeight).sum()
            );
            replace(start, new GraphNode(graph, start, end, "insert-delete"));
        });

    }

}
