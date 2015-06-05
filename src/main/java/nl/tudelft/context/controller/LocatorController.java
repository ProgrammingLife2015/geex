package nl.tudelft.context.controller;

import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import nl.tudelft.context.drawable.DefaultLabel;
import nl.tudelft.context.drawable.Location;
import nl.tudelft.context.model.graph.DefaultNode;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
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
     * Label map by column.
     */
    Map<Integer, Integer> lengthMap;

    /**
     * Construct the LocatorController.
     *
     * @param locator   The locator pane.
     * @param scroll    The scroll pane.
     * @param labelsMap The map with labels.
     */
    public LocatorController(final Pane locator, final ScrollPane scroll,
                             final ObjectProperty<Location> locationProperty,
                             final Map<Integer, List<DefaultLabel>> labelsMap) {

        super(locator);

        this.locator = locator;
        this.scroll = scroll;

        locationProperty.addListener((observable, oldValue, newValue) -> setPosition(newValue));

        lengthMap = labelsMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(DefaultLabel::getNode)
                                .mapToInt(DefaultNode::getLength)
                                .max().getAsInt()
                ));

        loadFXML("/application/locator.fxml");

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

        int start = location.getStart();
        int end = location.getEnd();

        locatorIndicator.setTranslateX(start);
        locatorIndicator.setWidth(end - start);

    }
}
