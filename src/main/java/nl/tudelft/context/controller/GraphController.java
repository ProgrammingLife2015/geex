package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import nl.tudelft.context.drawable.DrawableEdge;
import nl.tudelft.context.graph.BaseCounter;
import nl.tudelft.context.graph.Graph;
import nl.tudelft.context.graph.Node;
import nl.tudelft.context.service.LoadGraphService;

import java.net.URL;
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

    protected static final int NODE_WIDTH = 60;

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
                    final VBox vbox = new VBox(0);
                    final HBox hbox = new HBox(0);
                    BaseCounter basecounter = node.getBaseCounter();

                    double widthA = Math.round(basecounter.getPercentage('A') * NODE_WIDTH);
                    double widthT = Math.round(basecounter.getPercentage('T') * NODE_WIDTH);
                    double widthC = Math.round(basecounter.getPercentage('C') * NODE_WIDTH);
                    double widthG = Math.round(basecounter.getPercentage('G') * NODE_WIDTH);
                    double widthN = Math.round(basecounter.getPercentage('N') * NODE_WIDTH);

                    final Rectangle rectangleA = new Rectangle(widthA, 5, Color.web("#e41a1c"));
                    final Rectangle rectangleT = new Rectangle(widthT, 5, Color.web("#377eb8"));
                    final Rectangle rectangleC = new Rectangle(widthC, 5, Color.web("#4daf4a"));
                    final Rectangle rectangleG = new Rectangle(widthG, 5, Color.web("#984ea3"));
                    final Rectangle rectangleN = new Rectangle(widthN, 5, Color.web("#ff7f00"));
                    final Label label = new Label(Integer.toString(node.getId()));
                    hbox.getChildren().addAll(rectangleA, rectangleT, rectangleC, rectangleG, rectangleN);
                    label.setCache(true);
                    label.setMinWidth(NODE_WIDTH);
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
