package nl.tudelft.context.model.graph.filter;

import nl.tudelft.context.model.graph.Graph;
import nl.tudelft.context.model.graph.StackGraph;

/**
 * @author René Vennik
 * @version 1.0
 * @since 21-6-2015
 */
public class CodingSequenceFilter implements StackGraphFilter {

    private final StackGraph filtered;
    private final StackGraph previous;

    /**
     * Create a graph with showing coding sequences only.
     *
     * @param graph Graph to remove not coding sequences from
     */
    public CodingSequenceFilter(final StackGraph graph) {
        this.previous = graph;
        this.filtered = new Graph();
    }

    @Override
    public StackGraph getFilterGraph() {
        BubbleReduction bubbleReduction = new BubbleReduction(previous, filtered);
        bubbleReduction.markBubbles(defaultNode -> defaultNode.getCodingSequences().size() == 0
                && defaultNode.getResistances().size() == 0);

        return filtered;
    }
}
