package nl.tudelft.context.drawable.graph;

import javafx.scene.layout.VBox;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 1-6-2015
 */
public abstract class AbstractLabel extends VBox {

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

}
