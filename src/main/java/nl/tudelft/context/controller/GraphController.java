package nl.tudelft.context.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import nl.tudelft.context.drawable.DrawableEdge;
import nl.tudelft.context.drawable.DrawableGraph;
import nl.tudelft.context.drawable.InfoLabel;
import nl.tudelft.context.model.annotation.AnnotationMap;
import nl.tudelft.context.model.graph.GraphMap;
import nl.tudelft.context.model.graph.SinglePointGraph;
import nl.tudelft.context.model.graph.StackGraph;

import java.net.URL;
import java.util.Collection;
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
public final class GraphController extends ViewController<AnchorPane> {

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
     * Reference to the MainController of the app.
     */
    MainController mainController;

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

        super(new AnchorPane());

        this.mainController = mainController;
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
        StackGraph graph = graphMap.flat(sources);
        graph = new SinglePointGraph(graph);
        DrawableGraph drawableGraph = new DrawableGraph(graph);
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
        List<InfoLabel> nodeList = drawableGraph.vertexSet().stream()
                .map(node -> new InfoLabel(mainController, this, drawableGraph.getGraph(), node))
                .collect(Collectors.toList());

        sequences.getChildren().addAll(edgeList);
        initOnTheFlyLoading(nodeList);

    }

    /**
     * Listen to position and load on the fly.
     *
     * @param nodeList Labels to to load on the fly
     */
    private void initOnTheFlyLoading(final List<InfoLabel> nodeList) {

        Map<Integer, List<InfoLabel>> map = nodeList.stream().collect(
                Collectors.groupingBy(
                        InfoLabel::currentColumn,
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
    private void showCurrentLabels(final Map<Integer, List<InfoLabel>> map) {

        double width = scroll.getWidth();
        double left = (scroll.getContent().layoutBoundsProperty().getValue().getWidth() - width)
                * scroll.getHvalue();
        int indexFrom = (int) Math.floor(left / DrawableGraph.LABEL_SPACING) - 1;
        int indexTo = indexFrom + (int) Math.ceil(width / DrawableGraph.LABEL_SPACING) + 1;

        List<InfoLabel> infoLabels = IntStream.rangeClosed(indexFrom, indexTo)
                .mapToObj(map::remove)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        infoLabels.forEach(InfoLabel::init);
        sequences.getChildren().addAll(infoLabels);

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
