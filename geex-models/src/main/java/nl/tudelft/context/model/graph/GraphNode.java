package nl.tudelft.context.model.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * @author René Vennik
 * @version 1.0
 * @since 1-6-2015
 */
public class GraphNode extends DefaultNode {

    /**
     * Nodes of sub graph.
     */
    Set<DefaultNode> nodes = new HashSet<>();

    /**
     * Create a graph node.
     *
     * @param graph Master graph
     * @param start Start of sub graph
     * @param end   End of sub graph
     */
    public GraphNode(final StackGraph graph, final DefaultNode start, final DefaultNode end) {

        sources = start.getSources();

        Queue<DefaultNode> queue = new LinkedList<>();
        queue.add(start);
        nodes.add(start);

        while (!queue.isEmpty()) {

            DefaultNode node = queue.remove();

            graph.getTargets(node).stream()
                    .filter(n -> !nodes.contains(n) && !n.equals(end))
                    .forEach(n -> {
                        queue.add(n);
                        nodes.add(n);
                    });

        }

        content = Integer.toString(nodes.size());

    }

    /**
     * Get nodes of sub graph.
     *
     * @return Nodes of sub graph
     */
    public Set<DefaultNode> getNodes() {
        return nodes;
    }
}
