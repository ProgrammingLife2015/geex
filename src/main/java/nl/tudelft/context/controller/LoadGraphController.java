package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import nl.tudelft.context.graph.Graph;
import nl.tudelft.context.graph.Node;
import nl.tudelft.context.service.LoadGraphService;
import org.jgrapht.graph.DefaultEdge;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 26-4-2015
 */
public class LoadGraphController extends DefaultController<GridPane> implements Initializable {

    @FXML
    protected Button
            loadNodes,
            loadEdges,
            load;

    @FXML
    protected TextField
            nodeSource,
            edgeSource;

    protected LoadGraphService loadGraphService;
    protected ProgressIndicator progressIndicator;
    protected GridPane sequences;

    /**
     * Init a controller at load_graph.fxml.
     *
     * @param progressIndicator progress indicator of graph loading
     * @param sequences         grid to display graph
     * @throws RuntimeException
     */
    public LoadGraphController(ProgressIndicator progressIndicator, GridPane sequences) {

        super(new GridPane());

        this.progressIndicator = progressIndicator;
        this.sequences = sequences;

        loadFXML("/application/load_graph.fxml");

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

        loadGraphService = new LoadGraphService();
        loadGraphService.setOnSucceeded(event -> showGraph(loadGraphService.getValue()));

        progressIndicator.visibleProperty().bind(loadGraphService.runningProperty());

        FileChooser nodeFileChooser = new FileChooser();
        nodeFileChooser.setTitle("Open node file");
        loadNodes.setOnAction(event -> loadGraphService.setNodeFile(loadFile(nodeFileChooser, nodeSource)));

        FileChooser edgeFileChooser = new FileChooser();
        edgeFileChooser.setTitle("Open edge file");
        loadEdges.setOnAction(event -> loadGraphService.setEdgeFile(loadFile(edgeFileChooser, edgeSource)));

        load.setOnAction(event -> loadGraph());

    }

    /**
     * Load file.
     */
    protected File loadFile(FileChooser fileChooser, TextField source) {

        File file = fileChooser.showOpenDialog(root.getScene().getWindow());

        if (file != null) {
            source.setText(file.getName());
        } else {
            source.setText("");
        }

        return file;

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

        Set<Node> start = new HashSet<>(Collections.singletonList(graph.getFirstNode()));
        showColumn(graph, start, 0);

    }

    /**
     * Show the column of the graph and call next column.
     *
     * @param graph  containing the nodes
     * @param nodes  nodes to display
     * @param column column index
     */
    protected void showColumn(Graph graph, Set<Node> nodes, int column) {

        if(nodes.isEmpty()) return;

        showNodes(nodes, column);

        Set<Node> nextNodes = new HashSet<>();
        for (Node node : nodes) {
            Set<DefaultEdge> nextEdges = graph.outgoingEdgesOf(node);
            nextNodes.addAll(nextEdges.stream().map(x -> {
                Node temp = graph.getEdgeTarget(x);
                temp.incrementIncoming();
                return temp;
            }).filter(x -> x.getCurrentIncoming() == graph.inDegreeOf(x)).collect(Collectors.toList()));
        }
        showColumn(graph, nextNodes, column + 1);

    }

    /**
     * Show all nodes at a start position.
     *
     * @param nodes  nodes to draw
     * @param column column to draw at
     */
    protected void showNodes(Set<Node> nodes, int column) {

        int row = 0;
        for (Node node : nodes) {

            final Label label = new Label(Integer.toString(node.getId()));
            sequences.add(label, column, row);

            row++;

        }

    }

}
