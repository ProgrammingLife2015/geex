package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import nl.tudelft.context.breadcrumb.Breadcrumb;
import nl.tudelft.context.breadcrumb.ViewStack;
import nl.tudelft.context.controller.overlay.OverlayController;
import nl.tudelft.context.workspace.Workspace;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 25-4-2015
 */
public class MainController extends DefaultController<BorderPane> {

    /**
     * The container of all views after this one.
     */
    @FXML
    StackPane view;

    /**
     * FXML pointer for right BorderPane.
     */
    @FXML
    BorderPane main;

    /**
     * A stack of the current views.
     */
    ViewStack viewStack;

    /**
     * The current workspace.
     */
    private Workspace workspace;

    /**
     * The MessageController that is needed to display error messages.
     */
    MessageController messageController;

    /**
     * Init a controller at main.fxml.
     */
    public MainController() {

        super(new BorderPane());

        viewStack = new ViewStack();

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
    public final void initialize(final URL location, final ResourceBundle resources) {

        main.setTop(new Breadcrumb(this, viewStack));
        root.setTop(new MenuController(this));

        messageController = new MessageController();
        main.setBottom(messageController.getRoot());

        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                previousView();
            }
        });

    }

    public final void setOverlay(final OverlayController overlay) {
        viewStack.add(null);
        view.getChildren().add(overlay.getRoot());
    }

    /**
     * Set a new base view (clear the stack).
     *
     * @param viewController Controller containing JavaFX root
     */
    public final void setBaseView(final ViewController viewController) {

        view.getChildren().clear();
        viewStack.clear();
        setView(viewController);

    }

    /**
     * Set a new main view and push it on the view stack.
     *
     * @param viewController Controller containing JavaFX root
     */
    public final void setView(final ViewController viewController) {

        viewStack.add(viewController);
        view.getChildren().add(viewController.getRoot());

    }

    /**
     * Set the previous view as view.
     */
    public final void previousView() {

        if (viewStack.size() > 1) {
            viewStack.pop();
            view.getChildren().retainAll(
                    viewStack.stream().map(ViewController::getRoot).collect(Collectors.toSet()));
        }

    }

    /**
     * Go back to the given view.
     *
     * @param viewController View to go back to
     */
    public final void backToView(final ViewController viewController) {

        while (!viewStack.peek().equals(viewController)) {
            previousView();
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
    public Workspace getWorkspace() {
        return workspace;
    }

    /**
     * Set the current workspace.
     *
     * @param workspace The new workspace
     */
    public final void setWorkspace(final Workspace workspace) {
        this.workspace = workspace;
    }


    /**
     * The function that is used to display a message in the footer.
     * @param text The text that will be displayed.
     */
    public final void displayMessage(final String text) {
        messageController.displayMessage(text);
    }
}
