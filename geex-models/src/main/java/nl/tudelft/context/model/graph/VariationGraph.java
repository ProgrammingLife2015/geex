package nl.tudelft.context.model.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Jim
 * @since 6/2/2015
 */
public class VariationGraph extends StackGraph {

    /**
     * The graph that will be read.
     */
    StackGraph graph;

    /**
     * All the variations that are detected.
     */
    HashSet<DefaultNode> variations;

    /**
     * Map with all start and end nodes.
     */
    Map<DefaultNode, DefaultNode> variationStartEnd;

    /**
     * The constructor.
     * @param graph Graph that will be read.
     */
    public VariationGraph(final StackGraph graph) {

        this.graph = graph;
        this.variations = new HashSet<>();
        this.variationStartEnd = new HashMap<>();
        this.setMarkVariations(true);

        setGraph(graph);

        checkMutations();

        variations.stream().forEach(node -> node.setVariation(true));

    }

    /**
     * The function that creates the mutations.
     */
    public void checkMutations() {

        Set<DefaultNode> nodeSet = vertexSet().stream()
                .filter(node -> outDegreeOf(node) > 1)
                .collect(Collectors.toSet());

        nodeSet.forEach((startNode) -> {
            if (!variations.contains(startNode)) {
                DefaultNode temp = checkVariation(startNode);
                if (!variations.contains(temp)) {
                    variationStartEnd.put(startNode, temp);
                }
            }
        });

    }

    /**
     * The function that recursively checks each variation.
     * @param startNode The node that starts the variation.
     * @return Returns the end of the variation.
     */
    private DefaultNode checkVariation(final DefaultNode startNode) {

        Queue<DefaultNode> nextNodes =  new LinkedList<>();
        HashSet<DefaultNode> currentVariation = new HashSet<>();
        nextNodes.addAll(getTargets(startNode));

        System.out.println(((Node) startNode).getId() + " goes to " + nextNodes.stream().map(node -> ((Node) node).getId()).collect(Collectors.toList()));

        int amountOfBranches = nextNodes.size() - 1;

        while (!nextNodes.isEmpty()) {

            DefaultNode node = nextNodes.remove();

            if (currentVariation.contains(node) || nextNodes.contains(node)) {

                amountOfBranches--;
                if (amountOfBranches == 0) {
                    currentVariation.removeIf(node2 -> node == node2);
                    variations.addAll(currentVariation);
                    return node;
                }

            } else {

                currentVariation.add(node);

                List<DefaultNode> setOfNextNodes = getTargets(node);
                System.out.println(((Node) node).getId() + " leads to " + setOfNextNodes.stream().map(node2 -> ((Node) node2).getId()).collect(Collectors.toList()));
                if (setOfNextNodes.size() > 1) {
                    nextNodes.add(checkVariation(node));
                } else if (!setOfNextNodes.isEmpty() && !nextNodes.isEmpty() && !currentVariation.containsAll(setOfNextNodes)) {
                    nextNodes.addAll(setOfNextNodes);
                } else {
                    variations.addAll(currentVariation);
                    return node;
                }

            }

        }

        return null;

    }

}
