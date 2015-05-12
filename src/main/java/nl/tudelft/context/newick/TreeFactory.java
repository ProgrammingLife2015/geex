package nl.tudelft.context.newick;

import net.sourceforge.olduvai.treejuxtaposer.TreeParser;
import net.sourceforge.olduvai.treejuxtaposer.drawer.TreeNode;

import java.io.*;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 3-5-2015
 */
public class TreeFactory {
    public NodeFactory nodeFactory = new NodeFactory();

    public Tree getTree(File nwkFile) throws FileNotFoundException, UnsupportedEncodingException {
        Tree tree = new Tree();

        parseTree(nwkFile, tree);

        return tree;
    }

    public void parseTree(File nwkFile, Tree tree) throws FileNotFoundException, UnsupportedEncodingException {
        TreeParser tp = new TreeParser(new BufferedReader(new InputStreamReader(new FileInputStream(nwkFile), "UTF-8")));
        net.sourceforge.olduvai.treejuxtaposer.drawer.Tree nwkTree = tp.tokenize(1, "", null);
        Node root = nodeFactory.getNode(nwkTree.getRoot());
        root.setTranslateX(0);
        root.setTranslateY(0);
        getOffspring(nwkTree.getRoot(), root, tree, 0);
    }

    public int getOffspring(TreeNode node, Node parent, Tree tree, int row) {
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
