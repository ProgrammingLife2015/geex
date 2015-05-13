package nl.tudelft.context.newick;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.Optional;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 3-5-2015
 */
public final class Tree extends DefaultDirectedGraph<Node, DefaultEdge> {

    private Node root;

    /**
     * Create a new Tree, with default edges.
     */
    public Tree() {
        super(DefaultEdge.class);
    }

    /**
     * Sets a node as the root of the tree.
     *
     * @param n the root
     */
    public void setRoot(Node n) {
        root = n;
    }

    /**
     * Gets the root of the tree.
     *
     * @return the root
     */
    public Node getRoot() {
        return root;
    }

    @Override
    public String toString() {
        Node root = getRoot();
        if (root != null) {
            return toString(root, 0);
        } else {
            return "";
        }
    }

    /**
     * Recursive toString helper for the tree.
     * @param node  the current node
     * @param level the level of the tree
     * @return      a string representation of the node
     */
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
