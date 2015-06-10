package nl.tudelft.context.drawable;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import nl.tudelft.context.drawable.graph.DrawableGraph;
import nl.tudelft.context.model.newick.Newick;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * @author René Vennik
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
     */
    public DrawableEdge(final DrawableGraph drawableGraph, final DefaultWeightedEdge edge) {

        initialize();
        setStart(drawableGraph.getEdgeSource(edge));
        setEnd(drawableGraph.getEdgeTarget(edge));

        setTranslateX(OFFSET_GRAPH);
        setTranslateY(OFFSET_GRAPH);

        setStrokeWidth(Math.max(MINIMUM_LINE_WIDTH, drawableGraph.getEdgeWeight(edge) * MAXIMUM_LINE_WIDTH));

    }

    /**
     * Creates edge for tree and bind it to nodes.
     *
     * @param newick graph that contains edge
     * @param edge   edge to bind and display
     */
    public DrawableEdge(final Newick newick, final DefaultEdge edge) {

        initialize();
        setStart(new DrawableNewickNode(newick.getEdgeSource(edge)));
        setEnd(new DrawableNewickNode(newick.getEdgeTarget(edge)));

        setTranslateX(OFFSET_TREE);
        setTranslateY(OFFSET_TREE);

    }

    /**
     * Creates an edge for a given graph and sets the color to white.
     */
    private void initialize() {
        setStroke(Color.WHITE);
    }

    /**
     * Bind the start position of the edge.
     *
     * @param start Position to bind with
     */
    private void setStart(final DrawablePosition start) {
        startXProperty().bind(start.translateXProperty());
        startYProperty().bind(start.translateYProperty());
    }

    /**
     * Bind the end position of the edge.
     *
     * @param end Position to bind with
     */
    private void setEnd(final DrawablePosition end) {
        endXProperty().bind(end.translateXProperty());
        endYProperty().bind(end.translateYProperty());
    }

}
