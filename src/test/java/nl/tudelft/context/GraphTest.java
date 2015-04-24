package nl.tudelft.context;

import junit.framework.TestCase;
import nl.tudelft.context.graph.Graph;
import nl.tudelft.context.graph.GraphFactory;
import nl.tudelft.context.graph.Node;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * Unit test for Graph.
 *
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.1
 * @since 23-4-2015
 */
public class GraphTest extends TestCase {

    protected Graph graph;

    protected Node node1 = new Node(0, new HashSet<>(Arrays.asList("Cat", "Dog")), 5, 7, "A");
    protected Node node2 = new Node(1, new HashSet<>(Collections.singletonList("Dog")), 8, 10, "C");
    protected Node node3 = new Node(2, new HashSet<>(Collections.singletonList("Cat")), 8, 10, "G");
    protected Node node4 = new Node(3, new HashSet<>(Arrays.asList("Cat", "Dog")), 10, 13, "T");

    /**
     * Set up the graph.
     *
     * @throws FileNotFoundException
     */
    public void setUp() throws FileNotFoundException {

        GraphFactory graphFactory = new GraphFactory();
        graph = graphFactory.getGraph("/graph/node.graph", "/graph/edge.graph");

    }

    /**
     * Test if node 1 is parsed correct.
     */
    public void testNode1() {

        Node node = graph.getVertexById(0);
        assertEquals(node1.getSources(), node.getSources());
        assertEquals(node1.getRefStartPosition(), node.getRefStartPosition());
        assertEquals(node1.getRefEndPosition(), node.getRefEndPosition());
        assertEquals(node1.getContent(), node.getContent());
        assertTrue(graph.containsVertex(node1));

    }

    /**
     * Test if node 2 is parsed correct.
     */
    public void testNode2() {

        Node node = graph.getVertexById(1);
        assertEquals(node2.getSources(), node.getSources());
        assertEquals(node2.getRefStartPosition(), node.getRefStartPosition());
        assertEquals(node2.getRefEndPosition(), node.getRefEndPosition());
        assertEquals(node2.getContent(), node.getContent());
        assertTrue(graph.containsVertex(node2));

    }

    /**
     * Test if node 3 is parsed correct.
     */
    public void testNode3() {

        Node node = graph.getVertexById(2);
        assertEquals(node3.getSources(), node.getSources());
        assertEquals(node3.getRefStartPosition(), node.getRefStartPosition());
        assertEquals(node3.getRefEndPosition(), node.getRefEndPosition());
        assertEquals(node3.getContent(), node.getContent());
        assertTrue(graph.containsVertex(node3));

    }

    /**
     * Test if node 4 is parsed correct.
     */
    public void testNode4() {

        Node node = graph.getVertexById(3);
        assertEquals(node4.getSources(), node.getSources());
        assertEquals(node4.getRefStartPosition(), node.getRefStartPosition());
        assertEquals(node4.getRefEndPosition(), node.getRefEndPosition());
        assertEquals(node4.getContent(), node.getContent());
        assertTrue(graph.containsVertex(node4));

    }

    /**
     * Test if edges are parsed correct.
     */
    public void testEdges() {

        assertTrue(graph.containsEdge(node1, node2));
        assertTrue(graph.containsEdge(node1, node3));
        assertTrue(graph.containsEdge(node2, node4));
        assertTrue(graph.containsEdge(node3, node4));

    }

}
