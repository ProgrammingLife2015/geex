package nl.tudelft.context.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import nl.tudelft.context.model.graph.Graph;
import nl.tudelft.context.model.graph.Node;
import nl.tudelft.context.mutations.Mutation;
import nl.tudelft.context.mutations.MutationParser;

import java.util.List;

/**
 * @author Jim
 * @version 1.0
 * @since 5/23/2015
 */
public class LoadMutationService extends Service<List<Mutation>> {

    /**
     * The graph where the mutations are in.
     */
    Graph graph;

    public LoadMutationService(final Graph graph) {

        System.out.println("Hoi!");
        this.graph = graph;

    }

    /**
     * Function used to set the graph.
     *
     * @param graph The graph that will be set.
     */
    public void setGraph(final Graph graph) {

        this.graph = graph;

    }

    /**
     * Create a task that will load the mutations.
     *
     * @return the task to execute.
     */
    @Override
    protected final Task<List<Mutation>> createTask() {
        return new Task<List<Mutation>>() {
            @Override
            protected List<Mutation> call() throws Exception {
                MutationParser mp = new MutationParser(graph);
                return mp.checkMutations();
            }
        };
    }
}
