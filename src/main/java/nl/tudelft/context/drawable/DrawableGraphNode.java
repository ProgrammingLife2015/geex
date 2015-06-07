package nl.tudelft.context.drawable;

import nl.tudelft.context.controller.AbstractGraphController;
import nl.tudelft.context.controller.MainController;
import nl.tudelft.context.model.graph.GraphNode;

/**
 * @author Gerben Oolbekkink <g.j.w.oolbekkink@gmail.com>
 * @version 1.0
 * @since 7-6-2015
 */
public class DrawableGraphNode extends AbstractDrawableNode {
    public DrawableGraphNode(GraphNode node) {
        super(node);
    }

    @Override
    public DefaultLabel getLabel(MainController mainController, AbstractGraphController graphController) {
        return new DrawableGraphNodeLabel(mainController, graphController, this, (GraphNode) getNode());
    }
}
