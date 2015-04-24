package nl.tudelft.context.graph;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.HashMap;
import java.util.Map;

/**
 * Graph
 *
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 23-4-2015
 */
public class Graph extends SimpleGraph<Node, DefaultEdge> {

    Map<Integer, Node> vertexes;

    /**
     * Create a Graph with default edges.
     */
    public Graph() {

        super(DefaultEdge.class);

        vertexes = new HashMap<>();

    }

    /**
     * Add vertex to graph.
     *
     * @param vertex vertex to add
     * @return if graph already contained vertex
     */
    @Override
    public boolean addVertex(Node vertex) {

        vertexes.put(vertex.getId(), vertex);
        return super.addVertex(vertex);

    }

    /**
     * Get vertex by id.
     *
     * @param id vertex id
     * @return vertex if found or null
     */
    public Node getVertexById(int id) {

        return vertexes.get(id);

    }

}
