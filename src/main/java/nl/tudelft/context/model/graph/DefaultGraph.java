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

}
