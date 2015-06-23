package nl.tudelft.context.controller.graphlist;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import nl.tudelft.context.logger.Log;
import nl.tudelft.context.model.graph.StackGraph;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author René Vennik
 * @version 1.0
 * @since 17-6-2015
 */
public class GraphFilterController implements InvalidationListener {

    /**
     * List of graph views.
     */
    ObservableList<GraphFilterLabel> graphList;

    /**
     * Active graph property.
     */
    ObjectProperty<StackGraph> activeGraph = new SimpleObjectProperty<>();

    /**
     * Base of this StackGraph.
     */
    private StackGraph baseGraph;
    /**
     * Pane containing the javafx labels.
     */
    private Pane filterList;


    /**
     * Create a graph list controller.
     *
     * @param graphs FXML Pane to add graphs labels to.
     */
    public GraphFilterController(final Pane graphs) {
        graphList = FXCollections.observableArrayList();


        filterList = new VBox();
        graphList.addListener(onGraphListChange(graphs));
        graphs.getChildren().addAll(
                new ScrollPane(filterList),
                createNewFilter(),
                new TrashCan());
    }

    /**
     * Add and remove listeners, update the graphs list when the graph list changes.
     *
     * @param graphs List of graphs to put the list in.
     * @return ChangeListener, to use when graphList updates.
     */
    private ListChangeListener<GraphFilterLabel> onGraphListChange(final Pane graphs) {
        return c -> {
            while (c.next()) {
                if (!c.wasPermutated() && !c.wasUpdated()) {
                    c.getRemoved().forEach(graphFilterLabel -> graphFilterLabel.removeListener(this));
                    c.getAddedSubList().forEach(graphFilterLabel -> graphFilterLabel.addListener(this));
                }
            }
            filterList.getChildren().setAll(graphList);
            activeGraph.set(createGraphFromFilter(graphList, baseGraph));
        };
    }

    private HBox createNewFilter() {
        HBox newFilter = new HBox();
        newFilter.getStyleClass().add("createNewFilter");

        ObservableList<GraphFilter> filters = FXCollections.observableArrayList(GraphFilter.values());

        ComboBox<GraphFilter> newFilterList = new ComboBox<>(filters);
        newFilterList.setPromptText("Add a filter..");
        Button createNewFilter = new Button("+");
        createNewFilter.getStyleClass().add("my-button");

        createNewFilter.setOnMouseClicked(event -> {
            if (newFilterList.getSelectionModel().getSelectedItem() != null) {
                graphList.add(new GraphFilterLabel(newFilterList.getSelectionModel().getSelectedItem(), graphList));
            }
        });

        newFilter.getChildren().addAll(newFilterList, createNewFilter);
        return newFilter;
    }

    /**
     * Set the base of this graph.
     *
     * @param baseGraph New base StackGraph
     */
    public void setBaseGraph(final StackGraph baseGraph) {
        this.baseGraph = baseGraph;
    }

    /**
     * Use the filters in graphs, to create a new StackGraph.
     *
     * @param graphs    List of filters
     * @param baseGraph Graph to use as base.
     * @return A combined graph.
     */
    private StackGraph createGraphFromFilter(final ObservableList<GraphFilterLabel> graphs,
                                             final StackGraph baseGraph) {
        StackGraph newGraph = baseGraph;
        for (GraphFilterLabel gli : graphs) {
            if (!gli.isActive()) {
                continue;
            }
            try {
                Class<? extends StackGraph> clazz = gli.getFilter().getGraph();
                Constructor<? extends StackGraph> constructor = clazz.getDeclaredConstructor(StackGraph.class);
                newGraph = constructor.newInstance(newGraph);
            } catch (ReflectiveOperationException e) {
                // Something went VERY wrong.
                Log.debug(e.getMessage());
            }
        }

        return newGraph;
    }

    /**
     * Get the current active graph.
     *
     * @return The current active graph
     */
    public StackGraph getActiveGraph() {
        return activeGraph.get();
    }

    /**
     * Get the current active graph readonly property.
     *
     * @return The current active graph readonly property
     */
    public ReadOnlyObjectProperty<StackGraph> getActiveGraphProperty() {
        return activeGraph;
    }

    /**
     * Reset the view.
     */
    public void reset() {
        activeGraph.setValue(baseGraph);
    }

    /**
     * Add a list of GraphFilterEnum.
     *
     * @param values GraphFilters to add
     */
    public void addAll(final GraphFilter[] values) {
        graphList.setAll(Arrays.asList(values).stream()
                .map(graphFilterEnum -> new GraphFilterLabel(graphFilterEnum, graphList))
                .collect(Collectors.toList()));
    }

    @Override
    public void invalidated(final Observable observable) {
        filterList.getChildren().setAll(graphList);

        activeGraph.set(createGraphFromFilter(graphList, baseGraph));
    }
}
