package nl.tudelft.context.model.graph;

import java.util.function.Predicate;

/**
 * @author René Vennik
 * @version 1.0
 * @since 12-6-2015
 */
public class UnknownGraph extends FilterGraph {

    /**
     * Minimum unknown ratio to remove.
     */
    public static final double REMOVE_RATIO = .75;

    /**
     * Create a graph with filtered unknown on an other graph.
     *
     * @param graph Graph to remove unkowns form.
     */
    public UnknownGraph(final StackGraph graph) {
        super(graph);
    }

    @Override
    protected Predicate<DefaultNode> filter() {
        return defaultNode -> defaultNode.getBaseCounter().getRatio('N') >= REMOVE_RATIO;
    }

    @Override
    public String getName() {
        return "Unknown bases (" + (REMOVE_RATIO * 100) + "%)";
    }

}
