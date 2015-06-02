package nl.tudelft.context.model.graph;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @param <T> Type of the nodes
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 31-5-2015
 */
public abstract class DefaultGraph<T> extends DefaultDirectedGraph<T, DefaultEdge> {

    /**
     * Create a default graph.
     */
    public DefaultGraph() {
        super(DefaultEdge.class);
    }

    /**
     * Get the first nodes, with no incoming edges.
     *
     * @return all first node
     */
    public List<T> getFirstNodes() {

        return vertexSet().stream()
                .filter(x -> this.inDegreeOf(x) == 0)
                .collect(Collectors.toList());

    }

    /**
     * Get all targets from a node.
     *
     * @param node Node to get targets from
     * @return The targets of the node
     */
    public List<T> getTargets(final T node) {

        return outgoingEdgesOf(node).stream()
                .map(this::getEdgeTarget)
                .collect(Collectors.toList());

    }

    /**
     * Get all sources from a node.
     *
     * @param node Node to get sources from
     * @return The sources of the node
     */
    public List<T> getSources(final T node) {

        return incomingEdgesOf(node).stream()
                .map(this::getEdgeSource)
                .collect(Collectors.toList());

    }

    /**
     * Replace a node with an other node.
     *
     * @param oldNode Old node
     * @param newNode New node
     */
    public void replace(final T oldNode, final T newNode) {

        addVertex(newNode);

        getTargets(oldNode).stream().forEach(node -> addEdge(newNode, node));
        getSources(oldNode).stream().forEach(node -> addEdge(node, newNode));

        removeVertex(oldNode);

    }

}
