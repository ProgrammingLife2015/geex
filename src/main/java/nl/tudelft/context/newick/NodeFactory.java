package nl.tudelft.context.newick;

import net.sourceforge.olduvai.treejuxtaposer.drawer.TreeNode;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 3-5-2015
 */
public class NodeFactory {
    public Node getNode(TreeNode node) {
        return new Node(node.getName(), node.getWeight());
    }
}
