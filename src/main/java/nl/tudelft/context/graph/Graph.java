package nl.tudelft.context.graph;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.Optional;

/**
 * Graph
 *
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 23-4-2015
 */
public class Graph extends DefaultDirectedGraph<Node, DefaultEdge> {

    /**
     * Create a Graph with default edges.
     */
    public Graph() {

        super(DefaultEdge.class);

    }

    /**
     * Get the first node, with no incoming edges.
     *
     * @return first node
     */
    public Node getFirstNode() {

        Optional<Node> node = vertexSet().stream().filter(x -> this.inDegreeOf(x) == 0).findFirst();
        return node.orElseGet(null);

    }

}
