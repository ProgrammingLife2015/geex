package nl.tudelft.context.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import nl.tudelft.context.graph.Graph;
import nl.tudelft.context.graph.GraphFactory;

import java.io.File;

/**
 * Service for loading a Graph.
 *
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 25-4-2015
 */
public class LoadGraphService extends Service<Graph> {

    /**
     * File containing node information.
     */
    File nodeFile;
    /**
     * File containing edge information.
     */
    File edgeFile;

    /**
     * Set node and edge file.
     *
     * @param nodeFile File containing the nodes
     * @param edgeFile File containing the edges
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
    protected final Task<Graph> createTask() {

        return new Task<Graph>() {
            @Override
            protected Graph call() throws Exception {
                GraphFactory graphFactory = new GraphFactory();
                return graphFactory.getGraph(nodeFile, edgeFile);
            }
        };

    }

}
