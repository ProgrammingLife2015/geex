package nl.tudelft.context;

import junit.framework.TestCase;
import nl.tudelft.context.graph.GraphFactory;
import org.jgrapht.UndirectedGraph;

import java.io.FileNotFoundException;

/**
 * Unit test for Graph.
 *
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 23-4-2015
 */
public class GraphTest extends TestCase {

    /**
     * Test if graph factory creates the right graph.
     *
     * @throws FileNotFoundException
     */
    public void testSimpleGraph() throws FileNotFoundException {

        GraphFactory graphFactory = new GraphFactory();
        UndirectedGraph graph = graphFactory.getGraph("/graph/node.graph", "/graph/edge.graph");

        assertEquals("([A, C, G, T], [{A,C}, {A,G}, {C,T}, {G,T}])", graph.toString());

    }

}
