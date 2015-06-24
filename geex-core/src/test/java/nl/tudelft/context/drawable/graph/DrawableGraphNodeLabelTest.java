package nl.tudelft.context.drawable.graph;

import de.saxsys.javafx.test.JfxRunner;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import nl.tudelft.context.controller.GraphController;
import nl.tudelft.context.controller.MainController;
import nl.tudelft.context.model.graph.Graph;
import nl.tudelft.context.model.graph.GraphNode;
import nl.tudelft.context.model.graph.Node;
import nl.tudelft.context.model.graph.NodeParser;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

/**
 * @author René Vennik
 * @version 1.0
 * @since 24-6-2015
 */
@RunWith(JfxRunner.class)
public class DrawableGraphNodeLabelTest {

    static DrawableGraphNodeLabel drawableGraphNodeLabel1, drawableGraphNodeLabel2, drawableGraphNodeLabel3;
    static Node node1, node2;
    static GraphNode graphNode1, graphNode2;

    static MainController mainController;
    static GraphController graphController;

    /**
     * Set up graph nodes.
     */
    @BeforeClass
    public static void beforeClass() {

        mainController = mock(MainController.class);
        graphController = mock(GraphController.class);

        NodeParser nodeParser = new NodeParser();
        node1 = nodeParser.getNode(new Scanner(">0 | Cat,Dog | 5 | 7\nA\n"));
        node2 = nodeParser.getNode(new Scanner(">1 | Dog | 8 | 10\nATC\n"));
        Graph graph = new Graph();
        graphNode1 = new GraphNode(graph, node1, "collapse");
        graphNode2 = new GraphNode(graph, node1, "collapse");

        drawableGraphNodeLabel1 = new DrawableGraphNodeLabel(mainController, graphController, new DrawableGraphNode(graphNode1), graphNode1);
        drawableGraphNodeLabel2 = new DrawableGraphNodeLabel(mainController, graphController, new DrawableGraphNode(graphNode2), graphNode2);
        drawableGraphNodeLabel3 = new DrawableGraphNodeLabel(mainController, graphController, new DrawableGraphNode(graphNode1), graphNode1);

    }

    /**
     * Test equals.
     */
    @Test
    public void testEquals() {

        assertEquals(drawableGraphNodeLabel1, drawableGraphNodeLabel3);

    }

    /**
     * Test not equals.
     */
    @Test
    public void testNotEquals() {

        assertNotEquals(drawableGraphNodeLabel1, drawableGraphNodeLabel2);

    }

    /**
     * Test if hashcode equals to node hashcode.
     */
    @Test
    public void testHashCode() {

        assertEquals(drawableGraphNodeLabel1.hashCode(), graphNode1.hashCode());

    }

    /**
     * Check if all main label is added.
     */
    @Test
    public void testMainLabel() {

        assertThat(drawableGraphNodeLabel1.getChildren().get(0), instanceOf(Label.class));


    }

    /**
     * Check if annotation box is added.
     */
    @Test
    public void testAnnotationBox() {

        assertThat(drawableGraphNodeLabel1.getChildren().get(1), instanceOf(HBox.class));


    }

    /**
     * Test update sources.
     */
    @Test
    public void testUpdateSources() {

        assertFalse(drawableGraphNodeLabel2.getStyleClass().contains("selected-label"));

        drawableGraphNodeLabel2.updateSources(new HashSet<>(Collections.singletonList("Dog")));
        assertTrue(drawableGraphNodeLabel2.getStyleClass().contains("selected-label"));

        drawableGraphNodeLabel2.updateSources(new HashSet<>());
        assertFalse(drawableGraphNodeLabel2.getStyleClass().contains("selected-label"));

    }

    /**
     * Test click on label
     */
    @Test
    public void testClick() {

        verifyZeroInteractions(mainController);

        EventHandler<? super MouseEvent> eh = drawableGraphNodeLabel1.getOnMouseClicked();
        MouseEvent myMouseEvent = new MouseEvent(drawableGraphNodeLabel1, drawableGraphNodeLabel1, MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true, true, true, true, true, true, true, null);
        eh.handle(myMouseEvent);

        verify(mainController).setView(anyObject(), anyObject(), anyBoolean());

    }

}