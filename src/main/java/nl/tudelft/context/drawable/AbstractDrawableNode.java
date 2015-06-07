package nl.tudelft.context.drawable;

import nl.tudelft.context.controller.DefaultGraphController;
import nl.tudelft.context.controller.MainController;
import nl.tudelft.context.model.graph.DefaultNode;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 1-5-2015
 */
public abstract class AbstractDrawableNode extends DrawablePosition {

    /**
     * Node that is drawn.
     */
    protected DefaultNode node;

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
    public AbstractDrawableNode(final DefaultNode node) {

        this.node = node;

    }

    /**
     * Get the node that is drawn.
     *
     * @return Node that is drawn.
     */
    public DefaultNode getNode() {

        return node;

    }

    public abstract DefaultLabel getLabel(MainController mainController, DefaultGraphController graphController);

    /**
     * Check if nodes are equal.
     *
     * @param other Other object
     * @return If other object has the same node
     */
    @Override
    public boolean equals(final Object other) {
        if (other instanceof AbstractDrawableNode) {
            AbstractDrawableNode that = (AbstractDrawableNode) other;
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