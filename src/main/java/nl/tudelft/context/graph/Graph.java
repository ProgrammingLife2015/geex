package nl.tudelft.context.graph;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Graph.
 *
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 23-4-2015
 */
public final class Graph extends DefaultDirectedGraph<Node, DefaultEdge> {

    /**
     * Define the amount of spacing for the nodes.
     */
    public static final int LABEL_SPACING = 100;

    /**
     * Create a Graph with default edges.
     */
    public Graph() {

        super(DefaultEdge.class);

    }

    /**
     * Get the first nodes, with no incoming edges.
     *
     * @return all first node
     */
    public List<Node> getFirstNodes() {

        return vertexSet().stream()
                .filter(x -> this.inDegreeOf(x) == 0)
                .collect(Collectors.toList());

    }

    /**
     * Calculate all the position of the graph elements.
     */
    public void position() {

        List<Node> start = getFirstNodes();

        int i = 0;
        while (!start.isEmpty()) {
            positionNodes(start, i++);
            start = nextColumn(start);
        }

    }

    /**
     * Determine the next column of the graph.
     *
     * @param nodes  nodes to display
     * @return next column
     */
    public List<Node> nextColumn(final List<Node> nodes) {

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
    private void positionNodes(final List<Node> nodes, final int column) {

        int shift = nodes.size() * LABEL_SPACING / 2;

        int row = 0;
        for (Node node : nodes) {
            node.setTranslateX(column * LABEL_SPACING);
            node.setTranslateY(row * LABEL_SPACING - shift);
            row++;
        }

    }

}
