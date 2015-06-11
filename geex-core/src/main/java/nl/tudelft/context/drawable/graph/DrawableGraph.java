package nl.tudelft.context.drawable.graph;

import nl.tudelft.context.model.graph.DefaultGraph;
import nl.tudelft.context.model.graph.DefaultNode;
import nl.tudelft.context.model.graph.StackGraph;

import java.util.HashMap;
import java.util.List;
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
    private StackGraph graph;

    /**
     * Create a wrapper around a graph to draw the graph.
     *
     * @param graph Graph to draw
     */
    public DrawableGraph(final StackGraph graph) {

        this.graph = graph;

        HashMap<DefaultNode, AbstractDrawableNode> added = new HashMap<>();

        graph.vertexSet().stream()
                .forEach(node -> {
                    AbstractDrawableNode abstractDrawableNode = DrawableNodeFactory.create(node);
                    added.put(node, abstractDrawableNode);
                    addVertex(abstractDrawableNode);
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
    public void position() {

        List<AbstractDrawableNode> start = getFirstNodes();

        int i = 0;
        while (!start.isEmpty()) {
            positionNodes(start, i++);
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
                        .filter(x -> x.incrementIncoming() == inDegreeOf(x)))
                .collect(Collectors.toList());

    }

    /**
     * Positions the nodes of a column.
     *
     * @param nodes  nodes to draw
     * @param column column to draw at
     */
    private void positionNodes(final List<AbstractDrawableNode> nodes, final int column) {

        int shift = nodes.size() * LABEL_SPACING / 2;

        int row = 0;
        for (AbstractDrawableNode node : nodes) {
            node.setTranslateX(column * LABEL_SPACING);
            node.setTranslateY(row * LABEL_SPACING - shift);
            row++;
        }

        if (nodes.size() == 1) {
            AbstractDrawableNode node = nodes.get(0);
            if (node.getNode().isShift()) {
                node.setTranslateY(-LABEL_SPACING);
            }
        }

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
