package nl.tudelft.context.model.graph;

import org.jgrapht.Graphs;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 20-5-2015
 */
public class GraphMap extends ConcurrentHashMap<String, Graph> {

    /**
     * Create a graph with graphs from sources flatten.
     *
     * @param sources Sources to flat
     * @return Flatten graph from sources
     */
    public final Graph flat(final Set<String> sources) {

        Graph graph = new Graph();

        sources.stream()
                .map(this::getGraph)
                .forEach(g -> Graphs.addGraph(graph, g));

        return graph;

    }

    /**
     * Get the graph by source if not exists create an new graph.
     *
     * @param source Source to get graph from
     * @return Graph by source
     */
    private Graph getGraph(final String source) {

        Graph graph = get(source);
        if (graph == null) {
            graph = new Graph();
            put(source, graph);
        }

        return graph;

    }

    /**
     * Add a node to all sources of the node.
     *
     * @param node Node to add
     */
    public final void addVertex(final Node node) {

        node.getSources().stream()
                .map(this::getGraph)
                .forEach(graph -> graph.addVertex(node));

    }

    /**
     * Add an edge to all sources of the edge.
     *
     * @param source Source node
     * @param target Target node
     */
    public final void addEdge(final Node source, final Node target) {

        source.getSources().stream()
                .filter(s -> target.getSources().contains(s))
                .map(this::getGraph)
                .forEach(graph -> graph.addEdge(source, target));

    }
}
