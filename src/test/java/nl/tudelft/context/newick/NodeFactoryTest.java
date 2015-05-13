package nl.tudelft.context.newick;

import net.sourceforge.olduvai.treejuxtaposer.drawer.TreeNode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 13-05-2015
 */
public class NodeFactoryTest {

    public static NodeFactory nodeFactory;

    /**
     * Test if a node is successfully created from a parserNode
     */
    @Test
    public void testNodeCreation() {
        nodeFactory = new NodeFactory();
        TreeNode parserNode = new TreeNode();
        parserNode.setName("");
        parserNode.setWeight(1);
        Node node = new Node("", 1);
        assertEquals(node, nodeFactory.getNode(parserNode));
    }
}
