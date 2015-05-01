package nl.tudelft.context.graph;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for Graph.
 *
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.1
 * @since 23-4-2015
 */
public class GraphTest {

    protected static Graph graph;

    protected static final Node node1 = new Node(0, new HashSet<>(Arrays.asList("Cat", "Dog")), 5, 7, "A");
    protected static final Node node2 = new Node(1, new HashSet<>(Collections.singletonList("Dog")), 7, 10, "C");
    protected static final Node node3 = new Node(2, new HashSet<>(Collections.singletonList("Cat")), 7, 10, "G");
    protected static final Node node4 = new Node(3, new HashSet<>(Arrays.asList("Cat", "Dog")), 10, 13, "T");

    /**
     * Set up the graphFromFactory.
     *
     * @throws FileNotFoundException
     */
    @BeforeClass
    public static void beforeClass() throws FileNotFoundException {

        File nodeFile = new File(GraphTest.class.getResource("/graph/node.graph").getPath());
        File edgeFile = new File(GraphTest.class.getResource("/graph/edge.graph").getPath());

        GraphFactory graphFactory = new GraphFactory();
        graph = graphFactory.getGraph(nodeFile, edgeFile);

    }

    /**
     * Test if node1 1 is parsed correct.
     */
    @Test
    public void testNode1() {

        assertTrue(graph.containsVertex(node1));

    }

    /**
     * Test if node1 2 is parsed correct.
     */
    @Test
    public void testNode2() {

        assertTrue(graph.containsVertex(node2));

    }

    /**
     * Test if node1 3 is parsed correct.
     */
    @Test
    public void testNode3() {

        assertTrue(graph.containsVertex(node3));

    }

    /**
     * Test if node1 4 is parsed correct.
     */
    @Test
    public void testNode4() {

        assertTrue(graph.containsVertex(node4));

    }

    /**
     * Test if edges are parsed correct.
     */
    @Test
    public void testEdges() {

        assertTrue(graph.containsEdge(node1, node2));
        assertTrue(graph.containsEdge(node1, node3));
        assertTrue(graph.containsEdge(node2, node4));
        assertTrue(graph.containsEdge(node3, node4));

    }

}
