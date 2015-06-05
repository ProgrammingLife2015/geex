package nl.tudelft.context.model.newick;

import net.sourceforge.olduvai.treejuxtaposer.drawer.TreeNode;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 3-5-2015
 */
public final class NodeParser {
    /**
     * Constructs the Node from a TreeNode.
     *
     * @param node A TreeNode from treejuxtaposer
     * @return A Newick node.
     */
    public AbstractNode getNode(final TreeNode node) {
        if (node.getName().equals("")) {
            return new AncestorNode(node.getName(), node.getWeight());
        } else {
            return new LeaveNode(node.getName(), node.getWeight());
        }
    }
}
