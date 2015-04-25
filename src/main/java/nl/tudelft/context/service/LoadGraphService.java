package nl.tudelft.context.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import nl.tudelft.context.graph.Graph;
import nl.tudelft.context.graph.GraphFactory;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 25-4-2015
 */
public class LoadGraphService extends Service<Graph> {

    /**
     * Create a task that loads the graph.
     *
     * @return the Task to execute
     */
    @Override
    protected Task<Graph> createTask() {

        return new Task<Graph>() {
            @Override
            protected Graph call() throws Exception {
                GraphFactory graphFactory = new GraphFactory();
                return graphFactory.getGraph("/graph/10_strains_graph/simple_graph.node.graph", "/graph/10_strains_graph/simple_graph.edge.graph");
            }
        };

    }

}
