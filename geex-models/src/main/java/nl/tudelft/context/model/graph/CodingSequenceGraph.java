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
        setGraph(graph);

    }

    /**
     * Get the name for the graph list view.
     *
     * @return The name for the graph list view
     */
    @Override
    public String getName() {
        return "Coding sequences";
    }

}
