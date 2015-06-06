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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    Map<Integer, List<Integer>> totalMap = new HashMap<>();

    /**
     * Location boundaries.
     */
    int minLocation, maxLocation;

    /**
     * Maximum of ref positions.
     */
    int maxRefPosition;

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

        TreeMap<Integer, List<Integer>> treeMap = labelsMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> Arrays.asList(entry.getValue().stream()
                                .map(DefaultLabel::getNode)
                                .mapToInt(DefaultNode::getRefStartPosition)
                                .min().getAsInt(), entry.getValue().stream()
                                .map(DefaultLabel::getNode)
                                .mapToInt(DefaultNode::getRefEndPosition)
                                .max().getAsInt()),
                        (a, b) -> a,
                        TreeMap::new
                ));

        maxRefPosition = 0;
        for (Map.Entry<Integer, List<Integer>> entry : treeMap.entrySet()) {
            List<Integer> current = entry.getValue();
            totalMap.put(entry.getKey(), current);
            maxRefPosition = Math.max(maxRefPosition, current.get(1));
        }

        minLocation = treeMap.firstKey();
        maxLocation = treeMap.lastKey();

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

        List<List<Integer>> list = IntStream.rangeClosed(start, end)
                .mapToObj(totalMap::get)
                .collect(Collectors.toList());

        int min = list.stream()
                .mapToInt(x -> x.get(0))
                .min().getAsInt();

        int max = list.stream()
                .mapToInt(x -> x.get(1))
                .max().getAsInt();

        double scale = locator.getWidth() / maxRefPosition;

        locatorIndicator.setTranslateX(min * scale);
        locatorIndicator.setWidth((max - min) * scale);

    }
}
