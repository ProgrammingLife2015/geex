package nl.tudelft.context.model.graph;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 1-6-2015
 */
public class SinglePointGraph extends StackGraph {

    /**
     * Create a graph with single point mutations based on an other graph.
     *
     * @param graph Graph to calculate single point mutations on
     */
    public SinglePointGraph(final StackGraph graph) {

        graph.vertexSet().stream()
                .forEach(this::addVertex);

        graph.edgeSet().stream()
                .forEach(edge -> addEdge(
                        graph.getEdgeSource(edge),
                        graph.getEdgeTarget(edge)
                ));

    }

}
