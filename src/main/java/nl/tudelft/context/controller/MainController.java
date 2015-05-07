package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 25-4-2015
 */
public class MainController extends DefaultController<BorderPane> implements Initializable {

    @FXML
    protected ScrollPane view;

    @FXML
    protected VBox control;

    protected Stack<Node> viewList = new Stack<>();

    /**
     * Init a controller at main.fxml.
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

        control.getChildren().add(new LoadGraphController(this).getRoot());
        control.getChildren().add(new LoadNewickController().getRoot());

        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE)
                previousView();
        });

    }

    /**
     * Set a new main view and push it on the view stack.
     *
     * @param node javaFX element
     */
    public void setView(Node node) {

        viewList.add(node);
        view.setContent(node);

    }

    /**
     * Set the previous view as view.
     */
    public void previousView() {

        if(viewList.size() > 0) {
            viewList.pop();
            view.setContent(viewList.peek());
        }

    }

}
