package nl.tudelft.context.model.graph;

import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Filter specific items from a StackGraph.
 *
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 23-6-2015
 */
public abstract class FilterGraph extends StackGraph {
    /**
     * Unknown nodes (75% or more).
     */
    Set<DefaultNode> filtered;

    /**
     * Clean graph.
     */
    StackGraph graph;

    /**
     * Create a graph with filtered filtered on an other graph.
     *
     * @param graph Graph to remove unkowns form.
     */
    public FilterGraph(final StackGraph graph) {

        this.graph = graph;
        setGraph(graph);

        markFiltered();
        removeAll();

    }

    /**
     * The filter used to remove objects.
     * @return filter (predicate)
     */
    protected abstract Predicate<DefaultNode> filter();

    /**
     * Mark all the filtered nodes.
     */
    private void markFiltered() {

        filtered = vertexSet().stream()
                .filter(filter())
                .filter(node -> outDegreeOf(node) <= 1 && inDegreeOf(node) <= 1)
                .collect(Collectors.toSet());

    }

    /**
     * Remove all the filtered nodes.
     */
    private void removeAll() {

        filtered.stream()
                .forEach(node -> {
                    if (outDegreeOf(node) != 0 && inDegreeOf(node) != 0) {
                        DefaultNode start = getSources(node).get(0);
                        DefaultNode end = getTargets(node).get(0);
                        DefaultWeightedEdge edge = getEdge(start, end);
                        double weight = getEdgeWeight(getEdge(start, node));
                        if (edge != null) {
                            setEdgeWeight(edge, weight);
                        } else {
                            setEdgeWeight(addEdge(start, end), weight);
                        }
                    }
                    this.removeVertex(node);
                });

    }
}
