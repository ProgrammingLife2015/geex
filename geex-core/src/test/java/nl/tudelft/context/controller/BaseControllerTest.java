package nl.tudelft.context.controller;

import de.saxsys.javafx.test.JfxRunner;
import nl.tudelft.context.model.graph.Graph;
import nl.tudelft.context.model.graph.GraphParser;
import nl.tudelft.context.model.graph.Node;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

/**
 * @author René Vennik
 * @version 1.0
 * @since 8-5-2015
 */
@RunWith(JfxRunner.class)
public class BaseControllerTest {

    protected static BaseController baseController;

    protected static Graph graph;

    protected static final Node node1 = new Node(0, new HashSet<>(Arrays.asList("Cat", "Dog")), 5, 7, "A");
    protected static final Node node2 = new Node(0, new HashSet<>(Arrays.asList("Cat", "TKK_REF")), 5, 7, "A");

    /**
     * Setup Base Controller.
     */
    @BeforeClass
    public static void beforeClass() throws Exception {

        File nodeFile = new File(BaseControllerTest.class.getResource("/graph/node.graph").getPath());
        File edgeFile = new File(BaseControllerTest.class.getResource("/graph/edge.graph").getPath());

        graph = new GraphParser().setFiles(nodeFile, edgeFile).load().flat(new HashSet<>(Arrays.asList("Cat", "Dog")));

    }

    /**
     * Test RuntimeException on wrong FXML file.
     */
    @Test(expected = RuntimeException.class)
    public void testWrongFXMLFile() {

        baseController = new BaseController(node1);
        baseController.loadFXML("");

    }

    /**
     * Test if base content and text are correct.
     */
    @Test
    public void testBaseContent() {

        baseController = new BaseController(node1);
        assertEquals("A", baseController.bases.getText());
        assertEquals("None", baseController.codingSequences.getText());

    }

    /**
     * Test if base content and text are correct, containing TKK_REF.
     */
    @Test
    public void testTkkRefBaseContent() {

        baseController = new BaseController(node2);
        assertEquals("A", baseController.bases.getText());
        assertEquals(node2.getCodingSequenceText(), baseController.codingSequences.getText());

    }

}
