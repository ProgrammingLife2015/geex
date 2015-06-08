package nl.tudelft.context.drawable.graph;

import nl.tudelft.context.controller.AbstractGraphController;
import nl.tudelft.context.controller.GraphController;
import nl.tudelft.context.controller.MainController;
import nl.tudelft.context.drawable.DrawablePosition;
import nl.tudelft.context.drawable.VariationLabel;
import nl.tudelft.context.model.graph.DefaultNode;
import nl.tudelft.context.model.graph.GraphNode;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 1-5-2015
 */
public abstract class AbstractDrawableNode extends DrawablePosition {

    /**
     * Node that is drawn.
     */
    private DefaultNode node;

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

    /**
     * Get the drawable javafx label for this node.
     *
     * @param mainController Reference to mainController for keybinding and redirection.
     * @param graphController Reference to graphController for redirection.
     * @return Javafx VBox to draw.
     */
    public abstract AbstractLabel getLabel(final MainController mainController,
                                           final AbstractGraphController graphController);

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

    public abstract VariationLabel getVariationLabel(final MainController mainController,
                                            final AbstractGraphController graphController);

}
