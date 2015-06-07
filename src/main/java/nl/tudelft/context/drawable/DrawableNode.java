package nl.tudelft.context.drawable;

import nl.tudelft.context.controller.AbstractGraphController;
import nl.tudelft.context.controller.MainController;
import nl.tudelft.context.model.graph.Node;

/**
 * @author Gerben Oolbekkink <g.j.w.oolbekkink@gmail.com>
 * @version 1.0
 * @since 7-6-2015
 */
public class DrawableNode extends AbstractDrawableNode {
    public DrawableNode(Node node) {
        super(node);
    }

    @Override
    public DefaultLabel getLabel(MainController mainController, AbstractGraphController graphController) {
        return new InfoLabel(mainController, graphController, this, (Node) getNode());
    }


}
