package nl.tudelft.context.drawable;

import javafx.scene.control.Label;
import nl.tudelft.context.controller.AbstractGraphController;
import nl.tudelft.context.controller.MainController;
import nl.tudelft.context.controller.SubGraphController;
import nl.tudelft.context.model.graph.GraphNode;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 1-6-2015
 */
public class SinglePointLabel extends DefaultLabel {

    /**
     * The node the label belongs to.
     */
    GraphNode node;

    /**
     * Constructor for the single point mutation label.
     *
     * @param mainController       MainController indicating the controller
     * @param graphController      GraphController to place the next view on
     * @param abstractDrawableNode Node indicating drawable
     * @param node                 Node indicating the node
     */
    public SinglePointLabel(final MainController mainController, final AbstractGraphController graphController,
                            final AbstractDrawableNode abstractDrawableNode, final GraphNode node) {

        this.node = node;

        setCache(true);
        translateXProperty().bind(abstractDrawableNode.translateXProperty());
        translateYProperty().bind(abstractDrawableNode.translateYProperty());

        setOnMouseClicked(event -> mainController.setView(graphController,
                new SubGraphController(mainController, graphController.getGraphList().getFirst(), node)));

    }

    @Override
    public final void init() {

        getChildren().add(initMainLabel());

    }

    @Override
    public int currentColumn() {

        return (int) translateXProperty().get() / DrawableGraph.LABEL_SPACING;

    }

    /**
     * Initialize the Label without the BaseLabels shown.
     *
     * @return Initialized Upper label
     */
    private Label initMainLabel() {

        final Label label = new Label(node.getContent());
        label.setCache(true);
        label.getStyleClass().add("single-label");

        return label;

    }

}
