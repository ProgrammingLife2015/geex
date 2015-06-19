package nl.tudelft.context.controller.graphlist;

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
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author René Vennik
 * @version 1.0
 * @since 17-6-2015
 */
public class GraphListController {

    /**
     * List of graph views.
     */
    ObservableList<GraphFilterEnum> graphList;

    /**
     * Active graph property.
     */
    ObjectProperty<StackGraph> activeGraph = new SimpleObjectProperty<>();

    private StackGraph baseGraph;


    /**
     * Create a graph list controller.
     *
     * @param graphs FXML Pane to add graphs labels to.
     */
    public GraphListController(final Pane graphs) {
        graphList = FXCollections.observableArrayList();

        activeGraph.addListener((observable, oldValue, newValue) -> {

        });

        graphList.addListener((ListChangeListener<GraphFilterEnum>) c -> {
            List<GraphListItem> graphListItemList = graphList.stream()
                    .map(graphFilter -> new GraphListItem(graphFilter, graphList))
                    .collect(Collectors.toList());

            graphs.getChildren().setAll(graphListItemList);

            activeGraph.set(createGraphFromFilter(graphList, baseGraph));

            Log.debug("Updating graphlist");
        });
    }

    public void setBaseGraph(StackGraph baseGraph) {
        this.baseGraph = baseGraph;
    }

    /**
     * Use the filters in graphs, to create a new StackGraph.
     *
     * @param graphs    List of filters
     * @param baseGraph Graph to use as base.
     * @return
     */
    private StackGraph createGraphFromFilter(final ObservableList<GraphFilterEnum> graphs, final StackGraph baseGraph) {
        StackGraph newGraph = baseGraph;
        for (GraphFilterEnum gfe : graphs) {
            if (!gfe.isActive()) {
                break;
            }
            try {
                Class<? extends StackGraph> clazz = gfe.getGraph();
                Constructor<? extends StackGraph> constructor = clazz.getDeclaredConstructor(StackGraph.class);
                newGraph = constructor.newInstance(newGraph);
            } catch (ReflectiveOperationException e) {
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

    public void addAll(GraphFilterEnum[] values) {
        graphList.setAll(Arrays.asList(values));
    }
}
