package nl.tudelft.context.model.graph;

import nl.tudelft.context.controller.GraphController;
import nl.tudelft.context.controller.MainController;
import nl.tudelft.context.drawable.DefaultLabel;
import nl.tudelft.context.drawable.DrawableNode;
import nl.tudelft.context.drawable.SinglePointLabel;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 1-6-2015
 */
public class GraphNode extends DefaultNode {

    /**
     * Create a graph node.
     *
     * @param graph Master graph
     * @param start Start of sub graph
     * @param end   End of sub graph
     */
    public GraphNode(final StackGraph graph, final DefaultNode start, final DefaultNode end) {

        sources = start.getSources();

        Set<DefaultNode> visited = new HashSet<>();
        visited.add(end);
        Queue<DefaultNode> queue = new LinkedList<>();
        queue.add(start);

        while (!queue.isEmpty()) {

            DefaultNode node = queue.remove();
            graph.getTargets(node).stream()
                    .filter(n -> !visited.contains(n))
                    .forEach(n -> {
                        queue.add(n);
                        visited.add(n);
                    });

        }

        content = Integer.toString(visited.size());

    }

    @Override
    public DefaultLabel getLabel(final MainController mainController, final GraphController graphController,
                                 final DrawableNode drawableNode) {
        return new SinglePointLabel(mainController, graphController, drawableNode, this);
    }

}
