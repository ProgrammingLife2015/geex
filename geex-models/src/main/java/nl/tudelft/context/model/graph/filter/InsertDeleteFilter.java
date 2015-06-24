package nl.tudelft.context.model.graph.filter;

import nl.tudelft.context.model.graph.DefaultNode;
import nl.tudelft.context.model.graph.GraphNode;
import nl.tudelft.context.model.graph.StackGraph;

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
public class InsertDeleteFilter implements StackGraphFilter {

    /**
     * Parts of a insert deletion.
     */
    private Set<DefaultNode> inDelPart = new HashSet<>();

    /**
     * Map with start and end of insert deletions.
     */
    private Map<DefaultNode, DefaultNode> inDel = new HashMap<>();

    /**
     * Filtered and input graph.
     */
    private StackGraph filtered, previous;

    /**
     * Create a graph with insert deletes single point mutations based on an other graph.
     *
     * @param graph Graph to calculate insert deletes on
     */
    public InsertDeleteFilter(final StackGraph graph) {
        this.previous = graph;
        this.filtered = graph.deepClone();
    }

    @Override
    public StackGraph getFilterGraph() {
        markInDel();
        shift();
        replaceInsertDeletes();

        return filtered;
    }


    /**
     * Mark the insert deletes.
     */
    private void markInDel() {

        filtered.vertexSet().stream()
                .forEach(startNode -> {

                    List<DefaultNode> targets = filtered.getTargets(startNode);

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
        List<DefaultNode> ends = filtered.getTargets(part);
        if (ends.size() == 1
                && ends.get(0).equals(end)
                && filtered.inDegreeOf(part) == 1
                && filtered.inDegreeOf(end) == 2) {
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

        inDelPart.forEach(filtered::removeVertex);
        inDel.forEach((start, end) -> {
            filtered.setEdgeWeight(
                    filtered.getEdge(start, filtered.getTargets(start).get(0)),
                    previous.outgoingEdgesOf(end).stream().mapToDouble(previous::getEdgeWeight).sum()
            );
            filtered.replace(start, new GraphNode(previous, start, end, "insert-delete"));
        });

    }
}
