package nl.tudelft.context.drawable;

import nl.tudelft.context.model.newick.node.AbstractNode;

/**
 * @author Gerben Oolbekkink <g.j.w.oolbekkink@gmail.com>
 * @version 1.0
 * @since 8-6-2015
 */
public class DrawableNewickNode extends DrawablePosition {
    /**
     * Creates a drawable newick node, based on a newick node.
     *
     * @param node The newick node
     */
    public DrawableNewickNode(final AbstractNode node) {
        this.translateXProperty().bind(node.translateXProperty());
        this.translateYProperty().bind(node.translateYProperty());
    }
}
