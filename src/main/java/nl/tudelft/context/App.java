package nl.tudelft.context;

import nl.tudelft.context.graph.GraphFactory;
import org.jgrapht.UndirectedGraph;

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
        UndirectedGraph graph = graphFactory.getGraph("/10_strains_graph/simple_graph.node.graph", "/10_strains_graph/simple_graph.edge.graph");

    }

}
