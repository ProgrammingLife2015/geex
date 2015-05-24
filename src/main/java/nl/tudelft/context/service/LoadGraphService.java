package nl.tudelft.context.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import nl.tudelft.context.model.graph.GraphMap;
import nl.tudelft.context.model.graph.GraphParser;

import java.io.File;

/**
 * Service for loading a Graph.
 *
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 25-4-2015
 */
public class LoadGraphService extends Service<GraphMap> {

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
    protected final Task<GraphMap> createTask() {

        return new Task<GraphMap>() {
            @Override
            protected GraphMap call() throws Exception {
                GraphParser graphParser = new GraphParser();
                return graphParser.getGraphMap(nodeFile, edgeFile);
            }
        };

    }

}
