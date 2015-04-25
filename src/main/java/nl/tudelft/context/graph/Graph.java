package nl.tudelft.context.graph;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.*;

/**
 * Graph
 *
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 23-4-2015
 */
public class Graph extends SimpleGraph<Node, DefaultEdge> {

    Map<Integer, Node> vertexes = new HashMap<>();
    Set<Integer> referencePoints = new TreeSet<>();

    Map<Integer, Set<Node>> nodeStartAt = new HashMap<>();
    Map<Integer, Set<Node>> nodeEndAt = new HashMap<>();

    /**
     * Create a Graph with default edges.
     */
    public Graph() {

        super(DefaultEdge.class);

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

        referencePoints.add(vertex.getRefStartPosition());
        referencePoints.add(vertex.getRefEndPosition());

        addVertexToMap(vertex, vertex.getRefStartPosition(), nodeStartAt);
        addVertexToMap(vertex, vertex.getRefEndPosition(), nodeEndAt);

        return super.addVertex(vertex);

    }

    /**
     * Add node to start and end map depending on ref points.
     *
     * @param vertex node to add
     */
    protected void addVertexToMap(Node vertex, int position, Map<Integer, Set<Node>> map) {

        if (!map.containsKey(position)) {
            map.put(position, new HashSet<>());
        }

        map.get(position).add(vertex);

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

    /**
     * Get vertexes by reference start position.
     *
     * @param startPosition start position of nodes
     */
    public Set<Node> getVertexByStartPosition(int startPosition) {

        return nodeStartAt.get(startPosition);

    }

    /**
     * Get vertexes by reference end position.
     *
     * @param endPosition end position of nodes
     */
    public Set<Node> getVertexByEndPosition(int endPosition) {

        return nodeEndAt.get(endPosition);

    }

    /**
     * Get all reference points
     */
    public Set<Integer> getReferencePoints() {

        return referencePoints;

    }

}
