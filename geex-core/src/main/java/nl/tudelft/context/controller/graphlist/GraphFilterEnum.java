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
    /**
     * Class for filtering a Single Point mutation graph.
     */
    SINGLE_POINT(SinglePointGraph.class, "Point mutations"),
    /**
     * Class for filtering indels.
     */
    INSERT_DELETE(InsertDeleteGraph.class, "Insertions / Deletions"),
    /**
     * Class for collapsing straight lines.
     */
    COLLAPSE(CollapseGraph.class, "Straight sequences"),
    /**
     * Class for hiding nodes with too many unknowns.
     */
    UNKNOWN(UnknownGraph.class, "Unknown bases (" + (UnknownGraph.REMOVE_RATIO * 100) + "%)");

    /**
     * Class with filter.
     */
    private Class<? extends StackGraph> graph;
    /**
     * Name of this filter.
     */
    private String name;
    /**
     * Whether this filter is active.
     */
    private boolean active;

    /**
     * Get the filter for this enum.
     * @return The filter
     */
    public Class<? extends StackGraph> getGraph() {
        return graph;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Create a new GraphFilterEnum, this enum is used for creating filters on the graph.
     *
     * @param graph Class of the filter.
     * @param name Name of the filter.
     */
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
