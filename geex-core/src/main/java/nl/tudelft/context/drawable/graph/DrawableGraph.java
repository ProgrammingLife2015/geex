package nl.tudelft.context.drawable.graph;

import nl.tudelft.context.model.graph.DefaultGraph;
import nl.tudelft.context.model.graph.DefaultNode;
import nl.tudelft.context.model.graph.StackGraph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    final private StackGraph graph;

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
    final public void position() {

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

        final double shift = nodes.size() * LABEL_SPACING / 2;

        int row = 0;
        for (final AbstractDrawableNode node : nodes) {
            node.setTranslateX(column * LABEL_SPACING);
            node.setTranslateY(row * LABEL_SPACING - shift);
            row++;
        }

        if (nodes.size() == 1) {
            final AbstractDrawableNode node = nodes.get(0);
            final long sourcesEdges = getSources(node).stream()
                    .mapToInt(this::outDegreeOf).filter(source -> source > 1)
                    .count();
            final long targetEdges = getTargets(node).stream()
                    .mapToInt(this::inDegreeOf).filter(target -> target > 1)
                    .count();
            final boolean drawShift = inDegreeOf(node) == 1 && outDegreeOf(node) >= 1
                    && sourcesEdges > 0 && targetEdges > 0;
            if (node.getNode().isShift() || drawShift) {
                node.setTranslateY(prevShifted ? 0 : -LABEL_SPACING);
                return !prevShifted;
            }
        }

        return false;

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
