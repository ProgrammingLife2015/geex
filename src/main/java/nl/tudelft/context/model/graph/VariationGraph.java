package nl.tudelft.context.model.graph;

import java.util.*;
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
    List<DefaultNode> variations;

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
        this.variations = new ArrayList<>();
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
        nextNodes.addAll(getTargets(startNode));

        int amountOfBranches = nextNodes.size() - 1;
        DefaultNode node = null;

        while (!nextNodes.isEmpty()) {

            node = nextNodes.remove();

            if (!variations.contains(node)) {

                variations.add(node);

                List<DefaultNode> setOfNextNodes = getTargets(node);
                if (setOfNextNodes.size() > 1) {
                    nextNodes.add(checkVariation(node));
                } else if (!setOfNextNodes.isEmpty() || !nextNodes.isEmpty()) {
                    nextNodes.addAll(setOfNextNodes);
                }

            } else {

                amountOfBranches--;
                if (amountOfBranches == 0) {
                    variations.remove(node);
                    break;
                }

            }

        }

        return node;

    }

}
