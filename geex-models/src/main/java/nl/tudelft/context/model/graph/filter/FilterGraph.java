package nl.tudelft.context.model.graph.filter;

import nl.tudelft.context.model.graph.DefaultNode;
import nl.tudelft.context.model.graph.StackGraph;
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
public abstract class FilterGraph implements StackGraphFilter {
    /**
     * Filtered and input graph.
     */
    private final StackGraph previous, filtered;
    /**
     * Nodes which are candidate for removal.
     */
    Set<DefaultNode> candidate;

    /**
     * Create a graph with filtered filtered on an other graph.
     *
     * @param graph Graph to remove unkowns form.
     */
    public FilterGraph(final StackGraph graph) {
        this.filtered = graph.deepClone();
        this.previous = graph;
    }

    @Override
    public StackGraph getFilterGraph() {
        markFiltered();
        removeAll();

        return filtered;
    }


    /**
     * The filter used to remove objects.
     *
     * @return filter (predicate)
     */
    protected abstract Predicate<DefaultNode> filter();

    /**
     * Mark all the filtered nodes.
     */
    private void markFiltered() {

        candidate = filtered.vertexSet().stream()
                .filter(filter())
                .filter(node -> filtered.outDegreeOf(node) <= 1 && filtered.inDegreeOf(node) <= 1)
                .collect(Collectors.toSet());

    }

    /**
     * Remove all the filtered nodes.
     */
    private void removeAll() {

        candidate.stream()
                .forEach(node -> {
                    if (filtered.outDegreeOf(node) != 0 && filtered.inDegreeOf(node) != 0) {
                        DefaultNode start = filtered.getSources(node).get(0);
                        DefaultNode end = filtered.getTargets(node).get(0);
                        DefaultWeightedEdge edge = filtered.getEdge(start, end);
                        double weight = filtered.getEdgeWeight(filtered.getEdge(start, node));
                        if (edge != null) {
                            filtered.setEdgeWeight(edge, weight);
                        } else {
                            filtered.setEdgeWeight(filtered.addEdge(start, end), weight);
                        }
                    }
                    filtered.removeVertex(node);
                });

    }
}
