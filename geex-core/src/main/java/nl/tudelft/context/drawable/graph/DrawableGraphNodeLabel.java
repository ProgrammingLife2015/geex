package nl.tudelft.context.drawable.graph;

import javafx.beans.property.ObjectProperty;
import javafx.scene.CacheHint;
import javafx.scene.control.Label;
import nl.tudelft.context.controller.AbstractGraphController;
import nl.tudelft.context.controller.MainController;
import nl.tudelft.context.controller.SubGraphController;
import nl.tudelft.context.model.graph.GraphNode;

import java.util.Set;

/**
 * The Javafx part of DrawableGraphNode.
 *
 * @author René Vennik
 * @version 1.0
 * @since 1-6-2015
 */
public class DrawableGraphNodeLabel extends AbstractLabel {

    /**
     * Constructor for the single point mutation label.
     *
     * @param mainController       MainController indicating the controller
     * @param graphController      GraphController to place the next view on
     * @param abstractDrawableNode Node indicating drawable
     * @param node                 Node indicating the node
     * @param selectedSources      Property containing the current sources
     */
    public DrawableGraphNodeLabel(final MainController mainController, final AbstractGraphController graphController,
                                  final AbstractDrawableNode abstractDrawableNode, final GraphNode node,
                                  final ObjectProperty<Set<String>> selectedSources) {

        super(node);

        selectedSources.addListener((observable, oldValue, newValue) -> updateSources(newValue));
        updateSources(selectedSources.get());

        setCache(true);
        setCacheHint(CacheHint.SCALE);
        translateXProperty().bind(abstractDrawableNode.translateXProperty());
        translateYProperty().bind(abstractDrawableNode.translateYProperty());

        setOnMouseClicked(event -> mainController.setView(graphController,
                new SubGraphController(mainController, graphController.getGraphList().getFirst(), node)));

        init();

    }

    /**
     * Draw sub elements.
     */
    private void init() {

        getChildren().add(initMainLabel());

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

    @Override
    public boolean equals(final Object other) {
        return other instanceof DrawableGraphNodeLabel && node.equals(((DrawableGraphNodeLabel) other).node);
    }

    @Override
    public int hashCode() {
        return node.hashCode();
    }

}
