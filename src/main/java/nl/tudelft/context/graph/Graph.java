package nl.tudelft.context.graph;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Graph
 *
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 23-4-2015
 */
public class Graph extends DefaultDirectedGraph<Node, DefaultEdge> {

    Set<Node> nodeList = new HashSet<>();

    /**
     * Create a Graph with default edges.
     */
    public Graph() {

        super(DefaultEdge.class);

    }

    /**
     * Add node to graph and nodeList
     *
     * @param node node to add
     * @return true if graph contained specified edge
     */
    @Override
    public boolean addVertex(Node node) {

        nodeList.add(node);
        return super.addVertex(node);

    }

    /**
     * Get the first node, with no incoming edges.
     *
     * @return first node
     */
    public Node getFirstNode() {

        Optional<Node> node = nodeList.stream().filter(x -> this.inDegreeOf(x) == 0).findFirst();
        return node.orElseGet(null);

    }

}
