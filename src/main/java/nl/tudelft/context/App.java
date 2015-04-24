package nl.tudelft.context;

import nl.tudelft.context.graph.Graph;
import nl.tudelft.context.graph.GraphFactory;

import java.io.FileNotFoundException;

/**
 * App
 */
public class App {


    /**
     * @param args ignored.
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {

        GraphFactory graphFactory = new GraphFactory();
        Graph graph = graphFactory.getGraph("/graph/10_strains_graph/simple_graph.node.graph", "/graph/10_strains_graph/simple_graph.edge.graph");

    }

}
