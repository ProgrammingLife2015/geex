package nl.tudelft.context;

import nl.tudelft.context.graph.Graph;
import nl.tudelft.context.graph.GraphFactory;

import java.io.FileNotFoundException;

/**
 * App
 *
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 23-4-2015
 */
public class App {

    /**
     * @param args ignored
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {

        GraphFactory graphFactory = new GraphFactory();
        Graph graph = graphFactory.getGraph("/graph/10_strains_graph/simple_graph.node.graph", "/graph/10_strains_graph/simple_graph.edge.graph");

    }

}
