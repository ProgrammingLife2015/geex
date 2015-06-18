package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import nl.tudelft.context.model.graph.Node;
import org.apache.commons.lang.StringUtils;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author René Vennik
 * @version 1.0
 * @since 8-5-2015
 */
public final class BaseController extends ViewController<ScrollPane> {

    /**
     * JavaFX Text holder for sources.
     */
    @FXML
    Text sources;

    /**
     * JavaFX Text holder for coding sequences.
     */
    @FXML
    Text codingsequences;

    /**
     * JavaFX Text holder for bases.
     */
    @FXML
    Text bases;

    /**
     * JavaFX Text holder for percentages.
     */
    @FXML
    Text percentages;

    /**
     * Node that is displayed.
     */
    private Node node;

    /**
     * Init a controller at graph.fxml.
     *
     * @param node Node to display
     */
    public BaseController(final Node node) {

        super(new ScrollPane());

        this.node = node;

        loadFXML("/application/base.fxml");

    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root
     *                  object, or <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or
     *                  <tt>null</tt> if the root object was not localized.
     */
    @Override
    public void initialize(final URL location,
                           final ResourceBundle resources) {

        String content = node.getContent();
        codingsequences.setText(node.getCodingSequenceText());
        percentages.setText(node.getBaseCounter().toString() + "\n");
        bases.setText(content);

        sources.setText(StringUtils.join(node.getSources(), ", "));

    }

    @Override
    public String getBreadcrumbName() {
        return "Sequence: " + node.getId();
    }

}
