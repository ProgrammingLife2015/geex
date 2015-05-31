package nl.tudelft.context.drawable;

import nl.tudelft.context.model.graph.DefaultGraph;
import nl.tudelft.context.model.graph.Graph;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 31-5-2015
 */
public class DrawableGraph extends DefaultGraph<DrawableNode> {

    /**
     * Define the amount of spacing for the nodes.
     */
    public static final int LABEL_SPACING = 100;

    /**
     * Graph that is drawn.
     */
    private Graph graph;

    /**
     * Create a wrapper around a graph to draw the graph.
     *
     * @param graph Graph to draw
     */
    public DrawableGraph(final Graph graph) {

        this.graph = graph;

        graph.vertexSet().stream()
                .forEach(node -> addVertex(new DrawableNode(node)));

        graph.edgeSet().stream()
                .forEach(edge -> addEdge(
                        new DrawableNode(graph.getEdgeSource(edge)),
                        new DrawableNode(graph.getEdgeTarget(edge))
                ));

        position();

    }

    /**
     * Calculate all the position of the graph elements.
     */
    public void position() {

        List<DrawableNode> start = getFirstNodes();

        int i = 0;
        while (!start.isEmpty()) {
            System.out.println(start);
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
    private List<DrawableNode> nextColumn(final List<DrawableNode> nodes) {

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
    private void positionNodes(final List<DrawableNode> nodes, final int column) {

        int shift = nodes.size() * LABEL_SPACING / 2;

        int row = 0;
        for (DrawableNode node : nodes) {
            node.setTranslateX(column * LABEL_SPACING);
            node.setTranslateY(row * LABEL_SPACING - shift);
            row++;
        }

    }

    /**
     * Get graph that is being drawn.
     *
     * @return Graph that is being drawn
     */
    public Graph getGraph() {
        return graph;
    }

}
