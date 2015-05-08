package nl.tudelft.context.newick;

import net.sourceforge.olduvai.treejuxtaposer.TreeParser;
import net.sourceforge.olduvai.treejuxtaposer.drawer.TreeNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 3-5-2015
 */
public class TreeFactory {
    public NodeFactory nodeFactory = new NodeFactory();

    public Tree getTree(File nwkFile) throws FileNotFoundException {
        Tree tree = new Tree();

        parseTree(nwkFile, tree);

        return tree;
    }

    public void parseTree(File nwkFile, Tree tree) throws FileNotFoundException {
        TreeParser tp = new TreeParser(new BufferedReader(new FileReader(nwkFile)));
        net.sourceforge.olduvai.treejuxtaposer.drawer.Tree nwkTree = tp.tokenize(1, "", null);
        Node root = nodeFactory.getNode(nwkTree.getRoot());
        getOffspring(nwkTree.getRoot(), root, tree);
    }

    public void getOffspring(TreeNode node, Node parent, Tree tree) {
        tree.addVertex(parent);
        for (int i = 0; i < node.numberLeaves; i += 1) {
            if (node.getChild(i) != null) {
                Node n = nodeFactory.getNode(node.getChild(i));
                getOffspring(node.getChild(i), n, tree);
                parent.addChild(n);
            }
        }
    }
}
