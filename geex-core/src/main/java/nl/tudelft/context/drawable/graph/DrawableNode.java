package nl.tudelft.context.drawable.graph;

import nl.tudelft.context.controller.AbstractGraphController;
import nl.tudelft.context.controller.MainController;
import nl.tudelft.context.model.graph.Node;
import org.jgrapht.graph.AbstractGraph;

/**
 * @author Gerben Oolbekkink
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
        isVariation = node.getVariation();
    }

    @Override
    public AbstractLabel getLabel(final MainController mainController, final AbstractGraphController graphController) {
        if(isVariation && graphController.getGraphList().get(graphController.getDepth()).getMarkVariations()) {
            return new VariationLabel(mainController, graphController, this, (Node) getNode());
        } else {
            return new DrawableNodeLabel(mainController, graphController, this, (Node) getNode());
        }
    }

}
