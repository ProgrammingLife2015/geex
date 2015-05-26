package nl.tudelft.context.controller;

import javafx.concurrent.Service;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import nl.tudelft.context.drawable.DrawableEdge;
import nl.tudelft.context.drawable.InfoLabel;
import nl.tudelft.context.model.annotation.AnnotationMap;
import nl.tudelft.context.model.annotation.AnnotationParser;
import nl.tudelft.context.model.graph.Graph;
import nl.tudelft.context.model.graph.GraphMap;
import nl.tudelft.context.model.graph.GraphParser;
import nl.tudelft.context.model.resistance.ResistanceMap;
import nl.tudelft.context.model.resistance.ResistanceParser;
import nl.tudelft.context.service.LoadService;
import nl.tudelft.context.workspace.Workspace;

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
     * The service for loading the Graph.
     */
    Service<GraphMap> loadGraphService;

    /**
     * The service for loading the annotations.
     */
    Service<AnnotationMap> loadAnnotationService;

    /**
     * The service for loading the resistance annotations.
     */
    Service<ResistanceMap> loadResistanceService;

    /**
     * Sources that are displayed in the graph.
     */
    Set<String> sources;

    /**
     * Init a controller at graph.fxml.
     *
     * @param mainController MainController for the application
     * @param sources        Sources to display
     */
    public GraphController(final MainController mainController, final Set<String> sources) {

        super(new AnchorPane());

        this.mainController = mainController;
        this.sources = sources;
        Workspace workspace = mainController.getWorkspace();
        this.loadGraphService = new LoadService<>(GraphParser.class, workspace.getNodeFile(), workspace.getEdgeFile());
        this.loadAnnotationService = new LoadService<>(AnnotationParser.class, workspace.getAnnotationFile());
        this.loadResistanceService = new LoadService<>(ResistanceParser.class, workspace.getResistanceFile());


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

        progressIndicator.visibleProperty().bind(loadGraphService.runningProperty());

        loadGraph();
        loadAnnotation();
        loadResistance();

    }

    /**
     * Load graph from source.
     */
    private void loadGraph() {

        loadGraphService.setOnSucceeded(event -> {
            Graph graph = loadGraphService.getValue().flat(sources);
            graph.position();
            showGraph(graph);
            mainController.displayMessage(MessageController.SUCCESS_LOAD_GRAPH);
        });
        loadGraphService.setOnFailed(event -> {
            mainController.displayMessage(MessageController.FAIL_LOAD_GRAPH);
        });
        loadGraphService.restart();

    }

    /**
     * Load annotation from source.
     */
    private void loadAnnotation() {

        loadAnnotationService.setOnSucceeded(event -> {
            AnnotationMap annotationMap = loadAnnotationService.getValue();
            mainController.displayMessage(MessageController.SUCCESS_LOAD_ANNOTATION);
            System.out.println("annotationMap = " + annotationMap.toString());


        });
        loadAnnotationService.setOnFailed(event -> {
            mainController.displayMessage(MessageController.FAIL_LOAD_ANNOTATION);
        });
        loadAnnotationService.restart();

    }

    /**
     * Load resistances from source.
     */
    private void loadResistance() {

        loadResistanceService.setOnSucceeded(event -> {
            ResistanceMap resistanceMap = loadResistanceService.getValue();
            mainController.displayMessage(MessageController.SUCCESS_LOAD_RESISTANCE);
            System.out.println("resistanceMap = " + resistanceMap.toString());


        });
        loadResistanceService.setOnFailed(event -> {
            mainController.displayMessage(MessageController.FAIL_LOAD_RESISTANCE);
        });
        loadResistanceService.restart();

    }


    /**
     * Show graph with reference points.
     *
     * @param graph Graph to show
     */
    private void showGraph(final Graph graph) {

        // Bind edges
        List<DrawableEdge> edgeList = graph.edgeSet().stream()
                .map(edge -> new DrawableEdge(graph, edge))
                .collect(Collectors.toList());

        // Bind nodes
        List<InfoLabel> nodeList = graph.vertexSet().stream()
                .map(node -> new InfoLabel(mainController, this, graph, node))
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
        int indexFrom = (int) Math.floor(left / Graph.LABEL_SPACING) - 1;
        int indexTo = indexFrom + (int) Math.ceil(width / Graph.LABEL_SPACING) + 1;

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

}
