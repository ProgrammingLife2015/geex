package nl.tudelft.context.drawable.graph;

import javafx.beans.property.ObjectProperty;
import nl.tudelft.context.controller.AbstractGraphController;
import nl.tudelft.context.controller.MainController;
import nl.tudelft.context.model.graph.Node;

import java.util.Set;

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
    }

    @Override
    public AbstractLabel getLabel(final MainController mainController,
                                  final AbstractGraphController graphController,
                                  final ObjectProperty<Set<String>> selectedSources) {

        return new DrawableNodeLabel(mainController, graphController, this, (Node) getNode(), selectedSources);
    }
}
