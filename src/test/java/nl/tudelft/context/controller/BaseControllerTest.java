package nl.tudelft.context.controller;

import nl.tudelft.context.graph.Graph;
import nl.tudelft.context.graph.GraphFactory;
import nl.tudelft.context.graph.Node;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 8-5-2015
 */
public class BaseControllerTest {

    protected static BaseController baseController;

    protected static Graph graph;

    protected static final Node node1 = new Node(0, new HashSet<>(Arrays.asList("Cat", "Dog")), 5, 7, "A");

    /**
     * Setup Base Controller.
     */
    @BeforeClass
    public static void beforeClass() throws Exception {

        File nodeFile = new File(BaseControllerTest.class.getResource("/graph/node.graph").getPath());
        File edgeFile = new File(BaseControllerTest.class.getResource("/graph/edge.graph").getPath());

        GraphFactory graphFactory = new GraphFactory();
        graph = graphFactory.getGraph(nodeFile, edgeFile);

        baseController = new BaseController(graph, node1);

    }

    /**
     * Test RuntimeException on wrong FXML file.
     */
    @Test(expected = RuntimeException.class)
    public void testWrongFXMLFile() {

        baseController.loadFXML("");

    }

    /**
     * Test if base content is correct.
     */
    @Test
    public void testBaseContent() {

        assertEquals("A", baseController.base.getText());

    }

}
