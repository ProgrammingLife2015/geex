package nl.tudelft.context.drawable.graph;

import nl.tudelft.context.model.graph.DefaultNode;
import nl.tudelft.context.model.graph.GraphNode;
import nl.tudelft.context.model.graph.Node;

/**
 * Create a Drawable from a Node.
 *
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 7-6-2015
 */
public final class DrawableNodeFactory {
    /**
     * Private constructor for utility class.
     */
    private DrawableNodeFactory() {

    }

    /**
     * Make a DefaultNode of any type a proper Drawable.
     *
     * @param node Node to make drawable.
     * @return A DrawableNode of the specific Node type.
     */
    public static AbstractDrawableNode create(final DefaultNode node) {
        if (node instanceof GraphNode) {
            return new DrawableGraphNode((GraphNode) node);
        }
        if (node instanceof Node) {
            return new DrawableNode((Node) node);
        }

        throw new RuntimeException("Could not create DrawableNode of type: " + node.getClass());
    }
}
