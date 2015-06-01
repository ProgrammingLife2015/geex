package nl.tudelft.context.model.graph;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 1-6-2015
 */
public class SinglePointGraph extends StackGraph {

    /**
     * Parts of a single base mutation.
     */
    Set<Node> singlePart = new HashSet<>();

    /**
     * Nodes where single base mutation starts.
     */
    Set<Node> startSingle = new HashSet<>();

    /**
     * Nodes where single base mutation ends.
     */
    Set<Node> endSingle = new HashSet<>();

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
        markSingle();

        graph.vertexSet().stream()
                .forEach(this::addVertex);

        graph.edgeSet().stream()
                .forEach(edge -> addEdge(
                        graph.getEdgeSource(edge),
                        graph.getEdgeTarget(edge)
                ));

        singlePart.forEach(this::snip);
        endSingle.forEach(this::snip);
        startSingle.forEach(node ->
                replace(node, new Node((int) (-Math.random() * 100000000), new HashSet<>(), 0, 0, "")));

    }

    /**
     * Mark all the single base mutations.
     */
    public void markSingle() {

        graph.vertexSet().stream()
                .forEach(startNode -> {

                    List<Node> targets = graph.getTargets(startNode);

                    if (targets.size() == 1 || targets.stream().anyMatch(t -> t.getContent().length() != 1)) {
                        return;
                    }

                    Set<Node> end = targets.stream()
                            .map(graph::getTargets)
                            .flatMap(Collection::stream)
                            .collect(Collectors.toSet());

                    if (end.size() == 1) {
                        end.stream().filter(endNode -> graph.incomingEdgesOf(endNode).size() == targets.size())
                                .forEach(endNode -> {
                                    targets.stream().forEach(singlePart::add);
                                    startSingle.add(startNode);
                                    endSingle.add(endNode);
                                });
                    }

                });


        filterSingle();

    }

    /**
     * Remove duplicates in single start & and.
     */
    public void filterSingle() {

        Set<Node> overlay = new HashSet<>(startSingle);
        overlay.retainAll(endSingle);

        singlePart.addAll(overlay);
        startSingle.removeAll(overlay);
        endSingle.removeAll(overlay);

    }

}
