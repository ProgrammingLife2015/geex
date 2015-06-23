package nl.tudelft.context.controller.graphlist;

import nl.tudelft.context.model.graph.BaseLengthGraph;
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
public enum GraphFilter {
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
    UNKNOWN(UnknownGraph.class, "Unknown bases (" + (UnknownGraph.REMOVE_RATIO * 100) + "%)"),
    /**
     * Class for hiding nodes which are too short.
     */
    BASE_LENGTH(BaseLengthGraph.class, "Base length (" + BaseLengthGraph.THRESHOLD + ")");

    /**
     * Class with filter.
     */
    private final Class<? extends StackGraph> graph;
    /**
     * Name of this filter.
     */
    private final String name;

    /**
     * Get the filter for this enum.
     * @return The filter
     */
    public final Class<? extends StackGraph> getGraph() {
        return graph;
    }

    @Override
    public final String toString() {
        return name;
    }

    /**
     * Create a new GraphFilterEnum, this enum is used for creating filters on the graph.
     *
     * @param graph Class of the filter.
     * @param name Name of the filter.
     */
    GraphFilter(final Class<? extends StackGraph> graph, final String name) {
        this.graph = graph;
        this.name = name;
    }
}
