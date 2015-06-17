package nl.tudelft.context.model.graph;

import nl.tudelft.context.model.annotation.AnnotationMap;
import org.jgrapht.graph.AbstractBaseGraph;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author René Vennik
 * @version 1.0
 * @since 20-5-2015
 */
public class GraphMap extends ConcurrentHashMap<String, Graph> {

    /**
     * Load the codingSequences for this graph.
     *
     * @param codingSequenceMap All the codingSequences.
     */
    public void setAnnotations(final AnnotationMap codingSequenceMap) {
        values().parallelStream()
                .forEach(graph -> graph.setAnnotations(codingSequenceMap));
    }

    /**
     * Load the resistance for this graph.
     *
     * @param resistanceMap All the resistance mutations.
     */
    public void setResistance(final AnnotationMap resistanceMap) {
        values().parallelStream()
                .forEach(graph -> graph.setResistance(resistanceMap));
    }

    /**
     * Create a graph with graphs from sources flatten.
     *
     * @param sources Sources to flat
     * @return Flatten graph from sources
     */
    public final Graph flat(final Set<String> sources) {

        final double strains = sources.size();

        Graph graph = new Graph();
        List<Graph> graphs = getGraphList(sources);

        graphs.stream()
                .map(AbstractBaseGraph::vertexSet)
                .flatMap(Collection::stream)
                .forEach(node -> {
                    node.reset();
                    graph.addVertex(node);
                });

        graphs.parallelStream()
                .map(AbstractBaseGraph::edgeSet)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(
                        edge -> Arrays.asList(
                                graph.getEdgeSource(edge),
                                graph.getEdgeTarget(edge)
                        ),
                        Collectors.counting()
                ))
                .forEach((nodes, count) ->
                        graph.setEdgeWeight(graph.addEdge(nodes.get(0), nodes.get(1)), count / strains));

        return graph;

    }

    /**
     * Get the graph list by sources.
     *
     * @param sources Sources to get the graphs from
     * @return Graphs by sources
     */
    private List<Graph> getGraphList(final Set<String> sources) {

        return sources.stream()
                .map(this::getGraph)
                .collect(Collectors.toList());

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
                .filter(s -> node.getSources().contains(s))
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
                .filter(s -> source.getSources().contains(s) && target.getSources().contains(s))
                .map(this::getGraph)
                .forEach(graph -> graph.addEdge(source, target));

    }

    /**
     * Filter all skipping edges in single strains.
     */
    public void filter() {

        values().parallelStream().forEach(graph -> {

            List<DefaultNode> current = new LinkedList<>(graph.getFirstNodes());
            while (!current.isEmpty()) {

                DefaultNode start = current.get(0);
                if (graph.outDegreeOf(start) > 1) {

                    graph.getTargets(start).stream()
                            .filter(vertex -> graph.inDegreeOf(vertex) > 1)
                            .forEach(end -> graph.removeEdge(start, end));

                }

                current = graph.getTargets(start);

            }

        });

    }

}
