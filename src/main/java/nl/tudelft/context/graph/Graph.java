package nl.tudelft.context.graph;

import org.apache.commons.collections.CollectionUtils;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.List;
import java.util.Set;
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

        return vertexSet().stream().filter(x -> this.inDegreeOf(x) == 0).collect(Collectors.toList());

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

}
