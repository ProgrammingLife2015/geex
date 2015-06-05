package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import nl.tudelft.context.drawable.DrawableGraph;
import nl.tudelft.context.model.graph.StackGraph;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 04-06-2015
 */
public final class LocatorController {

    /**
     * The locator of the graph.
     */
    @FXML
    Pane locator;

    /**
     * The ScrollPane with the graph.
     */
    final ScrollPane scroll;

    /**
     * The graph.
     */
    final DrawableGraph graph;

    /**
     * The length of the locator.
     */
    final int length = 1;

    /**
     * The locator of the graph.
     */
    Line locatorIndicator;

    /**
     * Construct the LocatorController.
     *
     * @param locator The locator pane.
     * @param scroll  The scroll pane.
     * @param graph   The list with labels.
     */
    public LocatorController(final Pane locator, final ScrollPane scroll, final DrawableGraph graph) {

        this.locator = locator;
        this.scroll = scroll;
        this.graph = graph;

        locatorIndicator = new Line(0, 0, 0, 0);
        locatorIndicator.setStroke(Color.RED);
        locator.getChildren().add(locatorIndicator);
        locator.heightProperty().addListener(event -> locatorIndicator.setEndY(locator.getHeight() - 1));

        scroll.hvalueProperty().addListener(event -> setPosition(scroll.getHvalue()));

    }

    /**
     * Sets the length of the locator.
     *
     * @param graph The graph.
     */
    public void setLength(final DrawableGraph graph) {

        int sum = graph.getMaxLength();
    }

    /**
     * Sets the position of the indicator.
     *
     * @param ratio The ratio.
     */
    public void setPosition(final double ratio) {
        locatorIndicator.setStartX(ratio * locator.getWidth());
        locatorIndicator.setEndX(ratio * locator.getWidth());
    }
}
