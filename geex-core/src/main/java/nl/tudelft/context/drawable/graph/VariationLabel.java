package nl.tudelft.context.drawable.graph;

import javafx.scene.control.Label;
import nl.tudelft.context.controller.AbstractGraphController;
import nl.tudelft.context.controller.MainController;
import nl.tudelft.context.drawable.graph.AbstractDrawableNode;
import nl.tudelft.context.drawable.graph.DrawableGraphNodeLabel;
import nl.tudelft.context.drawable.graph.DrawableNodeLabel;
import nl.tudelft.context.model.graph.GraphNode;
import nl.tudelft.context.model.graph.Node;

/**
 * @author Jim
 * @since 6/4/2015
 */
public class VariationLabel extends DrawableNodeLabel {


    Node node;

    /**
     * Constructor for the InfoLabel.
     *
     * @param node            Node indicating the node
     */
    public VariationLabel(final MainController mainController,
                          final AbstractGraphController graphController,
                          final AbstractDrawableNode abstractNode,
                          final Node node) {

        super(mainController, graphController, abstractNode, node);
        this.node = node;

        init();

    }

    private void init() {

        getChildren().clear();
        getChildren().add(initMainLabel());

    }

    /**
     * Initialize the Label without the BaseLabels shown.
     *
     * @return Initialized Upper label
     */
    private Label initMainLabel() {

        final Label label = new Label("" + node.getId());
        label.setCache(true);
        label.getStyleClass().add("variation-label");

        return label;

    }

}
