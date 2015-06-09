package nl.tudelft.context.drawable;

import nl.tudelft.context.model.newick.Node;

/**
 * @author Gerben Oolbekkink <g.j.w.oolbekkink@gmail.com>
 * @version 1.0
 * @since 8-6-2015
 */
public class DrawableNewickNode extends DrawablePosition {

    public DrawableNewickNode(Node node) {
        this.translateXProperty().bind(node.translateXProperty());
        this.translateYProperty().bind(node.translateYProperty());
    }
}
