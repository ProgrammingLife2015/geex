package nl.tudelft.context.model.graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author René Vennik
 * @version 1.0
 * @since 1-6-2015
 */
public class SinglePointGraph extends StackGraph {

    /**
     * Parts of a single base mutation.
     */
    Set<DefaultNode> singlePart = new HashSet<>();

    /**
     * Map with start and end of single mutation.
     */
    Map<DefaultNode, DefaultNode> single = new HashMap<>();

    /**
     * Clean graph.
     */
    StackGraph graph;

    /**
     * Create a graph with single point mutations based on an other graph.
     *
     * @param graph Graph to calculate single point mutations on
     */
    public SinglePointGraph(final StackGraph graph) {

        this.graph = graph;
        setGraph(graph);

        markSingle();
        filterSingle();
        replaceSingle();

    }

    /**
     * Mark all the single base mutations.
     */
    private void markSingle() {

        graph.vertexSet().stream()
                .forEach(startNode -> {

                    List<DefaultNode> targets = graph.getTargets(startNode);

                    if (targets.size() == 1 || targets.stream().anyMatch(t -> t.getContent().length() != 1)) {
                        return;
                    }

                    Set<DefaultNode> end = targets.stream()
                            .map(graph::getTargets)
                            .flatMap(Collection::stream)
                            .collect(Collectors.toSet());

                    if (end.size() == 1) {
                        end.stream().filter(endNode -> graph.inDegreeOf(endNode) == targets.size())
                                .forEach(endNode -> {
                                    targets.stream().forEach(singlePart::add);
                                    single.put(startNode, endNode);
                                });
                    }

                });

    }

    /**
     * Remove duplicates in single start & and.
     */
    private void filterSingle() {

        Map<DefaultNode, DefaultNode> newSingle = new HashMap<>();

        Set<DefaultNode> overlay = new HashSet<>(single.keySet());
        overlay.retainAll(single.values());

        single.keySet().stream()
                .filter(node -> !overlay.contains(node))
                .forEach(startNode -> {
                    DefaultNode endNode = startNode;
                    while (single.containsKey(endNode)) {
                        endNode = single.get(endNode);
                    }
                    newSingle.put(startNode, endNode);
                });

        single = newSingle;
        singlePart.addAll(overlay);

    }

    /**
     * Replace all single parts with a graph node.
     */
    private void replaceSingle() {

        singlePart.forEach(this::removeVertex);
        single.forEach((start, end) -> {
            setEdgeWeight(
                    addEdge(start, end),
                    graph.outgoingEdgesOf(end).stream().mapToDouble(graph::getEdgeWeight).sum()
            );
            replace(start, new GraphNode(graph, start, end, "single"));
        });

    }

}
