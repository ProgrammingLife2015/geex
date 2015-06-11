package nl.tudelft.context.model.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author René Vennik
 * @version 1.0
 * @since 11-6-2015
 */
public class InsertDeleteGraph extends StackGraph {

    /**
     * Parts of a insert deletion.
     */
    Set<DefaultNode> inDelPart = new HashSet<>();

    /**
     * Map with start and end of insert deletions.
     */
    Map<DefaultNode, DefaultNode> inDel = new HashMap<>();

    /**
     * Clean graph.
     */
    StackGraph graph;

    /**
     * Create a graph with insert deletes single point mutations based on an other graph.
     *
     * @param graph Graph to calculate insert deletes on
     */
    public InsertDeleteGraph(final StackGraph graph) {

        this.graph = graph;
        setGraph(graph);

        markInDel();

    }

    /**
     * Mark the insert deletes.
     */
    private void markInDel() {

        graph.vertexSet().stream()
                .forEach(startNode -> {

                    List<DefaultNode> targets = getTargets(startNode);

                    if (targets.size() != 2) {
                        return;
                    }

                    List<List<DefaultNode>> end = targets.stream().map(this::getTargets).collect(Collectors.toList());
                    if (end.get(0).size() == 1 && end.get(0).get(0).equals(targets.get(1))) {
                        inDelPart.add(targets.get(0));
                        inDel.put(startNode, targets.get(1));
                    } else if (end.get(1).size() == 1 && end.get(1).get(0).equals(targets.get(0))) {
                        inDelPart.add(targets.get(1));
                        inDel.put(startNode, targets.get(0));
                    }

                });

    }

}
