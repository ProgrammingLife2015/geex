package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import net.sourceforge.olduvai.treejuxtaposer.drawer.Tree;
import net.sourceforge.olduvai.treejuxtaposer.drawer.TreeNode;
import nl.tudelft.context.drawable.DrawableEdge;
import nl.tudelft.context.graph.Graph;
import nl.tudelft.context.graph.Node;
import nl.tudelft.context.service.LoadGraphService;
import nl.tudelft.context.service.LoadTreeService;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
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
            loadNewick,
            load,
            treeload;

    @FXML
    protected TextField
            nodeSource,
            edgeSource,
            nwkSource;

    protected LoadGraphService loadGraphService;
    protected LoadTreeService loadTreeService;
    protected ProgressIndicator progressIndicator;
    protected Group sequences;

    /**
     * Init a controller at load_graph.fxml.
     *
     * @param progressIndicator progress indicator of graph loading
     * @param sequences         grid to display graph
     * @throws RuntimeException
     */
    public LoadGraphController(ProgressIndicator progressIndicator, Group sequences) {

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


        loadTreeService = new LoadTreeService();
        loadTreeService.setOnSucceeded(event -> showTree(loadTreeService.getValue()));

        FileChooser nwkFileChooser = new FileChooser();
        nwkFileChooser.setTitle("Open Newick file");
        loadNewick.setOnAction(event -> loadTreeService.setNwkFile(loadFile(nwkFileChooser, nwkSource)));

        treeload.setOnAction(event -> loadTree());

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
     * Load tree from source.
     */
    protected void loadTree() {
        loadTreeService.restart();
    }

    /**
     * Show graph with reference points.
     */
    protected void showGraph(Graph graph) {

        List<Node> start = new ArrayList<>(Collections.singletonList(graph.getFirstNode()));
        showColumn(graph, start, 0);

        // Bind edges
        graph.edgeSet().stream().forEach(edge -> {
            final DrawableEdge line = new DrawableEdge(graph, edge);
            sequences.getChildren().add(line);
        });

        // Bind nodes
        graph.vertexSet().stream().forEach(node -> {
            final Label label = new Label(Integer.toString(node.getId()));
            label.translateXProperty().bind(node.translateXProperty());
            label.translateYProperty().bind(node.translateYProperty());
            sequences.getChildren().add(label);
        });

    }

    /**
     * Show the columns of the graph recursive.
     *
     * @param graph  containing the nodes
     * @param nodes  nodes to display
     * @param column column index
     */
    protected void showColumn(Graph graph, List<Node> nodes, int column) {

        if (nodes.isEmpty()) return;

        showNodes(nodes, column);

        List<Node> nextNodes = nodes.stream()
                .map(node -> graph.outgoingEdgesOf(node).stream()
                        .map(graph::getEdgeTarget)
                        .filter(x -> x.incrementIncoming() == graph.inDegreeOf(x)))
                .flatMap(l -> l)
                .collect(Collectors.toList());

        showColumn(graph, nextNodes, column + 1);

    }

    /**
     * Show the tree in console.
     *
     * @param tree tree to show
     */
    protected void showTree(Tree tree) {
        System.out.println("check: " + tree.getName());
        printTree(tree.getRoot(), .0);
    }

    /**
     * Print tree recursive to console.
     *
     * @param node   current node
     * @param prev_w previous position
     */
    protected void printTree(TreeNode node, double prev_w) {
        for (int i = 0; i < node.numberLeaves; i += 1) {
            if (node.getChild(i) != null) {
                double w = prev_w + node.getChild(i).getWeight() * .5e4;
                for (int h = 0; h < w; h += 1) {
                    System.out.print("  ");
                }
                System.out.println(node.getChild(i).getName());
                printTree(node.getChild(i), w);
            }
        }
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
