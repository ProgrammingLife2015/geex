package nl.tudelft.context.drawable;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import nl.tudelft.context.controller.BaseController;
import nl.tudelft.context.controller.GraphController;
import nl.tudelft.context.controller.MainController;
import nl.tudelft.context.model.graph.BaseCounter;
import nl.tudelft.context.model.graph.Graph;
import nl.tudelft.context.model.graph.Node;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 13-5-2015
 */
public class InfoLabel extends VBox {

    /**
     * The node the InfoLabel belongs to.
     */
    Node node;

    /**
     * The width of the InfoLabel.
     */
    public static final int LABEL_WIDTH = 60;

    /**
     * Constructor for the InfoLabel.
     *
     * @param mainController  MainController indicating the controller
     * @param graphController GraphController to place the next view on
     * @param graph           Graph containing the node
     * @param node            Node indicating the node
     */
    public InfoLabel(final MainController mainController, final GraphController graphController,
                     final Graph graph, final Node node) {

        this.node = node;

        setCache(true);
        translateXProperty().bind(node.translateXProperty());
        translateYProperty().bind(node.translateYProperty());

        setOnMouseClicked(event -> mainController.setView(graphController, new BaseController(graph, node)));

    }

    /**
     * Draw sub elements when needed.
     */
    public final void init() {

        getChildren().addAll(
                initMainLabel(),
                initBaseLabels()
        );

    }

    /**
     * Get the current column the label is displayed.
     *
     * @return Column index
     */
    public int currentColumn() {

        return (int) translateXProperty().get() / Graph.LABEL_SPACING;

    }

    /**
     * Initialize the Label without the BaseLabels shown.
     *
     * @return Initialized Upper label
     */
    private Label initMainLabel() {

        final Label label = new Label(Integer.toString(node.getId()));
        label.setCache(true);

        final Tooltip percentages = new Tooltip(node.getBaseCounter().toString());
        label.setTooltip(percentages);

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

}
