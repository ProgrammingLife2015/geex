package nl.tudelft.context.model.newick;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 3-5-2015
 */
public class Newick extends DefaultDirectedGraph<Node, DefaultEdge> {

    /**
     * Serial Version UID for serializing purposes.
     */
    private static final long serialVersionUID = -9035500723462666491L;

    /**
     * The root of the tree.
     */
    transient Node root;

    /**
     * Create a new Tree, with default edges.
     */
    public Newick() {
        super(DefaultEdge.class);
    }

    /**
     * Sets a node as the root of the tree.
     *
     * @param n the root
     */
    public void setRoot(final Node n) {
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

    /**
     * Create a sub newick tree of selected nodes.
     *
     * @return New newick tree
     */
    public Newick createSubNewick() {
        return this;
    }

    @Override
    public String toString() {
        if (getRoot() == null) {
            return "";
        }
        return toString(getRoot(), 0);
    }

    /**
     * Recursive toString helper for the tree.
     *
     * @param node  the current node
     * @param level the level of the tree
     * @return a string representation of the node
     */
    public String toString(final Node node, final int level) {
        StringBuilder res = new StringBuilder();
        res.append(new String(new char[level]).replace("\0", "\t"));
        res.append(node.toString()).append("\n");

        for (Node child : node.getChildren()) {
            res.append(toString(child, level + 1));
        }

        return res.toString();
    }
}
