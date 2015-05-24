package nl.tudelft.context.controller;

import de.saxsys.javafx.test.JfxRunner;
import nl.tudelft.context.model.graph.Graph;
import nl.tudelft.context.model.graph.GraphParser;
import nl.tudelft.context.model.graph.Node;
import nl.tudelft.context.workspace.Workspace;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 26-4-2015
 */
@RunWith(JfxRunner.class)
public class MainControllerTest {

    protected static Graph graph;

    protected static final Node node1 = new Node(0, new HashSet<>(Arrays.asList("Cat", "Dog")), 5, 7, "A");
    protected static final Node node2 = new Node(1, new HashSet<>(Collections.singletonList("Dog")), 7, 10, "C");

    protected static MainController mainController;
    protected static Workspace workspace;

    /**
     * Setup Main Controller.
     */
    @BeforeClass
    public static void beforeClass() throws Exception {

        File nodeFile = new File(BaseControllerTest.class.getResource("/graph/node.graph").getPath());
        File edgeFile = new File(BaseControllerTest.class.getResource("/graph/edge.graph").getPath());

        graph = new GraphParser().setReader(nodeFile, edgeFile).parse().flat(new HashSet<>(Arrays.asList("Cat", "Dog")));

        mainController = new MainController();

        workspace = mock(Workspace.class);

        when(workspace.getNodeFile()).thenReturn(mock(File.class));
        when(workspace.getEdgeFile()).thenReturn(mock(File.class));
        when(workspace.getNwkFile()).thenReturn(mock(File.class));

    }

    /**
     * Test RuntimeException on wrong FXML file.
     */
    @Test(expected = RuntimeException.class)
    public void testWrongFXMLFile() {

        mainController.loadFXML("");

    }

    /**
     * Test previous view.
     */
    @Test
    public void testPreviousView() {

        MainController mc = new MainController();
        mc.setWorkspace(workspace);

        BaseController baseController1 = new BaseController(graph, node1);
        BaseController baseController2 = new BaseController(graph, node2);

        mc.setBaseView(baseController1);
        mc.setView(baseController1, baseController2);

        mc.previousView();

        assertEquals(baseController1, mc.viewList.get(0));
        assertEquals(baseController1.getRoot(), mc.view.getChildren().get(0));
        assertFalse(baseController2.getVisibilityProperty().getValue());

    }

    /**
     * Test to view.
     */
    public void testToView() {

        MainController mc = new MainController();
        mc.setWorkspace(workspace);

        BaseController baseController1 = new BaseController(graph, node1);
        BaseController baseController2 = new BaseController(graph, node2);

        mc.setBaseView(baseController1);
        mc.setView(baseController1, baseController2);

        assertTrue(baseController1.getVisibilityProperty().getValue());
        assertTrue(baseController2.getVisibilityProperty().getValue());

        mc.toView(baseController1);

        assertTrue(baseController1.getVisibilityProperty().getValue());
        assertFalse(baseController2.getVisibilityProperty().getValue());

    }

    /**
     * Test to view.
     */
    @Test
    public void testToViewWithNewick() {

        MainController mc = new MainController();
        mc.setWorkspace(workspace);
        mc.toggleNewick();

        BaseController baseController1 = new BaseController(graph, node1);
        BaseController baseController2 = new BaseController(graph, node2);

        mc.setBaseView(baseController1);
        mc.setView(baseController1, baseController2);

        assertTrue(baseController1.getVisibilityProperty().getValue());
        assertTrue(baseController2.getVisibilityProperty().getValue());

        mc.toView(baseController1);

        assertTrue(baseController1.getVisibilityProperty().getValue());
        assertFalse(baseController2.getVisibilityProperty().getValue());

    }

    /**
     * Test viewing text.
     */
    @Test
    public void testFooterText() {
        MainController mc = new MainController();
        mc.setWorkspace(workspace);

        String text = "This is a test.";
        mc.displayMessage(text);

        assertEquals(mc.messageController.message.getText(), text);
    }

    /**
     * Test toggle Newick.
     */
    @Test
    public void toggleNewick() {

        assertFalse(mainController.newickLifted.getValue());

        mainController.toggleNewick();

        assertTrue(mainController.newickLifted.getValue());

        mainController.toggleNewick();

        assertFalse(mainController.newickLifted.getValue());

    }

}
