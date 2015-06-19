package nl.tudelft.context.controller.graphlist;

import nl.tudelft.context.model.graph.CollapseGraph;
import nl.tudelft.context.model.graph.InsertDeleteGraph;
import nl.tudelft.context.model.graph.SinglePointGraph;
import nl.tudelft.context.model.graph.StackGraph;
import nl.tudelft.context.model.graph.UnknownGraph;

/**
 * List the available graph filters.
 *
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 18-6-2015
 */
public enum GraphFilterEnum {
    SINGLE_POINT(SinglePointGraph.class, "Point mutations"),
    INSERT_DELETE(InsertDeleteGraph.class, "Insertions / Deletions"),
    COLLAPSE(CollapseGraph.class, "Straight sequences"),
    UNKNOWN(UnknownGraph.class, "Unknown bases (" + (UnknownGraph.REMOVE_RATIO * 100) + "%)");

    private Class<? extends StackGraph> graph;
    private String name;
    private boolean active;

    public Class<? extends StackGraph> getGraph() {
        return graph;
    }

    @Override
    public String toString() {
        return name;
    }

    GraphFilterEnum(final Class<? extends StackGraph> graph, final String name) {
        this.graph = graph;
        this.name = name;
    }

    /**
     * Set the active state of this GraphFilter.
     * @param active New state.
     */
    public void setActive(final boolean active) {
        this.active = active;
    }

    /**
     * Get if this filter is active.
     * @return State of this filter.
     */
    public boolean isActive() {
        return active;
    }
}
