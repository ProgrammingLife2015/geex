package nl.tudelft.context.drawable;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.tudelft.context.controller.BaseController;
import nl.tudelft.context.controller.MainController;
import nl.tudelft.context.graph.BaseCounter;
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

    Node node;

    public static final int LABEL_WIDTH = 60;

    public InfoLabel(MainController mainController, Node node) {

        this.node = node;

        setCache(true);
        translateXProperty().bind(node.translateXProperty());
        translateYProperty().bind(node.translateYProperty());

        setOnMouseClicked(event -> mainController.setView(new BaseController(node.getContent()).getRoot()));

        initMainLabel();
        initBaseLabels();

    }

    private void initMainLabel() {

        final Label label = new Label(Integer.toString(node.getId()));
        label.setCache(true);

        final Tooltip percentages = new Tooltip(node.getBaseCounter().toString());
        label.setTooltip(percentages);

        getChildren().add(label);

    }

    private void initBaseLabels() {

        final HBox hbox = new HBox();

        final BaseCounter basecounter = node.getBaseCounter();
        List<BaseLabel> baseLabels = Arrays.asList('A', 'T', 'C', 'G', 'N').stream()
                .map(base -> new BaseLabel(base, basecounter.getRatio(base)))
                .collect(Collectors.toList());

        baseLabels.get(0).setWidth(
                baseLabels.get(0).getWidth() + LABEL_WIDTH - baseLabels.stream()
                        .mapToInt(nodeLabel -> (int) nodeLabel.getWidth()).sum()
        );

        hbox.getChildren().addAll(baseLabels);
        getChildren().add(hbox);

    }

}
