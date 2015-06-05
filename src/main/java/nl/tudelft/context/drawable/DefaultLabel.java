package nl.tudelft.context.drawable;

import javafx.scene.layout.VBox;
import nl.tudelft.context.model.graph.DefaultNode;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 1-6-2015
 */
public abstract class DefaultLabel extends VBox {

    /**
     * Draw sub elements when needed.
     */
    public abstract void init();

    /**
     * Get the current column the label is displayed.
     *
     * @return Column index
     */
    public abstract int currentColumn();

    /**
     * Gets the node that belongs to the label.
     *
     * @return The node
     */
    public abstract DefaultNode getNode();

}
