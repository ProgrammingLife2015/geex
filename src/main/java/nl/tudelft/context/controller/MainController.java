package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 25-4-2015
 */
public class MainController extends DefaultController<BorderPane> implements Initializable {

    @FXML
    protected ScrollPane scrollPane;
    @FXML
    protected ProgressIndicator progressIndicator;
    @FXML
    protected HBox ruler;
    @FXML
    protected GridPane sequences;

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

        root.setLeft(new LoadGraphController(progressIndicator, ruler, sequences).getRoot());
        reverseScroll();

    }

    /**
     * On vertical scroll, make it horizontal.
     */
    protected void reverseScroll() {

        root.setOnScroll(event -> {
            final double displacement = event.getDeltaY() / scrollPane.getContent().getBoundsInLocal().getWidth();
            scrollPane.setHvalue(scrollPane.getHvalue() - displacement);
        });

    }

}