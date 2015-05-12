package nl.tudelft.context.graph;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Graph.
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
     * Get the first nodes, with no incoming edges.
     *
     * @return all first node
     */
    public List<Node> getFirstNodes() {

        return vertexSet().stream().filter(x -> this.inDegreeOf(x) == 0).collect(Collectors.toList());

    }

}
