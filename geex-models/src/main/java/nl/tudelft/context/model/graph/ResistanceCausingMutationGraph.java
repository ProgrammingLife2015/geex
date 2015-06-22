package nl.tudelft.context.model.graph;

/**
 * @author René Vennik
 * @version 1.0
 * @since 21-6-2015
 */
public class ResistanceCausingMutationGraph extends StackGraph {

    /**
     * Create a graph with showing the resistance causing mutations only.
     *
     * @param graph Graph to remove not resistance causing mutations from
     */
    public ResistanceCausingMutationGraph(final StackGraph graph) {

        BubbleReduction bubbleReduction = new BubbleReduction(graph);
        bubbleReduction.applyFilter(defaultNode -> defaultNode.getResistances().size() > 0);
        bubbleReduction.fillGraph(this);

    }
}
