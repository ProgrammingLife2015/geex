package nl.tudelft.context.controller;

import com.sun.deploy.util.StringUtils;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import nl.tudelft.context.graph.Graph;
import nl.tudelft.context.graph.Node;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 8-5-2015
 */
public final class BaseController extends DefaultController<StackPane> {

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
    private Graph graph;

    /**
     * Node that is displayed.
     */
    private Node node;

    /**
     * Init a controller at graph.fxml.
     *
     * @param graph Graph containing the node
     * @param node  Node to display
     */
    public BaseController(final Graph graph, final Node node) {

        super(new StackPane());

        this.graph = graph;
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

        List<String> otherOccurrences = graph.vertexSet().stream()
                .filter(vertex -> vertex.getContent().equals(content) && !vertex.equals(node))
                .map(Node::getId)
                .map(String::valueOf)
                .collect(Collectors.toList());

        if (otherOccurrences.size() > 0) {
            occurrences.setText(StringUtils.join(otherOccurrences, ", "));
        } else {
            occurrences.setText("None");
        }

    }

}
