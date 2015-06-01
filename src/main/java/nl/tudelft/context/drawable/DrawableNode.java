package nl.tudelft.context.drawable;

import nl.tudelft.context.model.graph.Node;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 1-5-2015
 */
public class DrawableNode extends DrawablePosition {

    /**
     * Node that is drawn.
     */
    private Node node;

    /**
     * The current number of incoming nodes.
     */
    int currentIncoming = 0;

    /**
     * Increment current incoming and return.
     *
     * @return current incoming
     */
    public final int incrementIncoming() {

        return ++currentIncoming;

    }

    /**
     * Create a drawable node.
     *
     * @param node Node to draw
     */
    public DrawableNode(final Node node) {

        this.node = node;

    }

    /**
     * Get the node that is drawn.
     *
     * @return Node that is drawn.
     */
    public Node getNode() {

        return node;

    }

    /**
     * Check if nodes are equal.
     *
     * @param other Other object
     * @return If other object has the same node
     */
    @Override
    public boolean equals(final Object other) {
        if (other instanceof DrawableNode) {
            DrawableNode that = (DrawableNode) other;
            return node.equals(that.node);
        }
        return false;
    }

    /**
     * Creates an hashCode base on node.
     *
     * @return unique hashCode by id
     */
    @Override
    public int hashCode() {
        return node.hashCode();
    }

}
