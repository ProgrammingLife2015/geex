package nl.tudelft.context.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import nl.tudelft.context.drawable.DrawableEdge;
import nl.tudelft.context.drawable.graph.AbstractDrawableNode;
import nl.tudelft.context.drawable.graph.AbstractLabel;
import nl.tudelft.context.drawable.graph.DrawableGraph;
import nl.tudelft.context.effects.Zoom;
import nl.tudelft.context.model.graph.StackGraph;

import java.net.URL;
import java.util.ArrayList;
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
public abstract class AbstractGraphController extends ViewController<AnchorPane> {

    /**
     * ProgressIndicator to show when the graph is loading.
     */
    @FXML
    ProgressIndicator progressIndicator;

    /**
     * The container for the graph.
     */
    @FXML
    Group edges, nodes;

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
    ObjectProperty<Map<Integer, List<AbstractDrawableNode>>> labelMapProperty = new SimpleObjectProperty<>(new HashMap<>());

    /**
     * Property containing the current shown labels.
     */
    ObjectProperty<List<AbstractLabel>> currentLabelsProperty = new SimpleObjectProperty<>(new ArrayList<>());

    /**
     * Create default graph controller.
     *
     * @param mainController MainController to set views with
     */
    public AbstractGraphController(final MainController mainController) {

        super(new AnchorPane());

        this.mainController = mainController;

    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

        new Zoom(scroll, nodes, currentLabelsProperty);
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

        edges.getChildren().setAll(edgeList);

        labelMapProperty.setValue(drawableGraph.vertexSet().parallelStream().collect(
                Collectors.groupingBy(
                        AbstractDrawableNode::currentColumn,
                        Collectors.mapping(Function.identity(), Collectors.toList())
                )
        ));

    }

    /**
     * Listen to position and load on the fly.
     */
    private void initOnTheFlyLoading() {

        labelMapProperty.addListener(event -> showCurrentLabels());
        scroll.widthProperty().addListener(event -> showCurrentLabels());
        scroll.hvalueProperty().addListener(event -> showCurrentLabels());

        currentLabelsProperty.addListener(((observable, oldValue, newValue) -> {
            newValue.forEach(AbstractLabel::init);
            nodes.getChildren().setAll(newValue);
        }));

    }

    /**
     * Show all the labels on current position.
     */
    private void showCurrentLabels() {

        double width = scroll.getWidth();
        double left = (scroll.getContent().layoutBoundsProperty().getValue().getWidth() - width)
                * scroll.getHvalue();
        int indexFrom = (int) Math.floor(left / DrawableGraph.LABEL_SPACING) - 1;
        int indexTo = indexFrom + (int) Math.ceil(width / DrawableGraph.LABEL_SPACING) + 1;

        final Map<Integer, List<AbstractDrawableNode>> labelMap = labelMapProperty.getValue();
        currentLabelsProperty.setValue(
                IntStream.rangeClosed(indexFrom, indexTo)
                        .mapToObj(labelMap::get)
                        .filter(Objects::nonNull)
                        .flatMap(Collection::stream)
                        .map(node -> node.getLabel(mainController, this))
                        .collect(Collectors.toList()));

    }

}
