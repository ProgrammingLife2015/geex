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
     * Minimum base length to keep.
     */
    public static final int THRESHOLD = 30;

    /**
     * Create a graph with filtered small base lengths on an other graph.
     *
     * @param graph Graph to remove small base length nodes from
     */
    public BaseLengthFilter(final StackGraph graph) {
        super(graph);
    }

    @Override
    protected Predicate<DefaultNode> filter() {
        return defaultNode -> defaultNode instanceof Node && defaultNode.getContent().length() <= THRESHOLD;
    }

}
