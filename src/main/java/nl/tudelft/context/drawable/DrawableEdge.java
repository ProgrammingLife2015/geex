package nl.tudelft.context.drawable;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import nl.tudelft.context.model.newick.Newick;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

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
     * @param maxWeight     Total amount of possible weight
     */
    public DrawableEdge(final DrawableGraph drawableGraph, final DefaultWeightedEdge edge, final int maxWeight) {

        initialize(drawableGraph, edge);

        setTranslateX(OFFSET_GRAPH);
        setTranslateY(OFFSET_GRAPH);

        startXProperty().bind(drawableGraph.getEdgeSource(edge).translateXProperty());
        endXProperty().bind(drawableGraph.getEdgeTarget(edge).translateXProperty());
        startYProperty().bind(drawableGraph.getEdgeSource(edge).translateYProperty());
        endYProperty().bind(drawableGraph.getEdgeTarget(edge).translateYProperty());

        setStrokeWidth(
                Math.max(MINIMUM_LINE_WIDTH, MAXIMUM_LINE_WIDTH * drawableGraph.getEdgeWeight(edge) / maxWeight));

    }

    /**
     * Creates edge for tree and bind it to nodes.
     *
     * @param newick graph that contains edge
     * @param edge   edge to bind and display
     */
    public DrawableEdge(final Newick newick, final DefaultWeightedEdge edge) {

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
    private void initialize(final DefaultDirectedWeightedGraph<? extends DrawablePosition, DefaultWeightedEdge> graph,
                            final DefaultWeightedEdge edge) {

        startXProperty().bind(graph.getEdgeSource(edge).translateXProperty());
        endXProperty().bind(graph.getEdgeTarget(edge).translateXProperty());
        startYProperty().bind(graph.getEdgeSource(edge).translateYProperty());
        endYProperty().bind(graph.getEdgeTarget(edge).translateYProperty());

        setStroke(Color.WHITE);

    }

}
