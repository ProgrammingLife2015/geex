package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.tudelft.context.drawable.DrawableEdge;
import nl.tudelft.context.graph.BaseCounter;
import nl.tudelft.context.graph.Graph;
import nl.tudelft.context.graph.Node;
import nl.tudelft.context.graph.NodeLabel;
import nl.tudelft.context.service.LoadGraphService;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 7-5-2015
 */
public class GraphController extends DefaultController<ScrollPane> {

    @FXML
    protected ProgressIndicator progressIndicator;
    @FXML
    protected Group sequences;

    protected MainController mainController;
    protected LoadGraphService loadGraphService;

    public static final int NODE_WIDTH = 60;

    /**
     * Init a controller at graph.fxml.
     *
     * @param loadGraphService service with file locations
     */
    public GraphController(MainController mainController, LoadGraphService loadGraphService) {

        super(new ScrollPane());

        this.mainController = mainController;
        this.loadGraphService = loadGraphService;

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
    public void initialize(URL location, ResourceBundle resources) {

        progressIndicator.visibleProperty().bind(loadGraphService.runningProperty());
        loadGraphService.setOnSucceeded(event -> showGraph(loadGraphService.getValue()));

        loadGraph();

    }

    /**
     * Load graph from source.
     */
    protected void loadGraph() {

        sequences.getChildren().clear();
        loadGraphService.restart();

    }

    /**
     * Show graph with reference points.
     */
    protected void showGraph(Graph graph) {

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
                .map(node -> {
                    final VBox vbox = new VBox();
                    final HBox hbox = new HBox();
                    final BaseCounter basecounter = node.getBaseCounter();

                    List<NodeLabel> nodeLabels = Arrays.asList('A', 'T', 'C', 'G', 'N').stream()
                            .map(base -> new NodeLabel(base, basecounter.getRatio(base)))
                            .collect(Collectors.toList());

                    nodeLabels.get(0).setWidth(
                            nodeLabels.get(0).getWidth() + NODE_WIDTH - nodeLabels.stream().mapToInt(nodeLabel -> (int) nodeLabel.getWidth()).sum()
                    );

                    hbox.getChildren().addAll(nodeLabels);

                    final Label label = new Label(Integer.toString(node.getId()));
                    label.setCache(true);
                    label.setMaxWidth(NODE_WIDTH);
                    label.prefWidth(NODE_WIDTH);
                    vbox.setCache(true);
                    vbox.getChildren().addAll(label, hbox);
                    vbox.translateXProperty().bind(node.translateXProperty());
                    vbox.translateYProperty().bind(node.translateYProperty());
                    final Tooltip percentages = new Tooltip(node.getBaseCounter().toString());
                    label.setTooltip(percentages);
                    label.setOnMouseClicked(event -> mainController.setView(new BaseController(node.getContent()).getRoot()));
                    return vbox;
                }).collect(Collectors.toList());


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
    protected List<Node> showColumn(Graph graph, List<Node> nodes, int column) {

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
    protected void showNodes(List<Node> nodes, int column) {

        int shift = nodes.size() * 50;
        int row = 0;
        for (Node node : nodes) {

            node.setTranslateX(column * 100);
            node.setTranslateY(row * 100 - shift);

            row++;

        }

    }

}
