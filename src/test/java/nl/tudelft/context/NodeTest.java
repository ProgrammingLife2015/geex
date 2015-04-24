package nl.tudelft.context;

import junit.framework.TestCase;
import nl.tudelft.context.graph.Node;
import nl.tudelft.context.graph.NodeFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 24-4-2015
 */
public class NodeTest extends TestCase {

    Node node;

    /**
     * Set up by parsing a single node
     */
    public void setUp() {

        Scanner sc = new Scanner(">0 | Cat,Dog | 5 | 7\nA\n");

        NodeFactory nodeFactory = new NodeFactory();
        node = nodeFactory.getNode(sc);

    }

    /**
     * Test node id.
     */
    public void testId() {

        assertEquals(0, node.getId());

    }

    /**
     * Test node sources.
     */
    public void testSources() {

        assertEquals(new HashSet<>(Arrays.asList("Cat", "Dog")), node.getSources());

    }

    /**
     * Test node ref start position.
     */
    public void testRefStartPosition() {

        assertEquals(5, node.getRefStartPosition());

    }

    /**
     * Test node ref end position.
     */
    public void testRefEndPosition() {

        assertEquals(7, node.getRefEndPosition());

    }

    /**
     * Test node content.
     */
    public void testContent() {

        assertEquals("A", node.getContent());

    }

}
