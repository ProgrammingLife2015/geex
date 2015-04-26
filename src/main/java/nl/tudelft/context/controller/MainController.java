package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import nl.tudelft.context.graph.Graph;
import nl.tudelft.context.graph.Node;
import nl.tudelft.context.service.LoadGraphService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 25-4-2015
 */
public class MainController extends ScrollPane implements Initializable {

    @FXML
    protected ProgressIndicator progressIndicator;
    @FXML
    protected HBox ruler;
    @FXML
    protected GridPane sequences;

    protected LoadGraphService loadGraphService;
    protected Graph graph;

    /**
     * Init a controller at main.fxml.
     *
     * @throws RuntimeException
     */
    public MainController(String nodeFile, String edgeFile) throws IOException {

        loadGraphService = new LoadGraphService(nodeFile, edgeFile);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/main.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();

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

        loadGraph();
        reverseScroll();

    }

    /**
     * Load graph with service, show progress indicator meanwhile.
     */
    protected void loadGraph() {

        progressIndicator.visibleProperty().bind(loadGraphService.runningProperty());

        loadGraphService.setOnSucceeded(event -> {
            graph = loadGraphService.getValue();
            showGraph();
        });

        loadGraphService.restart();

    }

    /**
     * Show graph with reference points.
     */
    protected void showGraph() {

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

    /**
     * On vertical scroll, make it horizontal.
     */
    protected void reverseScroll() {

        this.setOnScroll(event -> {
            final double displacement = event.getDeltaY() / this.getContent().getBoundsInLocal().getWidth();
            this.setHvalue(this.getHvalue() - displacement);
        });

    }

}
