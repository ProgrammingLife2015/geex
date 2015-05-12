package nl.tudelft.context.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import nl.tudelft.context.graph.Graph;
import nl.tudelft.context.graph.GraphFactory;

import java.io.File;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 25-4-2015
 */
public final class LoadGraphService extends Service<Graph> {

    protected File nodeFile;
    protected File edgeFile;

    /**
     * Set node and edge file
     */
    public LoadGraphService(final File nodeFile, final File edgeFile) {

        this.nodeFile = nodeFile;
        this.edgeFile = edgeFile;

    }

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
                return graphFactory.getGraph(nodeFile, edgeFile);
            }
        };

    }

}
