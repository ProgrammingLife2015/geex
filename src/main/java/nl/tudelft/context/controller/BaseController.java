package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import nl.tudelft.context.model.graph.Node;
import nl.tudelft.context.model.graph.StackGraph;
import org.apache.commons.lang.StringUtils;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @author René Vennik <renevennik@gmail.com>
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
     * JavaFX Text holder for bases.
     */
    @FXML
    Text bases;

    /**
     * JavaFX Text holder for occurrences.
     */
    @FXML
    Text occurrences;

    /**
     * Graph containing the node to display.
     */
    private StackGraph stackGraph;

    /**
     * Node that is displayed.
     */
    private Node node;

    /**
     * Init a controller at graph.fxml.
     *
     * @param stackGraph Graph containing the node
     * @param node       Node to display
     */
    public BaseController(final StackGraph stackGraph, final Node node) {

        super(new ScrollPane());

        this.stackGraph = stackGraph;
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
        bases.setText(content);

        sources.setText(StringUtils.join(node.getSources(), ", "));

        List<String> otherOccurrences = stackGraph.vertexSet().stream()
                .filter(vertex -> vertex.getContent().equals(content) && !vertex.equals(node))
                .map(node -> (Node) node)
                .map(Node::getId)
                .map(String::valueOf)
                .collect(Collectors.toList());

        if (!otherOccurrences.isEmpty()) {
            occurrences.setText(StringUtils.join(otherOccurrences, ", "));
        } else {
            occurrences.setText("None");
        }

    }

    @Override
    public String getBreadcrumbName() {
        return "Sequence: " + node.getId();
    }

    @Override
    public void activate() {
        // empty method
    }

    @Override
    public void deactivate() {
        // empty method
    }

}
