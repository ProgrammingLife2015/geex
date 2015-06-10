package nl.tudelft.context.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import nl.context.tudelft.effect.Zoom;
import nl.tudelft.context.controller.locator.LocatorController;
import nl.tudelft.context.drawable.DrawableEdge;
import nl.tudelft.context.drawable.graph.AbstractDrawableNode;
import nl.tudelft.context.drawable.graph.AbstractLabel;
import nl.tudelft.context.drawable.graph.DrawableGraph;
import nl.tudelft.context.model.graph.StackGraph;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author René Vennik
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
    Group sequences;

    /**
     * Scroll pane to monitor.
     */
    @FXML
    ScrollPane scroll;

    /**
     * The locator.
     */
    @FXML
    Pane locator;

    /**
     * List of graph views.
     */
    LinkedList<StackGraph> graphList = new LinkedList<>();

    /**
     * Reference to the MainController of the app.
     */
    MainController mainController;

    /**
     * Map containing the nodes indexed by position.
     */
    ObjectProperty<Map<Integer, List<AbstractDrawableNode>>> nodeMapProperty =
            new SimpleObjectProperty<>(new HashMap<>());

    /**
     * Property containing the position by columns.
     */
    ObjectProperty<List<Integer>> positionProperty = new SimpleObjectProperty<>();

    /**
     * Property containing the current shown labels.
     */
    ObjectProperty<Set<AbstractLabel>> currentLabelsProperty = new SimpleObjectProperty<>(new HashSet<>());

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

        ObjectProperty<List<Region>> zoomLabelsProperty = new SimpleObjectProperty<>();
        new Zoom(scroll, sequences, zoomLabelsProperty);
        currentLabelsProperty.addListener((observable, oldValue, newValue) -> {
            zoomLabelsProperty.setValue(newValue.stream().collect(Collectors.toList()));
        });

        new LocatorController(locator, nodeMapProperty, positionProperty);

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

        sequences.getChildren().setAll(drawableGraph.edgeSet().stream()
                .map(edge -> new DrawableEdge(drawableGraph, edge))
                .collect(Collectors.toList()));

        nodeMapProperty.setValue(drawableGraph.vertexSet().parallelStream().collect(
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

        nodeMapProperty.addListener(event -> updatePosition());
        scroll.widthProperty().addListener(event -> updatePosition());
        scroll.hvalueProperty().addListener(event -> updatePosition());

        currentLabelsProperty.addListener((observable, oldValue, newValue) -> {
            sequences.getChildren().removeAll(oldValue);
            sequences.getChildren().addAll(newValue);
        });

        positionProperty.addListener(event -> showCurrentLabels());

    }

    /**
     * Update the current position.
     */
    private void updatePosition() {

        double width = scroll.getWidth();
        double left = (scroll.getContent().layoutBoundsProperty().getValue().getWidth() - width) * scroll.getHvalue();
        int from = (int) Math.floor(left / DrawableGraph.LABEL_SPACING) - 1;
        int to = from + (int) Math.ceil(width / DrawableGraph.LABEL_SPACING) + 1;

        positionProperty.setValue(IntStream.rangeClosed(from, to)
                .boxed()
                .collect(Collectors.toList()));

    }

    /**
     * Show all the labels on current position.
     */
    private void showCurrentLabels() {

        final Map<Integer, List<AbstractDrawableNode>> labelMap = nodeMapProperty.getValue();
        currentLabelsProperty.setValue(positionProperty.get().stream()
                .map(labelMap::get)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .map(node -> node.getLabel(mainController, this))
                .collect(Collectors.toSet()));

    }

}
