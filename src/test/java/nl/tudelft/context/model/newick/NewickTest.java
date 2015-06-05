package nl.tudelft.context.model.newick;

import nl.tudelft.context.model.newick.node.AbstractNode;
import nl.tudelft.context.model.newick.node.LeaveNode;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 06-05-2015
 */
public class NewickTest {

    private static Newick newick;

    @Before
    public void setup() {
        newick = new Newick();
    }

    /**
     * Test if it can get the first node and returns
     * null if it doesn't have any.
     */
    @Test
    public void testRoot() {
        assertNull(newick.getRoot());
        AbstractNode root1 = new LeaveNode("A", 1.39);
        newick.setRoot(root1);
        assertEquals(root1, newick.getRoot());
        AbstractNode root2 = new LeaveNode("B", 1.1);
        newick.setRoot(root2);
        assertEquals(root2, newick.getRoot());
    }

    @Test
    public void testToString() {
        assertEquals("", newick.toString());
        newick.setRoot(new LeaveNode("Some name", 1.23));
        assertEquals("Node<Some name,1.23>\n", newick.toString());
        newick.getRoot().addChild(new LeaveNode("Some other name", 2.34));
        assertEquals("Node<Some name,1.23>\n" +
                "\tNode<Some other name,2.34>\n",
                newick.toString());
    }
}
