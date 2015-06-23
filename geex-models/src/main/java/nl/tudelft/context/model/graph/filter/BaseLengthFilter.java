package nl.tudelft.context.model.graph.filter;

import nl.tudelft.context.model.graph.DefaultNode;
import nl.tudelft.context.model.graph.Node;
import nl.tudelft.context.model.graph.StackGraph;

import java.util.function.Predicate;

/**
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 23-06-2015
 */
public final class BaseLengthFilter extends FilterGraph {
    /**
     * Minimum unknown ratio to remove.
     */
    public static final double THRESHOLD = 30;

    /**
     * Create a graph with filtered unknown on an other graph.
     *
     * @param graph Graph to remove unkowns form.
     */
    public BaseLengthFilter(final StackGraph graph) {
        super(graph);
    }

    @Override
    protected Predicate<DefaultNode> filter() {
        return defaultNode -> defaultNode instanceof Node && defaultNode.getContent().length() <= THRESHOLD;
    }

}
