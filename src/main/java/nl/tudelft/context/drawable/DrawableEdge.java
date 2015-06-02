package nl.tudelft.context.drawable;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import nl.tudelft.context.model.graph.DefaultNode;
import nl.tudelft.context.model.newick.Newick;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.Set;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 1-5-2015
 */
public class DrawableEdge extends Line {

    /**
     * Define the Y translation of the graph nodes.
     */
    public static final int OFFSET_GRAPH = 30;

    /**
     * Define the Y translation of the graph nodes.
     */
    public static final int OFFSET_TREE = 10;

    /**
     * Min width of line.
     */
    private static final double MINIMUM_LINE_WIDTH = .25;

    /**
     * Max width of line.
     */
    private static final double MAXIMUM_LINE_WIDTH = 8;

    /**
     * Creates edge for graph and bind it to nodes.
     *
     * @param drawableGraph graph that contains edge
     * @param edge          edge to bind and display
     * @param sources       strains in this graph
     */
    public DrawableEdge(final DrawableGraph drawableGraph, final DefaultEdge edge, final Set<String> sources) {

        initialize(drawableGraph, edge);

        setTranslateX(OFFSET_GRAPH);
        setTranslateY(OFFSET_GRAPH);

        DefaultNode source = drawableGraph.getEdgeSource(edge).getNode();
        DefaultNode target = drawableGraph.getEdgeTarget(edge).getNode();

        long sourceAmount = source.getSources().stream()
                .filter(s -> target.getSources().contains(s) && sources.contains(s))
                .count();
        setStrokeWidth(Math.max(MINIMUM_LINE_WIDTH, MAXIMUM_LINE_WIDTH * sourceAmount / sources.size()));

    }

    /**
     * Creates edge for tree and bind it to nodes.
     *
     * @param newick graph that contains edge
     * @param edge   edge to bind and display
     */
    public DrawableEdge(final Newick newick, final DefaultEdge edge) {

        initialize(newick, edge);

        setTranslateX(OFFSET_TREE);
        setTranslateY(OFFSET_TREE);

    }

    /**
     * Creates an edge for a given graph and sets the color to white.
     *
     * @param graph the graph of the edge
     * @param edge  the edge
     */
    private void initialize(final DefaultDirectedGraph<? extends DrawablePosition, DefaultEdge> graph,
                            final DefaultEdge edge) {

        startXProperty().bind(graph.getEdgeSource(edge).translateXProperty());
        endXProperty().bind(graph.getEdgeTarget(edge).translateXProperty());
        startYProperty().bind(graph.getEdgeSource(edge).translateYProperty());
        endYProperty().bind(graph.getEdgeTarget(edge).translateYProperty());

        setStroke(Color.WHITE);

    }

}
