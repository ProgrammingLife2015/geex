package nl.tudelft.context.model.newick;

import net.sourceforge.olduvai.treejuxtaposer.TreeParser;
import net.sourceforge.olduvai.treejuxtaposer.drawer.Tree;
import net.sourceforge.olduvai.treejuxtaposer.drawer.TreeNode;
import nl.tudelft.context.model.Parser;

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
     * The vertical distances between nodes.
     */
    public static final int ROW_HEIGHT = 25;
    /**
     * The weights are scaled with this factor to make relative weights noticeable.
     */
    public static final double WEIGHT_SCALE = 5e4;
    /**
     * Child nodes are at least MIN_WEIGHT pixels moved from their parent.
     */
    public static final int MIN_WEIGHT = 30;

    /**
     * Empty constructor for creating an empty TreeParser.
     */
    public NewickParser() {
        super();
    }

    @Override
    protected Newick parse(final BufferedReader... readerList) {
        BufferedReader reader = readerList[0];
        TreeParser tp =
                new TreeParser(reader);

        Tree nwkTree = tp.tokenize("");
        AbstractNode root = nodeParser.getNode(nwkTree.getRoot());

        Newick newick = new Newick();
        getOffspring(nwkTree.getRoot(), root, newick, 0);
        newick.setRoot(root);

        return newick;
    }

    /**
     * Recursive call that creates nodes and edges for the tree.
     *
     * @param node   the node, read by the newick parser
     * @param parent the parent node to add this node to as a child
     * @param newick   the tree to add the nodes and edges to
     * @param row    the current row (depth) of the node
     * @return the new row (depth) of the next node
     */
    public int getOffspring(final TreeNode node, final AbstractNode parent, final Newick newick, final int row) {
        newick.addVertex(parent);

        int ret = row;

        int addRow = 1;
        for (int i = 0; i < node.numberLeaves; i += 1) {
            TreeNode child = node.getChild(i);
            if (child != null) {
                addRow = 0;
                AbstractNode n = createNode(child, parent, ret);
                ret = getOffspring(child, n, newick, ret);
                parent.addChild(n);
                if (i > 0) {
                    addDummy(parent, n, newick);
                } else {
                    newick.addEdge(parent, n);
                }
            }
        }

        return ret + addRow;
    }

    /**
     * Creation of the node.
     *
     * @param child  the node, read by the newick parser
     * @param parent the parent node to add the child to
     * @param row    the current row (depth) of the node
     * @return the node as a Node
     */
    public AbstractNode createNode(final TreeNode child, final AbstractNode parent, final int row) {
        AbstractNode n = nodeParser.getNode(child);
        n.setParent(parent);
        double x = parent.translateXProperty().doubleValue() + MIN_WEIGHT + WEIGHT_SCALE * n.getWeight();
        n.setTranslateX(x);
        n.setTranslateY(row * ROW_HEIGHT);

        return n;
    }

    /**
     * Adds a dummy node, so that we can use orthogonal lines.
     *
     * @param parent the parent of the node
     * @param n      the node
     * @param newick   the tree to add the dummy to
     */
    public void addDummy(final AbstractNode parent, final AbstractNode n, final Newick newick) {
        AbstractNode dummy = new DummyNode();
        dummy.setTranslateX(parent.translateXProperty().doubleValue());
        dummy.setTranslateY(n.translateYProperty().doubleValue());
        newick.addVertex(dummy);
        newick.addEdge(parent, dummy);
        newick.addEdge(dummy, n);
    }
}
