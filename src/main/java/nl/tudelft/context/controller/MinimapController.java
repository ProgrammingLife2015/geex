package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 08-05-2015
 */
public class MinimapController extends DefaultController<BorderPane> implements Initializable {
    private final MainController mainController;

    @FXML
    private Group minimap;

    /**
     * Init a controller at minimap.fxml
     *
     * @param mainController    main controller to set view
     */
    public MinimapController(MainController mainController) {
        super(new BorderPane());

        this.mainController = mainController;

        loadFXML("/application/minimap.fxml");
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Rectangle rectangle = new Rectangle(100, 100, Color.web("white"));
        rectangle.setOnMouseClicked(event -> mainController.cycleViews());
        minimap.getChildren().add(rectangle);
    }
}
