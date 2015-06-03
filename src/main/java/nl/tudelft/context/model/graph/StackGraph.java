package nl.tudelft.context.model.graph;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 1-6-2015
 */
public abstract class StackGraph extends DefaultGraph<DefaultNode> {

    /**
     * Clone an other graph into this graph.
     *
     * @param stackGraph Graph to clone
     */
    protected void setGraph(StackGraph stackGraph) {

        stackGraph.vertexSet().stream()
                .forEach(this::addVertex);

        stackGraph.edgeSet().stream()
                .forEach(edge -> setEdgeWeight(addEdge(
                        stackGraph.getEdgeSource(edge),
                        stackGraph.getEdgeTarget(edge)
                ), stackGraph.getEdgeWeight(edge)));

    }

}
