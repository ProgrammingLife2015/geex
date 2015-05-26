package nl.tudelft.context.model.newick;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 06-05-2015
 */
public class TreeTest {

    private static Tree tree;

    @Before
    public void setup() {
        tree = new Tree();
    }

    /**
     * Test if it can get the first node and returns
     * null if it doesn't have any.
     */
    @Test
    public void testRoot() {
        assertNull(tree.getRoot());
        Node root1 = new Node("A", 1.39);
        tree.setRoot(root1);
        assertEquals(root1, tree.getRoot());
        Node root2 = new Node("B", 1.1);
        tree.setRoot(root2);
        assertEquals(root2, tree.getRoot());
    }

    @Test
    public void testToString() {
        assertEquals("", tree.toString());
        tree.setRoot(new Node("Some name", 1.23));
        assertEquals("Node<Some name,1.23>\n", tree.toString());
        tree.getRoot().addChild(new Node("Some other name", 2.34));
        assertEquals("Node<Some name,1.23>\n" +
                "\tNode<Some other name,2.34>\n",
                tree.toString());
    }
}
