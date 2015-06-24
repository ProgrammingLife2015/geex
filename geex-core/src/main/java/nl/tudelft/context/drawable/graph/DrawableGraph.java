package nl.tudelft.context.drawable.graph;

import nl.tudelft.context.model.graph.DefaultGraph;
import nl.tudelft.context.model.graph.DefaultNode;
import nl.tudelft.context.model.graph.StackGraph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/**
 * @author René Vennik
 * @version 1.0
 * @since 31-5-2015
 */
public class DrawableGraph extends DefaultGraph<AbstractDrawableNode> {

    /**
     * Define the amount of spacing for the nodes.
     */
    public static final int LABEL_SPACING = 100;

    /**
     * Graph that is drawn.
     */
    private final StackGraph graph;

    /**
     * Create a wrapper around a graph to draw the graph.
     *
     * @param graph Graph to draw
     */
    public DrawableGraph(final StackGraph graph) {

        super();
        this.graph = graph;

        final Map<DefaultNode, AbstractDrawableNode> added = new HashMap<>();

        graph.vertexSet().stream()
                .forEach(node -> {
                    final AbstractDrawableNode drawableNode = DrawableNodeFactory.create(node);
                    added.put(node, drawableNode);
                    addVertex(drawableNode);
                });

        graph.edgeSet().stream()
                .forEach(edge -> setEdgeWeight(addEdge(
                        added.get(graph.getEdgeSource(edge)),
                        added.get(graph.getEdgeTarget(edge))
                ), graph.getEdgeWeight(edge)));

        position();

    }

    /**
     * Calculate all the position of the graph elements.
     */
    public final void position() {

        List<AbstractDrawableNode> start = getFirstNodes();

        int column = 0;
        boolean shifted = false;
        while (!start.isEmpty()) {
            shifted = positionNodes(start, column++, shifted);
            start = nextColumn(start);
        }

    }

    /**
     * Determine the next column of the graph.
     *
     * @param nodes nodes to display
     * @return next column
     */
    private List<AbstractDrawableNode> nextColumn(final List<AbstractDrawableNode> nodes) {

        return nodes.stream()
                .flatMap(node -> outgoingEdgesOf(node).stream()
                        .map(this::getEdgeTarget)
                        .filter(target -> target.incrementIncoming() == inDegreeOf(target)))
                .collect(Collectors.toList());

    }

    /**
     * Positions the nodes of a column.
     *
     * @param nodes       nodes to draw
     * @param column      column to draw at
     * @param prevShifted If previous column is shifted
     * @return If column is shifted
     */
    private boolean positionNodes(final List<AbstractDrawableNode> nodes, final int column, final boolean prevShifted) {

        final double shift = nodes.size() * LABEL_SPACING / 2d;

        int row = 0;
        for (final AbstractDrawableNode node : nodes) {
            node.setTranslateX(column * LABEL_SPACING);
            node.setTranslateY(row * LABEL_SPACING - shift);
            row++;
        }

        return isShifted(nodes, prevShifted);

    }

    /**
     * Check if the node should be shifted.
     *
     * @param nodes       Nodes to possible shift if only 1 node
     * @param prevShifted If the previous node was shifted
     * @return If this node is shifted
     */
    private boolean isShifted(final List<AbstractDrawableNode> nodes, final boolean prevShifted) {
        boolean shifted = false;
        if (nodes.size() == 1) {
            shifted = nodes.stream().map(node -> {
                final boolean single = hasEdges(getSources(node), this::outDegreeOf)
                        && hasEdges(getTargets(node), this::inDegreeOf);
                final boolean drawShift = inDegreeOf(node) == 1 && outDegreeOf(node) >= 1 && single;
                if (node.getNode().isShift() || drawShift) {
                    if (prevShifted) {
                        node.setTranslateY(0);
                    } else {
                        node.setTranslateY(-LABEL_SPACING);
                    }
                    return !prevShifted;
                }
                return false;
            }).findFirst().get();
        }
        return shifted;
    }

    /**
     * Check if there are nodes with more then 1 incoming or outgoing edges.
     *
     * @param nodes       Nodes to count from
     * @param degreeCount Function that returns the degree of edges
     * @return If there are nodes with a higher than 1 edge degree
     */
    private boolean hasEdges(final List<AbstractDrawableNode> nodes,
                             final ToIntFunction<AbstractDrawableNode> degreeCount) {

        return nodes.stream()
                .mapToInt(degreeCount)
                .anyMatch(source -> source > 1);

    }

    /**
     * Get graph that is being drawn.
     *
     * @return Graph that is being drawn
     */
    public StackGraph getGraph() {
        return graph;
    }

}
