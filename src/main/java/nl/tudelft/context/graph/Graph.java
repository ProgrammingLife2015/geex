package nl.tudelft.context.graph;

import org.apache.commons.collections.CollectionUtils;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
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
     * Clean the graph from sources that aren't shown.
     *
     * @param sources Source to keep.
     */
    public void cleanGraph(final Set<String> sources) {

        // Remove unnecessary edges
        removeAllEdges(edgeSet().stream()
                .filter(edge -> {
                    Node source = getEdgeSource(edge);
                    Node target = getEdgeTarget(edge);
                    return !CollectionUtils.containsAny(source.getSources(), sources)
                            || !CollectionUtils.containsAny(target.getSources(), sources);
                })
                .collect(Collectors.toList()));

        // Remove unnecessary nodes
        removeAllVertices(vertexSet().stream()
                .filter(vertex -> !CollectionUtils.containsAny(vertex.getSources(), sources))
                .collect(Collectors.toList()));

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
    private List<Node> nextColumn(final List<Node> nodes) {

        return nodes.stream()
                .map(node -> outgoingEdgesOf(node).stream()
                        .map(this::getEdgeTarget)
                        .filter(x -> x.incrementIncoming() == inDegreeOf(x)))
                .flatMap(Function.identity())
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
