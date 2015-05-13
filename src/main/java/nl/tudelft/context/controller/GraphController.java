package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import nl.tudelft.context.drawable.DrawableEdge;
import nl.tudelft.context.drawable.InfoLabel;
import nl.tudelft.context.graph.Graph;
import nl.tudelft.context.graph.Node;
import nl.tudelft.context.service.LoadGraphService;
import org.apache.commons.collections.CollectionUtils;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 7-5-2015
 */
public class GraphController extends DefaultController<ScrollPane> {

    /**
     * ProgressIndicator to show when the graph is loading.
     */
    @FXML
    ProgressIndicator progressIndicator;

    /**
     * The container for the graph.
     */
    @FXML
    Group sequences;

    /**
     * Reference to the MainController of the app.
     */
    MainController mainController;

    /**
     * The service for loading the Graph.
     */
    LoadGraphService loadGraphService;

    /**
     * Sources that are displayed in the graph.
     */
    Set<String> sources;

    /**
     * Define the amount of the shift.
     */
    public static final int NODE_SPACING = 50;

    public static final int NODE_WIDTH = 60;

    /**
     * Init a controller at graph.fxml.
     *
     * @param mainController MainController for the application
     * @param sources        Sources to display
     */
    public GraphController(final MainController mainController, final Set<String> sources) {

        super(new ScrollPane());

        this.mainController = mainController;
        this.sources = sources;
        this.loadGraphService = mainController.getWorkspace().getGraphList().get(0);

        loadFXML("/application/graph.fxml");

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

        progressIndicator.visibleProperty().bind(loadGraphService.runningProperty());
        loadGraphService.setOnSucceeded(event -> showGraph(cleanGraph(loadGraphService.getValue())));

        loadGraph();

    }

    /**
     * Load graph from source.
     */
    private void loadGraph() {

        sequences.getChildren().clear();
        loadGraphService.restart();

    }

    /**
     * Clean the graph from sources that aren't shown.
     *
     * @param graph Graph to show
     * @return Cleaned up graph
     */
    private Graph cleanGraph(final Graph graph) {

        // Remove unnecessary edges
        graph.removeAllEdges(graph.edgeSet().stream()
                .filter(edge -> {
                    Node source = graph.getEdgeSource(edge);
                    Node target = graph.getEdgeTarget(edge);
                    return !CollectionUtils.containsAny(source.getSources(), sources)
                            || !CollectionUtils.containsAny(target.getSources(), sources);
                })
                .collect(Collectors.toList()));

        // Remove unnecessary nodes
        graph.removeAllVertices(graph.vertexSet().stream()
                .filter(vertex -> !CollectionUtils.containsAny(vertex.getSources(), sources))
                .collect(Collectors.toList()));

        return graph;

    }

    /**
     * Show graph with reference points.
     *
     * @param graph Graph to show
     */
    private void showGraph(final Graph graph) {

        List<Node> start = graph.getFirstNodes();

        int i = 0;
        while (!start.isEmpty()) {
            start = showColumn(graph, start, i++);
        }

        // Bind edges
        List<DrawableEdge> edgeList = graph.edgeSet().stream()
                .map(edge -> new DrawableEdge(graph, edge))
                .collect(Collectors.toList());

        // Bind nodes
        List<VBox> nodeList = graph.vertexSet().stream()
                .map(node -> new InfoLabel(mainController, node))
                .collect(Collectors.toList());

        sequences.getChildren().addAll(edgeList);
        sequences.getChildren().addAll(nodeList);

    }

    /**
     * Show the columns of the graph recursive.
     *
     * @param graph  containing the nodes
     * @param nodes  nodes to display
     * @param column column index
     * @return next column
     */
    private List<Node> showColumn(final Graph graph, final List<Node> nodes, final int column) {

        showNodes(nodes, column);

        return nodes.stream()
                .map(node -> graph.outgoingEdgesOf(node).stream()
                        .map(graph::getEdgeTarget)
                        .filter(x -> x.incrementIncoming() == graph.inDegreeOf(x)))
                .flatMap(l -> l)
                .collect(Collectors.toList());

    }

    /**
     * Show all nodes at a start position.
     *
     * @param nodes  nodes to draw
     * @param column column to draw at
     */
    private void showNodes(final List<Node> nodes, final int column) {
        int shift = nodes.size() * NODE_SPACING;
        int row = 0;
        for (Node node : nodes) {

            node.setTranslateX(column * 100);
            node.setTranslateY(row * 100 - shift);

            row++;

        }

    }

}
