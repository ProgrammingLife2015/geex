package nl.tudelft.context.controller;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import nl.tudelft.context.drawable.DefaultLabel;
import nl.tudelft.context.drawable.DrawableEdge;
import nl.tudelft.context.drawable.DrawableGraph;
import nl.tudelft.context.model.graph.StackGraph;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    public DefaultGraphController(MainController mainController) {

        super(new AnchorPane());

        this.mainController = mainController;

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

        sequences.getChildren().addAll(edgeList);
        initOnTheFlyLoading(nodeList);

    }

    /**
     * Listen to position and load on the fly.
     *
     * @param nodeList Labels to to load on the fly
     */
    private void initOnTheFlyLoading(final List<DefaultLabel> nodeList) {

        Map<Integer, List<DefaultLabel>> map = nodeList.stream().collect(
                Collectors.groupingBy(
                        DefaultLabel::currentColumn,
                        Collectors.mapping(Function.identity(), Collectors.toList())
                )
        );

        showCurrentLabels(map);
        scroll.widthProperty().addListener(event -> showCurrentLabels(map));
        scroll.hvalueProperty().addListener(event -> showCurrentLabels(map));

    }

    /**
     * Show all the labels on current position.
     *
     * @param map Containing the labels indexed by position
     */
    private void showCurrentLabels(final Map<Integer, List<DefaultLabel>> map) {

        double width = scroll.getWidth();
        double left = (scroll.getContent().layoutBoundsProperty().getValue().getWidth() - width)
                * scroll.getHvalue();
        int indexFrom = (int) Math.floor(left / DrawableGraph.LABEL_SPACING) - 1;
        int indexTo = indexFrom + (int) Math.ceil(width / DrawableGraph.LABEL_SPACING) + 1;

        List<DefaultLabel> infoLabels = IntStream.rangeClosed(indexFrom, indexTo)
                .mapToObj(map::remove)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        infoLabels.forEach(DefaultLabel::init);
        sequences.getChildren().addAll(infoLabels);

    }

}
