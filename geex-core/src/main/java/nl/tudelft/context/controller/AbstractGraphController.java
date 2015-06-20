package nl.tudelft.context.controller;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import nl.tudelft.context.drawable.DrawableEdge;
import nl.tudelft.context.drawable.graph.AbstractDrawableNode;
import nl.tudelft.context.drawable.graph.AbstractLabel;
import nl.tudelft.context.drawable.graph.DrawableGraph;
import nl.tudelft.context.effect.Zoom;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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
     * Center for the scrollbar.
     */
    private static final double CENTER = .5;

    /**
     * Sources that are displayed in the graph.
     */
    ObjectProperty<Set<String>> selectedSources = new SimpleObjectProperty<>(new HashSet<>());

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
            zoomLabelsProperty.set(newValue.stream().collect(Collectors.toList()));
            newValue.stream().forEach(label -> label.updateSources(selectedSources.get()));
        });

        selectedSources.addListener((observable, oldValue, newValue) -> {
            Set<AbstractLabel> labels = currentLabelsProperty.get();
            sequences.getChildren().removeAll(labels);
            sequences.getChildren().addAll(labels);
            labels.stream().forEach(label -> label.updateSources(newValue));
        });

        initOnTheFlyLoading();

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

        currentLabelsProperty.set(new HashSet<>());
        nodeMapProperty.set(drawableGraph.vertexSet().parallelStream().collect(
                Collectors.groupingBy(
                        AbstractDrawableNode::currentColumn,
                        Collectors.mapping(Function.identity(), Collectors.toList())
                )
        ));

        Platform.runLater(this::updatePosition);
        Platform.runLater(() -> scroll.setVvalue(CENTER));

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
     * Set the position of the graph by column.
     *
     * @param column The colum to set the position to
     */
    public void setPosition(final int column) {

        double width = (scroll.getContent().layoutBoundsProperty().get().getWidth() - scroll.getWidth());
        double left = column * DrawableGraph.LABEL_SPACING;
        scroll.setHvalue(left / width);

    }

    /**
     * Update the current position.
     */
    private void updatePosition() {

        double width = scroll.getWidth();
        double left = (scroll.getContent().layoutBoundsProperty().get().getWidth() - width) * scroll.getHvalue();
        int from = (int) Math.floor(left / DrawableGraph.LABEL_SPACING) - 1;
        int to = from + (int) Math.ceil(width / DrawableGraph.LABEL_SPACING) + 1;

        positionProperty.set(IntStream.rangeClosed(from, to)
                .boxed()
                .collect(Collectors.toList()));

    }

    /**
     * Show all the labels on current position.
     */
    private void showCurrentLabels() {

        final Map<Integer, List<AbstractDrawableNode>> labelMap = nodeMapProperty.get();
        currentLabelsProperty.set(positionProperty.get().stream()
                .map(labelMap::get)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .map(node -> node.getLabel(mainController, this))
                .collect(Collectors.toSet()));

    }

}
