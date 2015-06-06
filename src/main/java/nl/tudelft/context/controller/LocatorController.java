package nl.tudelft.context.controller;

import javafx.beans.property.ObjectProperty;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import nl.tudelft.context.drawable.DefaultLabel;
import nl.tudelft.context.drawable.Location;
import nl.tudelft.context.model.graph.DefaultNode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 04-06-2015
 */
public final class LocatorController {

    /**
     * The locator of the graph.
     */
    Pane locator;

    /**
     * The locator of the graph.
     */
    Rectangle locatorIndicator;

    /**
     * Height of the locator.
     */
    private static final int LOCATOR_HEIGHT = 43;

    /**
     * The ScrollPane with the graph.
     */
    final ScrollPane scroll;

    /**
     * Total distance form left in a map.
     */
    Map<Integer, List<Integer>> totalMap = new HashMap<>();

    /**
     * Location boundaries.
     */
    int minLocation = Integer.MAX_VALUE, maxLocation = Integer.MIN_VALUE;

    /**
     * Minimum and maximum of ref positions.
     */
    int minRefPosition = Integer.MAX_VALUE, maxRefPosition = Integer.MIN_VALUE;

    /**
     * Construct the LocatorController.
     *
     * @param locator          The locator pane
     * @param scroll           The scroll pane
     * @param locationProperty Location property of view
     * @param labelMap         The map with labels
     */
    public LocatorController(final Pane locator, final ScrollPane scroll,
                             final ObjectProperty<Location> locationProperty,
                             final Map<Integer, List<DefaultLabel>> labelMap) {

        this.locator = locator;
        this.scroll = scroll;

        initIndicator();
        initLabelMap(labelMap);

        locator.widthProperty().addListener(event -> setPosition(locationProperty.get()));
        locationProperty.addListener((observable, oldValue, newValue) -> {
            setPosition(newValue);
        });
        setPosition(locationProperty.get());

    }

    /**
     * Init indicator.
     */
    private void initIndicator() {

        locatorIndicator = new Rectangle();
        locatorIndicator.setHeight(LOCATOR_HEIGHT);
        locatorIndicator.setTranslateY(2);
        locator.getChildren().setAll(locatorIndicator);

    }

    /**
     * Init label map.
     *
     * @param labelMap Map to locate
     */
    private void initLabelMap(final Map<Integer, List<DefaultLabel>> labelMap) {

        labelMap.forEach((column, labels) -> {
            int min = labels.stream()
                    .map(DefaultLabel::getNode)
                    .mapToInt(DefaultNode::getRefStartPosition)
                    .min().getAsInt();
            int max = labels.stream()
                    .map(DefaultLabel::getNode)
                    .mapToInt(DefaultNode::getRefEndPosition)
                    .max().getAsInt();
            totalMap.put(column, Arrays.asList(min, max));
            minRefPosition = Math.min(minRefPosition, min);
            maxRefPosition = Math.max(maxRefPosition, max);
            minLocation = Math.min(minLocation, column);
            maxLocation = Math.max(maxLocation, column);
        });

    }

    /**
     * Sets the position of the indicator.
     *
     * @param location Location of the view
     */
    public void setPosition(final Location location) {

        int start = Math.max(minLocation, location.getStart());
        int end = Math.min(maxLocation, location.getEnd());

        List<List<Integer>> list = IntStream.rangeClosed(start, end)
                .mapToObj(totalMap::get)
                .collect(Collectors.toList());

        int min = list.stream()
                .mapToInt(x -> x.get(0))
                .min().getAsInt() - minRefPosition;

        int max = list.stream()
                .mapToInt(x -> x.get(1))
                .max().getAsInt();

        double scale = locator.getWidth() / maxRefPosition;

        locatorIndicator.setTranslateX(min * scale);
        locatorIndicator.setWidth((max - min) * scale);

    }
}
