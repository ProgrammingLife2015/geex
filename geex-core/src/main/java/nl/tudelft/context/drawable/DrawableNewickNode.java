package nl.tudelft.context.drawable;

import nl.tudelft.context.model.newick.Node;

/**
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 8-6-2015
 */
public class DrawableNewickNode extends DrawablePosition {

    /**
     * Bind the the node to it's position.
     *
     * @param node Node to bind
     */
    public DrawableNewickNode(final Node node) {
        this.translateXProperty().bind(node.translateXProperty());
        this.translateYProperty().bind(node.translateYProperty());
    }

}
