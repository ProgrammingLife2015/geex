package nl.tudelft.context;

import junit.framework.TestCase;
import nl.tudelft.context.graph.Node;
import nl.tudelft.context.graph.NodeFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 24-4-2015
 */
public class NodeTest extends TestCase {

    Node node1, node2;

    /**
     * Set up by parsing a single node1
     */
    public void setUp() {

        NodeFactory nodeFactory = new NodeFactory();

        node1 = nodeFactory.getNode(new Scanner(">0 | Cat,Dog | 5 | 7\nA\n"));
        node2 = nodeFactory.getNode(new Scanner(">1 | Dog | 8 | 10\nATC\n"));

    }

    /**
     * Test node1 id.
     */
    public void testId() {

        assertEquals(0, node1.getId());
        assertEquals(1, node2.getId());

    }

    /**
     * Test node1 sources.
     */
    public void testSources() {

        assertEquals(new HashSet<>(Arrays.asList("Cat", "Dog")), node1.getSources());
        assertEquals(new HashSet<>(Collections.singletonList("Dog")), node2.getSources());

    }

    /**
     * Test node1 ref start position.
     */
    public void testRefStartPosition() {

        assertEquals(5, node1.getRefStartPosition());
        assertEquals(8, node2.getRefStartPosition());

    }

    /**
     * Test node1 ref end position.
     */
    public void testRefEndPosition() {

        assertEquals(7, node1.getRefEndPosition());
        assertEquals(10, node2.getRefEndPosition());

    }

    /**
     * Test node1 content.
     */
    public void testContent() {

        assertEquals("A", node1.getContent());
        assertEquals("ATC", node2.getContent());

    }

}
