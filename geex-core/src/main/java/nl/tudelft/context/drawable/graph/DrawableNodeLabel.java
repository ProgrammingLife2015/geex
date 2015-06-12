package nl.tudelft.context.drawable.graph;

import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.control.Label;
import nl.tudelft.context.controller.AbstractGraphController;
import nl.tudelft.context.controller.BaseController;
import nl.tudelft.context.controller.MainController;
import nl.tudelft.context.model.graph.BaseCounter;
import nl.tudelft.context.model.graph.Node;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Javafx part of DrawableNode.
 *
 * @author René Vennik
 * @version 1.0
 * @since 13-5-2015
 */
public class DrawableNodeLabel extends AbstractLabel {

    /**
     * The node the label belongs to.
     */
    Node node;

    /**
     * The width of the InfoLabel.
     */
    public static final int LABEL_WIDTH = 60;

    /**
     * Constructor for the InfoLabel.
     *
     * @param mainController       MainController indicating the controller
     * @param graphController      GraphController to place the next view on
     * @param abstractDrawableNode Node indicating drawable
     * @param node                 Node indicating the node
     */
    public DrawableNodeLabel(final MainController mainController, final AbstractGraphController graphController,
                             final AbstractDrawableNode abstractDrawableNode, final Node node) {

        super(node);
        this.node = node;

        setCache(true);
        setCacheHint(CacheHint.SCALE);
        translateXProperty().bind(abstractDrawableNode.translateXProperty());
        translateYProperty().bind(abstractDrawableNode.translateYProperty());

        setOnMouseClicked(event -> mainController.setView(graphController,
                new BaseController(graphController.getGraphList().getFirst(), node)));

        init();

    }

    /**
     * Draw sub elements.
     */
    private void init() {

        getChildren().addAll(
                initMainLabel(),
                initBaseLabels()
        );

    }

    /**
     * Initialize the Label without the BaseLabels shown.
     *
     * @return Initialized Upper label
     */
    private Label initMainLabel() {

        final Label label = new Label(Integer.toString(node.getContent().length()));
        label.setCache(true);
        label.getStyleClass().add("info-label");

        return label;

    }

    /**
     * Initialize the BaseLabels.
     *
     * @return Initialized base labels
     */
    private Group initBaseLabels() {

        final Group group = new Group();

        final BaseCounter baseCounter = node.getBaseCounter();
        List<BaseLabel> baseLabels = Arrays.asList('A', 'T', 'C', 'G', 'N').stream()
                .map(base -> new BaseLabel(base, baseCounter.getRatio(base) * LABEL_WIDTH))
                .collect(Collectors.toList());

        double left = 0;
        for (BaseLabel baseLabel : baseLabels) {
            baseLabel.setTranslateX(left);
            left += baseLabel.widthProperty().get();
        }

        group.getChildren().addAll(baseLabels);
        return group;

    }

    @Override
    public boolean equals(final Object other) {
        return other instanceof DrawableNodeLabel && node.equals(((DrawableNodeLabel) other).node);
    }

    @Override
    public int hashCode() {
        return node.hashCode();
    }

}
