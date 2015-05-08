package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Stack;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 25-4-2015
 */
public class MainController extends DefaultController<BorderPane> {

    @FXML
    protected VBox control;

    protected Stack<Node> viewList = new Stack<>();
    protected Queue<Node> baseViews = new LinkedList<>();

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
        control.getChildren().add(new LoadNewickController(this).getRoot());

        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE)
                previousView();
        });

        root.setTop(new MenuController(this));

        root.setBottom(new MinimapController(this).getRoot());

    }

    /**
     * Set a new base view (clear the stack).
     *
     * @param node javaFX element
     */
    public void setBaseView(Node node) {

        viewList.clear();
        setView(node);
        baseViews.add(node);

    }

    /**
     * Set a new main view and push it on the view stack.
     *
     * @param node javaFX element
     */
    public void setView(Node node) {

        viewList.add(node);
        root.setCenter(node);

    }

    /**
     * Set the previous view as view.
     */
    public void previousView() {

        if (viewList.size() > 1) {
            viewList.pop();
            root.setCenter(viewList.peek());
        }

    }

    /**
     * Exits the program.
     */
    public void exitProgram() {
        System.exit(0);
    }

    /**
     * Cycles the baseViews that the MainController has seen as a queue.
     */
    public void cycleViews() {
        if (!baseViews.isEmpty()) {
            Node n = baseViews.remove();
            setBaseView(n);
        }
    }
}
