package nl.tudelft.context.graph;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

/**
 * Graph
 *
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 23-4-2015
 */
public class Graph extends SimpleGraph<Node, DefaultEdge> {

    /**
     * Create a Graph with default edges.
     */
    public Graph() {

        super(DefaultEdge.class);

    }

}
