package nl.tudelft.context.model.graph.filter;

import nl.tudelft.context.model.graph.DefaultNode;
import nl.tudelft.context.model.graph.StackGraph;

import java.util.function.Predicate;

/**
 * @author René Vennik
 * @version 1.0
 * @since 12-6-2015
 */
public class UnknownFilter extends FilterGraph {

    /**
     * Minimum unknown ratio to remove.
     */
    public static final double REMOVE_RATIO = .75;

    /**
     * Create a graph with filtered unknown on an other graph.
     *
     * @param graph Graph to remove unknowns from
     */
    public UnknownFilter(final StackGraph graph) {
        super(graph);
    }

    @Override
    protected Predicate<DefaultNode> filter() {
        return defaultNode -> defaultNode.getBaseCounter().getRatio('N') >= REMOVE_RATIO;
    }

}
