package nl.tudelft.context.model.graph.filter;

import nl.tudelft.context.model.graph.Graph;
import nl.tudelft.context.model.graph.StackGraph;
import nl.tudelft.context.model.graph.filter.BubbleReduction;
import nl.tudelft.context.model.graph.filter.StackGraphFilter;

/**
 * @author René Vennik
 * @version 1.0
 * @since 21-6-2015
 */
public class ResistanceCausingMutationFilter implements StackGraphFilter {

    private final StackGraph filtered;
    private final StackGraph previous;

    /**
     * Create a graph with showing the resistance causing mutations only.
     *
     * @param graph Graph to remove not resistance causing mutations from
     */
    public ResistanceCausingMutationFilter(final StackGraph graph) {
        this.previous = graph;
        this.filtered = new Graph();
    }

    @Override
    public StackGraph getFilterGraph() {
        BubbleReduction bubbleReduction = new BubbleReduction(previous, filtered);
        bubbleReduction.markBubbles(defaultNode -> defaultNode.getResistances().size() == 0);
//        bubbleReduction.fillGraph(this, "resistance");

        return filtered;
    }
}
