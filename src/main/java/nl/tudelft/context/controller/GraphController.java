package nl.tudelft.context.controller;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.MenuItem;
import nl.tudelft.context.drawable.DrawableGraph;
import nl.tudelft.context.model.annotation.AnnotationMap;
import nl.tudelft.context.model.graph.GraphMap;
import nl.tudelft.context.model.graph.SinglePointGraph;
import nl.tudelft.context.model.resistance.ResistanceMap;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 7-5-2015
 */
public final class GraphController extends AbstractGraphController {

    /**
     * Sources that are displayed in the graph.
     */
    Set<String> sources;

    /**
     * Sources that are displayed in the graph.
     */
    ObjectProperty<Set<String>> selectedSources = new SimpleObjectProperty<>(new HashSet<>());

    /**
     * Select controller to select strains.
     */
    SelectNewickController selectNewickController = new SelectNewickController(
            this,
            mainController.getWorkspace().getNewick()
    );;

    /**
     * Property with graph map.
     */
    ReadOnlyObjectProperty<GraphMap> graphMapIn;

    /**
     * Sources that are displayed in the graph.
     * Property with annotation map.
     */
    ReadOnlyObjectProperty<AnnotationMap> annotationMapIn;

    /**
     * Property with resistance map.
     */
    ReadOnlyObjectProperty<ResistanceMap> resistanceMapIn;

    /**
     * Init a controller at graph.fxml.
     *
     * @param mainController  MainController for the application
     * @param sources         Sources to display
     * @param graphMapIn      The graphMap from the workspace, might not be loaded.
     * @param annotationMapIn The AnnotationMap from the workspace, might not be loaded.
     * @param resistanceMapIn The ResistanceMap from the workspace, might not be loaded.
     */
    public GraphController(final MainController mainController,
                           final Set<String> sources,
                           final ReadOnlyObjectProperty<GraphMap> graphMapIn,
                           final ReadOnlyObjectProperty<AnnotationMap> annotationMapIn,
                           final ReadOnlyObjectProperty<ResistanceMap> resistanceMapIn) {

        super(mainController);
        this.sources = sources;

        this.graphMapIn = graphMapIn;
        this.annotationMapIn = annotationMapIn;
        this.resistanceMapIn = resistanceMapIn;

        loadFXML("/application/graph.fxml");
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

        super.initialize(location, resources);

        ObjectProperty<GraphMap> graphMapProperty = new SimpleObjectProperty<>();
        ObjectProperty<AnnotationMap> annotationMapProperty = new SimpleObjectProperty<>();
        ObjectProperty<ResistanceMap> resistanceMapProperty = new SimpleObjectProperty<>();

        graphMapProperty.addListener((observable, oldValue, newValue) -> {
            loadGraph(newValue);
        });

        annotationMapProperty.addListener((observable, oldValue, newValue) -> {
            loadAnnotation(newValue);
        });

        resistanceMapProperty.addListener((observable, oldValue, newValue) -> {
            loadResistance(newValue);
        });

        selectedSources.addListener(event -> System.out.println(selectedSources));

        graphMapProperty.bind(graphMapIn);
        annotationMapProperty.bind(annotationMapIn);
        resistanceMapProperty.bind(resistanceMapIn);

        progressIndicator.visibleProperty().bind(graphMapProperty.isNull());

        initMenu();

    }

    /**
     * Bind the menu buttons.
     */
    private void initMenu() {

        MenuController menuController = mainController.getMenuController();

        MenuItem zoomIn = menuController.getZoomIn();
        zoomIn.setOnAction(event -> showGraph(new DrawableGraph(graphList.getFirst())));
        zoomIn.disableProperty().bind(activeProperty.not());

        MenuItem zoomOut = menuController.getZoomOut();
        zoomOut.setOnAction(event -> showGraph(new DrawableGraph(graphList.getLast())));
        zoomOut.disableProperty().bind(activeProperty.not());

        MenuItem toggleSelect = menuController.getToggleSelect();
        toggleSelect.setOnAction(event -> mainController.setView(this, selectNewickController));
        toggleSelect.disableProperty().bind(activeProperty.not());

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
     * Update the selected sources.
     *
     * @param sources New selected sources.
     */
    public void updateSelectedSources(final Set<String> sources) {
        selectedSources.setValue(sources);
    }

    /**
     * Load annotation from source.
     *
     * @param annotationMap The annotation map which is loaded.
     */
    private void loadAnnotation(final AnnotationMap annotationMap) {
        mainController.displayMessage(MessageController.SUCCESS_LOAD_ANNOTATION);
        //
    }

    /**
     * Load resistances from source.
     *
     * @param resistanceMap The resistance map which is loaded.
     */
    private void loadResistance(final ResistanceMap resistanceMap) {
        mainController.displayMessage(MessageController.SUCCESS_LOAD_RESISTANCE);
    }

    @Override
    public String getBreadcrumbName() {
        return "Genome graph (" + sources.size() + ")";
    }
}
