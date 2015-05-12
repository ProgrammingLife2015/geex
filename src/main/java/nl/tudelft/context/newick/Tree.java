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
        Node root = getFirstNode();
        if (root != null) {
            return toString(root, 0);
        } else {
            return "";
        }
    }
    public String toString(Node node, int level) {
        StringBuilder res = new StringBuilder();
        res.append(new String(new char[level]).replace("\0", "\t"));
        res.append(node.toString() + "\n");

        for (Node child : node.getChildren()) {
            res.append(toString(child, level + 1));
        }

        return res.toString();
    }
}
