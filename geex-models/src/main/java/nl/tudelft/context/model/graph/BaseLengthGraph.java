package nl.tudelft.context.model.graph;

import java.util.function.Predicate;

/**
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 23-06-2015
 */
public class BaseLengthGraph extends FilterGraph {
    /**
     * Minimum unknown ratio to remove.
     */
    public static final double THRESHOLD = 30;

    /**
     * Create a graph with filtered unknown on an other graph.
     *
     * @param graph Graph to remove unkowns form.
     */
    public BaseLengthGraph(final StackGraph graph) {
        super(graph);
    }

    @Override
    protected Predicate<DefaultNode> filter() {
        return defaultNode -> defaultNode.getContent().length() <= THRESHOLD;
    }

}
