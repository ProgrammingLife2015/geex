package nl.tudelft.context.model.newick;

import net.sourceforge.olduvai.treejuxtaposer.TreeParser;
import net.sourceforge.olduvai.treejuxtaposer.drawer.Tree;
import net.sourceforge.olduvai.treejuxtaposer.drawer.TreeNode;
import nl.tudelft.context.model.Parser;
import nl.tudelft.context.model.newick.node.AbstractNode;
import nl.tudelft.context.model.newick.node.DummyNode;

import java.io.BufferedReader;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 3-5-2015
 */
public final class NewickParser extends Parser<Newick> {
    /**
     * The node factory.
     */
    private NodeParser nodeParser = new NodeParser();

    /**
     * Empty constructor for creating an empty TreeParser.
     */
    public NewickParser() {
        super();
    }

    @Override
    protected Newick parse(final BufferedReader... readerList) {
        BufferedReader reader = readerList[0];
        TreeParser tp = new TreeParser(reader);

        Tree nwkTree = tp.tokenize("");

        Newick newick = new Newick();
        AbstractNode root = nodeParser.getNode(nwkTree.getRoot());
        newick.setRoot(root);
        newick.addVertex(root);
        getOffspring(nwkTree.getRoot(), root, newick);

        return newick;
    }

    /**
     * Recursive call that creates nodes and edges for the tree.
     *
     * @param node   the node, read by the newick parser
     * @param parent the parent node to add this node to as a child
     * @param newick the tree to add the nodes and edges to
     * @return the new row (depth) of the next node
     */
    public void getOffspring(final TreeNode node, final AbstractNode parent, final Newick newick) {
        for (int i = 0; i < node.numberLeaves; i += 1) {
            TreeNode child = node.getChild(i);
            if (child != null) {
                AbstractNode n = createNode(child, parent, newick);
                getOffspring(child, n, newick);
            }
        }
    }

    /**
     * Creation of the node.
     *
     * @param child  the node, read by the newick parser
     * @param parent the parent node to add the child to
     * @param newick the tree to add the nodes and edges to
     * @return the node as a Node
     */
    public AbstractNode createNode(final TreeNode child, final AbstractNode parent, final Newick newick) {
        AbstractNode n = nodeParser.getNode(child);
        DummyNode dummy = new DummyNode();
        newick.addVertex(dummy);
        newick.addVertex(n);
        connectNodes(parent, dummy);
        connectNodes(dummy, n);

        return n;
    }

    /**
     * Adds a child to a parent node and sets the parent for the child node.
     *
     * @param parent The parent node
     * @param child  The child node
     */
    public void connectNodes(final AbstractNode parent, final AbstractNode child) {
        parent.addChild(child);
        child.setParent(parent);
    }
}
