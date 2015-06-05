package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import nl.tudelft.context.drawable.DefaultLabel;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

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
    Line locatorIndicator;

    /**
     * The ScrollPane with the graph.
     */
    final ScrollPane scroll;

    /**
     * Label map by column.
     */
    Map<Integer, List<DefaultLabel>> lengthMap;

    /**
     * Construct the LocatorController.
     *
     * @param locator   The locator pane.
     * @param scroll    The scroll pane.
     * @param labelsMap The map with labels.
     */
    public LocatorController(final Pane locator, final ScrollPane scroll, final Map<Integer, List<DefaultLabel>> labelsMap) {

        super(locator);

        this.locator = locator;
        this.scroll = scroll;

        loadFXML("/application/locator.fxml");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        scroll.hvalueProperty().addListener(event -> setPosition(scroll.getHvalue()));

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
