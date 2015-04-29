package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import net.sourceforge.olduvai.treejuxtaposer.drawer.Tree;
import nl.tudelft.context.graph.Graph;
import nl.tudelft.context.graph.Node;
import nl.tudelft.context.service.LoadGraphService;
import nl.tudelft.context.service.LoadTreeService;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
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
    protected HBox ruler;
    protected GridPane sequences;

    /**
     * Init a controller at load_graph.fxml.
     *
     * @param progressIndicator progress indicator of graph loading
     * @param ruler             ruler to display reference points
     * @param sequences         grid to display graph
     * @throws RuntimeException
     */
    public LoadGraphController(ProgressIndicator progressIndicator, HBox ruler, GridPane sequences) {

        super(new GridPane());

        this.progressIndicator = progressIndicator;
        this.ruler = ruler;
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

        ruler.getChildren().clear();
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

        List<Set<Node>> nodeSets = graph.getReferencePoints()
                .stream()
                .limit(200)
                .map(referencePoint -> {
                    ruler.getChildren().add(new Label(Integer.toString(referencePoint)));
                    return graph.getVertexesByStartPosition(referencePoint);
                }).collect(Collectors.toList());

        int row = 0;
        for (Set<Node> nodes : nodeSets) {
            showNodes(nodes, row);
            row++;
        }

    }

    protected void showTree(Tree tree) {
        System.out.println("check: " + tree);
    }

    /**
     * Show all nodes at a start position.
     *
     * @param nodes nodes to show
     * @param row   row at start position
     */
    protected void showNodes(Set<Node> nodes, int row) {

        int col = 1;
        for (Node node : nodes) {

            final Label label = new Label(Integer.toString(node.getId()));
            sequences.add(label, row, col);

            col++;

        }

    }

}
