package nl.tudelft.context.model.newick;

import nl.tudelft.context.model.newick.node.AbstractNode;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 3-5-2015
 */
public class Newick extends DefaultDirectedGraph<AbstractNode, DefaultEdge> {

    /**
     * Serial Version UID for serializing purposes.
     */
    private static final long serialVersionUID = -9035500723462666491L;

    /**
     * The root of the tree.
     */
    transient AbstractNode root;

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
    public void setRoot(final AbstractNode n) {
        root = n;
    }

    /**
     * Gets the root of the tree.
     *
     * @return the root
     */
    public AbstractNode getRoot() {
        return root;
    }

    /**
     * Gets a copy of the newick, containing only the previously selected nodes.
     *
     * @return           A copy of the tree, containing the selected nodes
     * @throws Exception When the tree has no selected nodes at all
     */
    public Newick getSelectedSubGraph() throws Exception {
        Newick subNewick = new Newick();

        subNewick.setRoot(root.getSelectedNodes()
                .orElseThrow(() -> new Exception("Graph doesn't have selected nodes.")));
        subNewick.addVertices(subNewick.getRoot());

        return subNewick;
    }

    /**
     * Adds the node and its children to the VertexSet of the tree.
     *
     * @param node the node to add.
     */
    public void addVertices(final AbstractNode node) {
        addVertex(node);
        node.getChildren().stream()
                .forEach(this::addVertices);
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
    public String toString(final AbstractNode node, final int level) {
        StringBuilder res = new StringBuilder();
        res.append(new String(new char[level]).replace("\0", "\t"));
        res.append(node.toString()).append("\n");

        for (AbstractNode child : node.getChildren()) {
            res.append(toString(child, level + 1));
        }

        return res.toString();
    }
}
