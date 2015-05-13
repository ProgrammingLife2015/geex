package nl.tudelft.context.drawable;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 1-5-2015
 */
public class DrawableEdge extends Line {

    /**
     * Define the X translation.
     */
    public static final int X_OFFSET = 30;

    /**
     * Define the Y translation.
     */
    public static final int Y_OFFSET = 30;

    /**
     * Create edge and bind it to nodes.
     *
     * @param graph graph that contains edge
     * @param edge  edge to bind and display
     */
    public DrawableEdge(final DefaultDirectedGraph<? extends DrawableNode, DefaultEdge> graph, final DefaultEdge edge) {

        startXProperty().bind(graph.getEdgeSource(edge).translateXProperty());
        endXProperty().bind(graph.getEdgeTarget(edge).translateXProperty());
        startYProperty().bind(graph.getEdgeSource(edge).translateYProperty());
        endYProperty().bind(graph.getEdgeTarget(edge).translateYProperty());

        setTranslateX(X_OFFSET);
        setTranslateY(Y_OFFSET);
        setStroke(Color.WHITE);

    }

}
