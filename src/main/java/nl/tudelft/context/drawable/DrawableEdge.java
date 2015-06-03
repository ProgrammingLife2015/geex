package nl.tudelft.context.drawable;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import nl.tudelft.context.model.newick.Newick;
import org.jgrapht.graph.DefaultEdge;
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

    }

    /**
     * Creates edge for tree and bind it to nodes.
     *
     * @param newick graph that contains edge
     * @param edge   edge to bind and display
     */
    public DrawableEdge(final Newick newick, final DefaultEdge edge) {

        initialize();
        setStart(newick.getEdgeSource(edge));
        setEnd(newick.getEdgeTarget(edge));

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
