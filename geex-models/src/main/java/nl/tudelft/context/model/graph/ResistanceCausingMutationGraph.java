package nl.tudelft.context.model.graph;

/**
 * @author René Vennik
 * @version 1.0
 * @since 21-6-2015
 */
public class ResistanceCausingMutationGraph extends StackGraph {

    /**
     * Clean graph.
     */
    StackGraph graph;

    /**
     * Create a graph with showing the resistance causing mutations only.
     *
     * @param graph Graph to remove not resistance causing mutations from
     */
    public ResistanceCausingMutationGraph(final StackGraph graph) {

        this.graph = graph;
        setGraph(graph);

    }
}
