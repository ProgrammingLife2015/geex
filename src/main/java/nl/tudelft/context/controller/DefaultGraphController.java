package nl.tudelft.context.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import nl.tudelft.context.drawable.DefaultLabel;
import nl.tudelft.context.drawable.DrawableEdge;
import nl.tudelft.context.drawable.DrawableGraph;
import nl.tudelft.context.drawable.Location;
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
     * Location of the scroll bar.
     */
    ObjectProperty<Location> locationProperty = new SimpleObjectProperty<>(new Location(0, 0));

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

        scroll.widthProperty().addListener(event -> updateLocation());
        scroll.hvalueProperty().addListener(event -> updateLocation());

        locationProperty.addListener(event -> showCurrentLabels());

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

        Map<Integer, List<DefaultLabel>> cleanLabelMap = new HashMap<>(labelMap);
        new Zoom(scroll, sequences, cleanLabelMap);
        new LocatorController(locator, scroll, locationProperty, cleanLabelMap);

        showCurrentLabels();

    }

    /**
     * Update to the current location.
     */
    private void updateLocation() {

        double width = scroll.getWidth();
        double left = (scroll.getContent().layoutBoundsProperty().getValue().getWidth() - width)
                * scroll.getHvalue();

        int indexFrom = (int) Math.round(left / DrawableGraph.LABEL_SPACING) - 1;
        int indexTo = indexFrom + (int) Math.round(width / DrawableGraph.LABEL_SPACING) + 1;
        locationProperty.setValue(new Location(indexFrom, indexTo));

    }

    /**
     * Show all the labels on current position.
     */
    private void showCurrentLabels() {

        Location current = locationProperty.get();

        List<DefaultLabel> infoLabels = IntStream.rangeClosed(current.getStart(), current.getEnd())
                .mapToObj(labelMap::remove)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        infoLabels.forEach(DefaultLabel::init);
        sequences.getChildren().addAll(infoLabels);

    }

}
