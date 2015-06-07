package nl.tudelft.context.drawable;

import nl.tudelft.context.model.graph.DefaultNode;
import nl.tudelft.context.model.graph.GraphNode;
import nl.tudelft.context.model.graph.Node;

/**
 * @author Gerben Oolbekkink <g.j.w.oolbekkink@gmail.com>
 * @version 1.0
 * @since 7-6-2015
 */
public class DrawableNodeFactory {
    public static AbstractDrawableNode create(DefaultNode node) {
        if (node instanceof GraphNode) {
            return new DrawableGraphNode((GraphNode) node);
        }
        if (node instanceof Node) {
            return new DrawableNode((Node) node);
        }

        throw new RuntimeException("Could not create DrawableNode of type: " + node.getClass());
    }
}
