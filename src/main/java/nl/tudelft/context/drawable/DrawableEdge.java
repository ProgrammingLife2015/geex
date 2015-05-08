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
     * Create edge and bind it to nodes.
     *
     * @param graph graph that contains edge
     * @param edge  edge to bind and display
     */
    public DrawableEdge(DefaultDirectedGraph<? extends DrawableNode, DefaultEdge> graph, DefaultEdge edge) {

        startXProperty().bind(graph.getEdgeSource(edge).translateXProperty());
        endXProperty().bind(graph.getEdgeTarget(edge).translateXProperty());
        startYProperty().bind(graph.getEdgeSource(edge).translateYProperty());
        endYProperty().bind(graph.getEdgeTarget(edge).translateYProperty());

        setTranslateX(30);
        setTranslateY(30);
        setStroke(Color.WHITE);

    }

}
