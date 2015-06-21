package nl.tudelft.context.controller.graphlist;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
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
    private Pane graphs;


    /**
     * Create a graph list controller.
     *
     * @param graphs FXML Pane to add graphs labels to.
     */
    public GraphFilterController(final Pane graphs) {
        this.graphs = graphs;
        graphList = FXCollections.observableArrayList();

        graphList.addListener((ListChangeListener<GraphFilterLabel>) c -> {
            while (c.next()) {
                if (!c.wasPermutated() && !c.wasUpdated()) {
                    for (GraphFilterLabel remitem : c.getRemoved()) {
                        remitem.removeListener(this);
                    }
                    for (GraphFilterLabel additem : c.getAddedSubList()) {
                        additem.addListener(this);
                    }
                }
            }

            graphs.getChildren().setAll(graphList);

            activeGraph.set(createGraphFromFilter(graphList, baseGraph));
        });
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
        graphs.getChildren().setAll(graphList);

        activeGraph.set(createGraphFromFilter(graphList, baseGraph));
    }
}
