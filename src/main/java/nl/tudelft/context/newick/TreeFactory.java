package nl.tudelft.context.newick;

import net.sourceforge.olduvai.treejuxtaposer.TreeParser;
import net.sourceforge.olduvai.treejuxtaposer.drawer.TreeNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 3-5-2015
 */
public final class TreeFactory {
    /**
     * The nodefactory for the Tree.
     */
    NodeFactory nodeFactory = new NodeFactory();

    /**
     * Gets the Newick tree from the File.
     *
     * @param nwkFile The file with the Newick tree
     * @return A Newick Tree
     * @throws FileNotFoundException When nwkFile is not found
     * @throws UnsupportedEncodingException When nwkFile has an unsupported encoding
     */
    public Tree getTree(final File nwkFile) throws FileNotFoundException, UnsupportedEncodingException {
        Tree tree = new Tree();

        parseTree(nwkFile, tree);

        return tree;
    }

    /**
     * Parse the nwkFile file.
     *
     * @param nwkFile The newick tree source file.
     * @param tree The object to construct a tree on.
     * @throws FileNotFoundException When the nwkFile is not found
     * @throws UnsupportedEncodingException When the nwkFile has an unsupported encoding
     */
    public void parseTree(final File nwkFile, final Tree tree)
            throws FileNotFoundException, UnsupportedEncodingException {
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(nwkFile), "UTF-8"));
        TreeParser tp = new TreeParser(fileReader);
        net.sourceforge.olduvai.treejuxtaposer.drawer.Tree nwkTree = tp.tokenize(1, "", null);
        Node root = nodeFactory.getNode(nwkTree.getRoot());
        root.setTranslateX(0);
        root.setTranslateY(0);
        getOffspring(nwkTree.getRoot(), root, tree, 0);
    }

    /**
     * Get the offspring of a node in a tree.
     *
     * @param node The node of the tree
     * @param parent The parent of the node
     * @param tree The tree
     * @param row The current row
     * @return The offspring of the Node
     */
    public int getOffspring(final TreeNode node, final Node parent, final Tree tree, final int row) {
        tree.addVertex(parent);

        boolean hasChildren = false;
        for (int i = 0; i < node.numberLeaves; i += 1) {
            TreeNode child = node.getChild(i);
            if (child != null) {
                hasChildren = true;
                Node n = nodeFactory.getNode(child);
                n.setTranslateX(parent.translateXProperty().doubleValue() + 100 + 10000 * n.getWeight());
                n.setTranslateY(row * 80);
                row = getOffspring(child, n, tree, row);
                parent.addChild(n);
                tree.addEdge(parent, n);
            }
        }

        if (hasChildren) {

            return row;
        } else {
            return row + 1;
        }
    }
}
