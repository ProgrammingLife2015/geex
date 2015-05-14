package nl.tudelft.context.drawable;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import nl.tudelft.context.controller.BaseController;
import nl.tudelft.context.controller.MainController;
import nl.tudelft.context.graph.BaseCounter;
import nl.tudelft.context.graph.Graph;
import nl.tudelft.context.graph.Node;

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
     * @param mainController MainController indicating the controller.
     * @param node           Node indicating the node.
     */
    public InfoLabel(final MainController mainController, final Graph graph, final Node node) {

        this.node = node;

        setCache(true);
        translateXProperty().bind(node.translateXProperty());
        translateYProperty().bind(node.translateYProperty());

        setOnMouseClicked(event -> mainController.setView(new BaseController(graph, node).getRoot()));

        initMainLabel();
        initBaseLabels();

    }

    /**
     * Initialise the Label without the BaseLabels shown.
     */
    private void initMainLabel() {

        final Label label = new Label(Integer.toString(node.getId()));
        label.setCache(true);

        final Tooltip percentages = new Tooltip(node.getBaseCounter().toString());
        label.setTooltip(percentages);

        getChildren().add(label);

    }

    /**
     * Initialise the BaseLabels.
     */
    private void initBaseLabels() {

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
        getChildren().add(group);

    }

}
