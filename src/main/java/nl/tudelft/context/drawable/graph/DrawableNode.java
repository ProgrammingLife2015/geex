package nl.tudelft.context.drawable.graph;

import nl.tudelft.context.controller.AbstractGraphController;
import nl.tudelft.context.controller.MainController;
import nl.tudelft.context.drawable.VariationLabel;
import nl.tudelft.context.model.graph.GraphNode;
import nl.tudelft.context.model.graph.Node;

/**
 * @author Gerben Oolbekkink <g.j.w.oolbekkink@gmail.com>
 * @version 1.0
 * @since 7-6-2015
 */
public class DrawableNode extends AbstractDrawableNode {
    /**
     * Create a new DrawableNode, used for drawing a Node.
     *
     * @param node Node containing the data.
     */
    public DrawableNode(final Node node) {
        super(node);
    }

    @Override
    public AbstractLabel getLabel(final MainController mainController, final AbstractGraphController graphController) {
        return new DrawableNodeLabel(mainController, graphController, this, (Node) getNode());
    }

    public VariationLabel getVariationLabel(final MainController mainController,
                                            final AbstractGraphController graphController) {
        return new VariationLabel(mainController, graphController, this,(Node) getNode());
    }
}
