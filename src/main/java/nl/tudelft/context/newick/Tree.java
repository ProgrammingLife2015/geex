package nl.tudelft.context.newick;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.Optional;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 3-5-2015
 */
public class Tree extends DefaultDirectedGraph<Node, DefaultEdge> {
    public Tree() {
        super(DefaultEdge.class);
    }

    /**
     * Get the first node, with no incoming edges.
     *
     * @return first node
     */
    public Node getFirstNode() {

        Optional<Node> node = vertexSet().stream().filter(x -> this.inDegreeOf(x) == 0).findFirst();
        return node.orElse(null);

    }

    @Override
    public String toString() {
        Node n = getFirstNode();
        if (n != null) {
            return n.toString();
        } else {
            return "";
        }
    }
}
