package nl.tudelft.context.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import nl.tudelft.context.drawable.DefaultLabel;
import nl.tudelft.context.drawable.DrawableEdge;
import nl.tudelft.context.drawable.DrawableGraph;
import nl.tudelft.context.model.annotation.AnnotationMap;
import nl.tudelft.context.model.graph.GraphMap;
import nl.tudelft.context.model.graph.SinglePointGraph;
import nl.tudelft.context.model.graph.StackGraph;

import java.net.URL;
import java.util.Collection;
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
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 7-5-2015
 */
public final class GraphController extends DefaultGraphController {

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
     * Sources that are displayed in the graph.
     */
    Set<String> sources;

    /**
     * Property with graph map.
     */
    ReadOnlyObjectProperty<GraphMap> graphMapIn;

    /**
     * Property with annotation map.
     */
    ReadOnlyObjectProperty<AnnotationMap> annotationMapIn;

    /**
     * List of graph views.
     */
    LinkedList<StackGraph> graphList = new LinkedList<>();

    /**
     * Init a controller at graph.fxml.
     *
     * @param mainController  MainController for the application
     * @param sources         Sources to display
     * @param graphMapIn      The graphMap from the workspace, might not be loaded.
     * @param annotationMapIn The AnnotationMap from the workspace, might not be loaded.
     */
    public GraphController(final MainController mainController,
                           final Set<String> sources,
                           final ReadOnlyObjectProperty<GraphMap> graphMapIn,
                           final ReadOnlyObjectProperty<AnnotationMap> annotationMapIn) {

        super(mainController);
        this.sources = sources;

        this.graphMapIn = graphMapIn;
        this.annotationMapIn = annotationMapIn;

        loadFXML("/application/graph.fxml");
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
    public void initialize(final URL location, final ResourceBundle resources) {

        ObjectProperty<GraphMap> graphMapProperty = new SimpleObjectProperty<>();
        ObjectProperty<AnnotationMap> annotationMapProperty = new SimpleObjectProperty<>();

        graphMapProperty.addListener((observable, oldValue, newValue) -> {
            loadGraph(newValue);
        });

        annotationMapProperty.addListener((observable, oldValue, newValue) -> {
            loadAnnotation(newValue);
        });

        graphMapProperty.bind(graphMapIn);
        annotationMapProperty.bind(annotationMapIn);

        progressIndicator.visibleProperty().bind(graphMapProperty.isNull());

    }

    /**
     * Load graph from source.
     *
     * @param graphMap The GraphMap which is loaded.
     */
    private void loadGraph(final GraphMap graphMap) {
        graphList.add(graphMap.flat(sources));
        graphList.add(new SinglePointGraph(graphList.getLast()));
        DrawableGraph drawableGraph = new DrawableGraph(graphList.getLast());
        showGraph(drawableGraph);
    }

    /**
     * Load annotation from source.
     *
     * @param annotationMap The annotationmap which is loaded.
     */
    private void loadAnnotation(final AnnotationMap annotationMap) {
    }


    /**
     * Show graph with reference points.
     *
     * @param drawableGraph Graph to show
     */
    private void showGraph(final DrawableGraph drawableGraph) {

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

    /**
     * Get the graph list.
     *
     * @return Graph list
     */
    public LinkedList<StackGraph> getGraphList() {
        return graphList;
    }

    @Override
    public String getBreadcrumbName() {
        return "Genome graph (" + sources.size() + ")";
    }

    @Override
    public void activate() {
        // empty method
    }

    @Override
    public void deactivate() {
        // empty method
    }
}
