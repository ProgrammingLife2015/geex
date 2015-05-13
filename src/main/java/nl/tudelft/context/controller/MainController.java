package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import nl.tudelft.context.workspace.Workspace;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 25-4-2015
 */
public class MainController extends DefaultController<BorderPane> {

    /**
     * The main container of the controller.
     */
    @FXML
    VBox control;

    /**
     * The container of all views after this one.
     */
    @FXML
    StackPane view;

    /**
     * A stack of the current views.
     */
    Stack<Node> viewList;

    /**
     * The current workspace.
     */
    Workspace workspace;

    /**
     * Init a controller at main.fxml.
     */
    public MainController() {

        super(new BorderPane());

        loadFXML("/application/main.fxml");

        viewList = new Stack<>();
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
    public final void initialize(final URL location, final ResourceBundle resources) {

        control.getChildren().add(new LoadGraphController(this).getRoot());
        control.getChildren().add(new LoadNewickController(this).getRoot());

        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                previousView();
            }
        });

        root.setTop(new MenuController(this));

    }

    /**
     * Set a new base view (clear the stack).
     *
     * @param node javaFX element
     */
    public final void setBaseView(final Node node) {

        view.getChildren().clear();
        viewList.clear();
        setView(node);

    }

    /**
     * Set a new main view and push it on the view stack.
     *
     * @param node javaFX element
     */
    public final void setView(final Node node) {

        viewList.add(node);
        view.getChildren().add(node);

    }

    /**
     * Set the previous view as view.
     */
    public final void previousView() {

        if (viewList.size() > 1) {
            viewList.pop();
            view.getChildren().retainAll(viewList);
        }

    }

    /**
     * Exits the program.
     */
    public final void exitProgram() {
        System.exit(0);
    }

    /**
     * Get the current workspace.
     *
     * @return The current workspace
     */
    public final Workspace getWorkspace() {
        return workspace;
    }

    /**
     * Set the current workspace.
     *
     * @param workspace The new workspace
     */
    public final void setWorkspace(final Workspace workspace) {
        this.workspace = workspace;

        // TODO: don't do this, for testing purposes only.
        this.setBaseView(new GraphController(this, workspace.getGraphList().get(0)).getRoot());
    }
}
