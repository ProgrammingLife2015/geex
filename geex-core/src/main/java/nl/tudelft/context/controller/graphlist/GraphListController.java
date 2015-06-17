package nl.tudelft.context.controller.graphlist;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import nl.tudelft.context.model.graph.StackGraph;

import java.util.LinkedList;

/**
 * @author René Vennik
 * @version 1.0
 * @since 17-6-2015
 */
public class GraphListController {

    /**
     * List of graph views.
     */
    LinkedList<StackGraph> graphList = new LinkedList<>();

    /**
     * Active graph property.
     */
    ObjectProperty<StackGraph> activeGraph = new SimpleObjectProperty<>();

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
     * Add a graph to the graph list.
     *
     * @param stackGraph Graph to add
     */
    public void add(final StackGraph stackGraph) {
        graphList.add(stackGraph);
        activeGraph.set(stackGraph);
    }

    /**
     * Zoom in.
     */
    public void zoomIn() {

        StackGraph newGraph = graphList.get(Math.max(graphList.indexOf(getActiveGraph()) - 1, 0));
        activeGraph.setValue(newGraph);

    }

    /**
     * Zoom out.
     */
    public void zoomOut() {

        StackGraph newGraph = graphList.get(Math.min(graphList.indexOf(getActiveGraph()) + 1, graphList.size() - 1));
        activeGraph.setValue(newGraph);

    }

    /**
     * Reset the view.
     */
    public void reset() {
        activeGraph.setValue(graphList.getLast());
    }

}
