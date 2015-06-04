package nl.tudelft.context.model.graph;

import nl.tudelft.context.controller.DefaultGraphController;
import nl.tudelft.context.controller.MainController;
import nl.tudelft.context.drawable.DefaultLabel;
import nl.tudelft.context.drawable.DrawableNode;
import nl.tudelft.context.drawable.VariationLabel;

/**
 * @author Jim
 * @since 6/4/2015
 */
public class VarNode extends DefaultNode {

    DefaultNode node;

    public VarNode(final DefaultNode node) {
        this.node = node;
    }

    public DefaultNode getNode() {
        return node;
    }

    @Override
    public DefaultLabel getLabel(final MainController mainController, final DefaultGraphController graphController,
                                 final DrawableNode drawableNode) {
        return new VariationLabel(mainController, graphController, drawableNode, (Node) node);
    }

}
