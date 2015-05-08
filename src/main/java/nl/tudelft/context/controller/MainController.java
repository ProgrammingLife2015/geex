package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 25-4-2015
 */
public class MainController extends DefaultController<BorderPane> implements Initializable {

    @FXML
    protected VBox control;
    @FXML
    protected ProgressIndicator progressIndicator;
    @FXML
    protected Group sequences;

    /**
     * Init a controller at main.fxml.
     *
     * @throws RuntimeException
     */
    public MainController() {

        super(new BorderPane());

        loadFXML("/application/main.fxml");

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

        progressIndicator.visibleProperty().setValue(false);

        control.getChildren().add(new LoadGraphController(progressIndicator, sequences).getRoot());
        control.getChildren().add(new LoadNewickController(progressIndicator, sequences).getRoot());

    }

}
