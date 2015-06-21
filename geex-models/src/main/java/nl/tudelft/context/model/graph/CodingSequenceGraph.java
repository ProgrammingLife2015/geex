package nl.tudelft.context.model.graph;

/**
 * @author René Vennik
 * @version 1.0
 * @since 21-6-2015
 */
public class CodingSequenceGraph extends StackGraph {

    /**
     * Clean graph.
     */
    StackGraph graph;

    /**
     * Create a graph with showing coding sequences only.
     *
     * @param graph Graph to remove not coding sequences from
     */
    public CodingSequenceGraph(final StackGraph graph) {

        this.graph = graph;

        BubbleReduction bubbleReduction =
                new BubbleReduction(graph, defaultNode -> defaultNode.getCodingSequences().size() > 0);

    }

}
