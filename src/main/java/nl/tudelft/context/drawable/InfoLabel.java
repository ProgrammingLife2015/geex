package nl.tudelft.context.drawable;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
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
     * The height of the InfoLabel.
     */
    public static final int LABEL_HEIGHT = 55;

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
        label.setOnMouseMoved(event -> mouseOver(event, label));
        label.setOnMouseExited(event -> addWidth(0, label));

        return label;

    }

    private void mouseOver(MouseEvent event, Label label) {
        double dx = event.getX() - (label.getWidth() / 2);
        double dy = event.getY() - (label.getHeight() / 2);
        double distance = Math.sqrt(dx * dx + dy * dy);
        distance = Math.max(0, distance - 10);
        int width = (int) (20 * Math.pow(Math.E, -(Math.pow(distance / 10, 2))));
        addWidth(width, label);
    }

    private void addWidth(int width, Label label) {
        label.translateXProperty().setValue(-width / 2);
        label.translateYProperty().setValue(-width / 2);
        label.setStyle("-fx-min-height: " + (LABEL_HEIGHT + width) + "px;"
                + "-fx-min-width: " + (LABEL_WIDTH + width) + "px;");
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
