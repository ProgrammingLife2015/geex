package nl.tudelft.context.controller;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import nl.tudelft.context.drawable.DefaultLabel;
import nl.tudelft.context.drawable.Location;
import nl.tudelft.context.model.graph.DefaultNode;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 04-06-2015
 */
public final class LocatorController extends DefaultController<Pane> {

    /**
     * The locator of the graph.
     */
    @FXML
    Pane locator;

    /**
     * The locator of the graph.
     */
    @FXML
    Rectangle locatorIndicator;

    /**
     * The ScrollPane with the graph.
     */
    final ScrollPane scroll;

    /**
     * Total distance form left in a map.
     */
    Map<Integer, Double> totalMap = new HashMap<>();

    /**
     * Location boundaries.
     */
    int minLocation, maxLocation;

    /**
     * Average total length.
     */
    double totalLength;

    /**
     * Location property.
     */
    ObjectProperty<Location> locationProperty;

    /**
     * Construct the LocatorController.
     *
     * @param locator          The locator pane
     * @param scroll           The scroll pane
     * @param locationProperty Location property of view
     * @param labelsMap        The map with labels
     */
    public LocatorController(final Pane locator, final ScrollPane scroll,
                             final ObjectProperty<Location> locationProperty,
                             final Map<Integer, List<DefaultLabel>> labelsMap) {

        super(locator);

        this.locator = locator;
        this.scroll = scroll;
        this.locationProperty = locationProperty;

        TreeMap<Integer, Double> treeMap = labelsMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(DefaultLabel::getNode)
                                .mapToInt(DefaultNode::getLength)
                                .average().getAsDouble(),
                        (a, b) -> a,
                        TreeMap::new
                ));

        totalLength = 0;
        for (Map.Entry<Integer, Double> entry : treeMap.entrySet()) {
            totalMap.put(entry.getKey(), totalLength);
            totalLength += entry.getValue();
        }

        minLocation = treeMap.firstKey();
        maxLocation = treeMap.lastKey() + 1;
        totalMap.put(maxLocation, totalLength);

        loadFXML("/application/locator.fxml");

        locationProperty.addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> setPosition(newValue));
        });

    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
    }

    /**
     * Sets the position of the indicator.
     *
     * @param location Location of the view
     */
    public void setPosition(final Location location) {

        int start = Math.max(minLocation, location.getStart());
        int end = Math.min(maxLocation, location.getEnd());

        double startPosition = totalMap.get(start) / totalLength;
        double endPosition = totalMap.get(end) / totalLength;

        locatorIndicator.setTranslateX(startPosition * locator.getWidth());
        locatorIndicator.setWidth((endPosition - startPosition) * locator.getWidth());

    }
}
