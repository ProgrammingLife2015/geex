package nl.tudelft.context.model.graph;

import nl.tudelft.context.model.graph.parser.NodeParser;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author René Vennik
 * @version 1.0
 * @since 24-4-2015
 */
public class NodeTest {

    protected static Node node1, node2;

    /**
     * Set up by parsing a single node1
     */
    @BeforeClass
    public static void beforeClass() {

        NodeParser nodeParser = new NodeParser();

        node1 = nodeParser.getNode(new Scanner(">0 | Cat,Dog | 5 | 7\nA\n"));
        node2 = nodeParser.getNode(new Scanner(">1 | Dog | 8 | 10\nATC\n"));

    }

    /**
     * Test node1 id.
     */
    @Test
    public void testId() {

        assertEquals(0, node1.getId());
        assertEquals(1, node2.getId());

    }

    /**
     * Test node1 sources.
     */
    @Test
    public void testSources() {

        assertEquals(new HashSet<>(Arrays.asList("Cat", "Dog")), node1.getSources());
        assertEquals(new HashSet<>(Collections.singletonList("Dog")), node2.getSources());

    }

    /**
     * Test node1 ref start position.
     */
    @Test
    public void testRefStartPosition() {

        assertEquals(5, node1.getRefStartPosition());
        assertEquals(8, node2.getRefStartPosition());

    }

    /**
     * Test node1 ref end position.
     */
    @Test
    public void testRefEndPosition() {

        assertEquals(7, node1.getRefEndPosition());
        assertEquals(10, node2.getRefEndPosition());

    }

    /**
     * Test node1 content.
     */
    @Test
    public void testContent() {

        assertEquals("A", node1.getContent());
        assertEquals("ATC", node2.getContent());

    }

    /**
     * Test node count.
     */
    @Test
    public void testCount() {
        assertEquals(1, node1.baseCounter.getCount('A'));
        assertEquals(1, node2.baseCounter.getCount('T'));
    }

    /**
     * Test the equals method for object mismatch.
     */
    @Test
    public void testEqualsNonNode() {
        assertFalse(node1.equals("not a node:"));
    }

    /**
     * Test node percentage.
     */
    @Test
    public void testPercentage() {
        assertEquals(1f, node1.baseCounter.getRatio('A'), 0.0001);
        assertEquals(0.3333f, node2.baseCounter.getRatio('T'), 0.01);
    }

    /**
     * Test getBaseCounter.
     */
    @Test
    public void testGetCounter() {
        BaseCounter baseCounter1 = new BaseCounter("A");
        BaseCounter baseCounter2 = new BaseCounter("ATC");
        assertEquals(baseCounter1.getRatio('A'), node1.getBaseCounter().getRatio('A'), 0.0001);
        assertEquals(baseCounter2.getRatio('T'), node2.getBaseCounter().getRatio('T'), 0.01);
    }

}
