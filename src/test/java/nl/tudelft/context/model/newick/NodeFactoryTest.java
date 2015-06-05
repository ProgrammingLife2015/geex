package nl.tudelft.context.model.newick;

import net.sourceforge.olduvai.treejuxtaposer.drawer.TreeNode;
import nl.tudelft.context.model.newick.node.AbstractNode;
import nl.tudelft.context.model.newick.node.AncestorNode;
import nl.tudelft.context.model.newick.node.StrandNode;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 13-05-2015
 */
public class NodeFactoryTest {

    public static NodeParser nodeParser;
    public static TreeNode parserNode;

    @Before
    public void beforeClass() {
        nodeParser = new NodeParser();
        parserNode = new TreeNode();
    }

    /**
     * Test if a leave node is successfully created from a parserNode
     */
    @Test
    public void testLeaveNodeCreation() {
        parserNode.setName("abc");
        parserNode.setWeight(1);
        AbstractNode abstractNode = nodeParser.getNode(parserNode);
        assertEquals("abc", abstractNode.getName());
        assertEquals(1, abstractNode.getWeight(), 1e-12);
        assertThat(abstractNode, instanceOf(StrandNode.class));
    }

    /**
     * Test if an ancestor node is successfully created from a parserNode
     */
    @Test
    public void testAncestorNodeCreation() {
        parserNode.setName("");
        parserNode.setWeight(2);
        AbstractNode abstractNode = nodeParser.getNode(parserNode);
        assertEquals("", abstractNode.getName());
        assertEquals(2, abstractNode.getWeight(), 1e-12);
        assertThat(abstractNode, instanceOf(AncestorNode.class));
    }
}
