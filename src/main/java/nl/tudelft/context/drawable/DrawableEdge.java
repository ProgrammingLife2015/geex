package nl.tudelft.context.drawable;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import nl.tudelft.context.graph.Graph;
import nl.tudelft.context.newick.Tree;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 1-5-2015
 */
public class DrawableEdge extends Line {

    /**
     * Creates edge for graph and bind it to nodes.
     *
     * @param graph graph that contains edge
     * @param edge  edge to bind and display
     */
    public DrawableEdge(final Graph graph, final DefaultEdge edge) {

        initialize(graph, edge);

        setTranslateX(30);
        setTranslateY(30);

    }

    /**
     * Creates edge for tree and bind it to nodes.
     *
     * @param tree graph that contains edge
     * @param edge edge to bind and display
     */
    public DrawableEdge(final Tree tree, final DefaultEdge edge) {

        initialize(tree, edge);

        setTranslateY(10);

    }

    /**
     * Creates an edge for a given graph and sets the color to white.
     * @param graph the graph of the edge
     * @param edge  the edge
     */
    private void initialize(DefaultDirectedGraph<? extends DrawableNode, DefaultEdge> graph, DefaultEdge edge) {

        startXProperty().bind(graph.getEdgeSource(edge).translateXProperty());
        endXProperty().bind(graph.getEdgeTarget(edge).translateXProperty());
        startYProperty().bind(graph.getEdgeSource(edge).translateYProperty());
        endYProperty().bind(graph.getEdgeTarget(edge).translateYProperty());

        setStroke(Color.WHITE);

    }

}
