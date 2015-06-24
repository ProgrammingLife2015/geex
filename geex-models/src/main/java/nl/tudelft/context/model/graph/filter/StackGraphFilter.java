package nl.tudelft.context.model.graph.filter;

import nl.tudelft.context.model.graph.StackGraph;

/**
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 23-6-2015
 */
public interface StackGraphFilter {
    /**
     * Clone and apply a filter to the stackGraph.
     *
     * @return StackGraph with this filter.
     */
    StackGraph getFilterGraph();
}
