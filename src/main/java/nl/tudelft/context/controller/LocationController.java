package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 04-06-2015
 */
public final class LocationController extends ViewController<AnchorPane> {

    /**
     * The locator of the graph.
     */
    @FXML
    Group locator;

    /**
     * Initializes the LocationController.
     *
     * @param root The AnchorPane
     */
    public LocationController(final AnchorPane root) {
        super(root);
    }

    @Override
    public String getBreadcrumbName() {
        return "Locator";
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

    }
}
