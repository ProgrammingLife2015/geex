package nl.tudelft.context.newick;

import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 06-05-2015
 */
public class NodeTest {

    /**
     * Test if a child is successfully set
     */
    @Test
    public void testChild() {
        Node n = new Node("a", 0);
        Node m = new Node("b", 1);
        n.addChild(m);
        assertEquals(n.getChildren(), Collections.singletonList(m));
    }

    /**
     * Node shouldn't equal null
     */
    @Test
    public void testNotEqualsNull() {
        assertFalse(new Node("", 0).equals(null));
    }

    /**
     * Node shouldn't equal other objects
     */
    @Test
    public void testNotEqualsOtherObject() {
        assertFalse(new Node("", 0).equals(""));
    }

    @Test
    public void testIsUnknown() {
        Node n1 = new Node("", 1);
        Node n2 = new Node("abc", 9);

        assertTrue(n1.isUnknown());
        assertFalse(n2.isUnknown());
    }

    /**
     * Node shouldn't equals nodes with other names or weights
     */
    @Test
    public void testNotEqualsOtherNode() {
        assertFalse(new Node("", 0).equals(new Node("", 1)));
        assertFalse(new Node("", 0).equals(new Node("a", 0)));
    }

    /**
     * Node should equal a similar node
     */
    @Test
    public void testEqualsNode() {
        assertTrue(new Node("", 0).equals(new Node("", 0)));
    }
}
