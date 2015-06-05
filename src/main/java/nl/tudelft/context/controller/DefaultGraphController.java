package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import nl.tudelft.context.drawable.DefaultLabel;
import nl.tudelft.context.drawable.DrawableEdge;
import nl.tudelft.context.drawable.DrawableGraph;
import nl.tudelft.context.effects.Zoom;
import nl.tudelft.context.model.graph.StackGraph;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 2-6-2015
 */
public abstract class DefaultGraphController extends ViewController<AnchorPane> {

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
     * Scroll pane to monitor.
     */
    @FXML
    ScrollPane scroll;

    /**
     * List of graph views.
     */
    LinkedList<StackGraph> graphList = new LinkedList<>();

    /**
     * Reference to the MainController of the app.
     */
    MainController mainController;

    /**
     * Map containing the labels indexed by position.
     */
    Map<Integer, List<DefaultLabel>> labelMap = new HashMap<>();

    /**
     * Create default graph controller.
     *
     * @param mainController MainController to set views with
     */
    public DefaultGraphController(final MainController mainController) {

        super(new AnchorPane());

        this.mainController = mainController;

    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

        initOnTheFlyLoading();

    }

    /**
     * Get the graph list.
     *
     * @return Graph list
     */
    public LinkedList<StackGraph> getGraphList() {
        return graphList;
    }

    /**
     * Show graph with reference points.
     *
     * @param drawableGraph Graph to show
     */
    protected void showGraph(final DrawableGraph drawableGraph) {

        // Bind edges
        List<DrawableEdge> edgeList = drawableGraph.edgeSet().stream()
                .map(edge -> new DrawableEdge(drawableGraph, edge))
                .collect(Collectors.toList());

        // Bind nodes
        List<DefaultLabel> nodeList = drawableGraph.vertexSet().stream()
                .map(node -> node.getNode().getLabel(mainController, this, node))
                .collect(Collectors.toList());

        sequences.getChildren().setAll(edgeList);

        labelMap = nodeList.parallelStream().collect(
                Collectors.groupingBy(
                        DefaultLabel::currentColumn,
                        Collectors.mapping(Function.identity(), Collectors.toList())
                )
        );

        new Zoom(scroll, sequences, new HashMap<>(labelMap));

        showCurrentLabels();

    }

    /**
     * Listen to position and load on the fly.
     */
    private void initOnTheFlyLoading() {

        scroll.widthProperty().addListener(event -> showCurrentLabels());
        scroll.hvalueProperty().addListener(event -> showCurrentLabels());

    }

    /**
     * Show all the labels on current position.
     *
     */
    private void showCurrentLabels() {

        double width = scroll.getWidth();
        double left = (scroll.getContent().layoutBoundsProperty().getValue().getWidth() - width)
                * scroll.getHvalue();
        int indexFrom = (int) Math.floor(left / DrawableGraph.LABEL_SPACING) - 1;
        int indexTo = indexFrom + (int) Math.ceil(width / DrawableGraph.LABEL_SPACING) + 1;

        List<DefaultLabel> infoLabels = IntStream.rangeClosed(indexFrom, indexTo)
                .mapToObj(labelMap::remove)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        infoLabels.forEach(DefaultLabel::init);
        sequences.getChildren().addAll(infoLabels);

    }

}
