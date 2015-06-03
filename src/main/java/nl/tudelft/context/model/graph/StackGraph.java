package nl.tudelft.context.model.graph;

import java.util.Set;

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
    protected void setGraph(final StackGraph stackGraph) {

        stackGraph.vertexSet().stream()
                .forEach(this::addVertex);

        stackGraph.edgeSet().stream()
                .forEach(edge -> setEdgeWeight(addEdge(
                        stackGraph.getEdgeSource(edge),
                        stackGraph.getEdgeTarget(edge)
                ), stackGraph.getEdgeWeight(edge)));

    }

    /**
     * Create a sub graph of this graph.
     *
     * @param nodes Nodes the sub graph will contain
     * @return Sub graph of this graph
     */
    public Graph getSubGraph(final Set<DefaultNode> nodes) {

        Graph subGraph = new Graph();

        vertexSet().stream()
                .filter(nodes::contains)
                .forEach(subGraph::addVertex);

        edgeSet().stream()
                .forEach(edge -> {
                    DefaultNode source = getEdgeTarget(edge);
                    DefaultNode target = getEdgeSource(edge);
                    if (nodes.contains(source)
                            && nodes.contains(target)) {
                        setEdgeWeight(
                                subGraph.addEdge(source, target),
                                getEdgeWeight(edge)
                        );
                    }
                });

        return subGraph;

    }

}
