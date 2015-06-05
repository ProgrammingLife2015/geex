package nl.tudelft.context.model.newick;

import net.sourceforge.olduvai.treejuxtaposer.drawer.TreeNode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 13-05-2015
 */
public class NodeFactoryTest {

    public static NodeParser nodeParser;

    /**
     * Test if a node is successfully created from a parserNode
     */
    @Test
    public void testNodeCreation() {
        nodeParser = new NodeParser();
        TreeNode parserNode = new TreeNode();
        parserNode.setName("");
        parserNode.setWeight(1);
        AbstractNode node = new LeaveNode("", 1);
        assertEquals(node, nodeParser.getNode(parserNode));
    }
}
