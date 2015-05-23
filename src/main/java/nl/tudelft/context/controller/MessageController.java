package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * @author Jim Hommes
 * @version 1.0
 * @since 19-05-2015
 */
public class MessageController extends DefaultController<VBox> {

    /**
     * Message used when graph loading fails.
     */
    public static final String FAIL_LOAD_GRAPH = "Could not load genome graph.";

    /**
     * Message used when graph loading succeeds.
     */
    public static final String SUCCESS_LOAD_GRAPH = "Genome graph loaded successfully.";

    /**
     * Message used when workspace loading fails.
     */
    public static final String FAIL_LOAD_WORKSPACE = "Could not load workspace.";

    /**
     * Message used when workspace loading succeeds.
     */
    public static final String SUCCESS_LOAD_WORKSPACE = "Workspace loaded successfully.";

    /**
     * Message used when tree loading fails.
     */
    public static final String FAIL_LOAD_TREE = "Could not load phylogenetic tree.";

    /**
     * Message used when tree loading succeeds.
     */
    public static final String SUCCESS_LOAD_TREE = "Phylogenetic tree loaded successfully.";

    /**
     * Message used when annotation loading fails.
     */
    public static final String FAIL_LOAD_ANNOTATION = "Could not load annotations.";

    /**
     * Message used when annotation loading succeeds.
     */
    public static final String SUCCESS_LOAD_ANNOTATION = "Annotations loaded successfully.";

    /**
     * Message used when mutations loading fails.
     */
    public static final String FAIL_LOAD_MUTATION = "Could not load mutations.";

    /**
     * Message used when mutations loading succeeds.
     */
    public static final String SUCCESS_LOAD_MUTATION = "Mutations loaded successfully.";

    /**
     * The text that is shown.
     */
    @FXML
    Text message;

    /**
     * Create a generic controller with T as root.
     * <p>
     * T must be a possible root for fxml so T must extend Parent.
     * </p>
     * A reference to the main controller.
     */
    public MessageController() {

        super(new VBox());

        loadFXML("/application/footer.fxml");
    }

    /**
     * The function used to display a message and remove the previous one.
     * @param text The string to display.
     */
    public final void displayMessage(final String text) {
        message.setText(text);
    }

    @Override
    public final void initialize(final URL location, final ResourceBundle resources) {
        displayMessage("Ready");
    }
}
