package nl.tudelft.context.drawable.graph;

import javafx.beans.property.ObjectProperty;
import nl.tudelft.context.controller.AbstractGraphController;
import nl.tudelft.context.controller.MainController;
import nl.tudelft.context.model.graph.GraphNode;

import java.util.Set;

/**
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 7-6-2015
 */
public class DrawableGraphNode extends AbstractDrawableNode {
    /**
     * Create a new DrawableGraphNode, used for drawing a GraphNode.
     *
     * @param node Node containing the data.
     */
    public DrawableGraphNode(final GraphNode node) {
        super(node);
    }

    @Override
    public AbstractLabel getLabel(final MainController mainController,
                                  final AbstractGraphController graphController,
                                  final ObjectProperty<Set<String>> selectedSources) {

        return new DrawableGraphNodeLabel(mainController, graphController, this,
                (GraphNode) getNode(), selectedSources);
    }
}
