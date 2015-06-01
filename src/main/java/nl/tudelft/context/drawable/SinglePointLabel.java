package nl.tudelft.context.drawable;

import javafx.scene.control.Label;
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
     * @param drawableNode    Node indicating drawable
     * @param node            Node indicating the node
     */
    public SinglePointLabel(final DrawableNode drawableNode, final GraphNode node) {

        this.node = node;

        setCache(true);
        getStyleClass().add("single");
        translateXProperty().bind(drawableNode.translateXProperty());
        translateYProperty().bind(drawableNode.translateYProperty());

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

        final Label label = new Label();
        label.setCache(true);

        return label;

    }

}
