package nl.tudelft.context.controller;

import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.text.Text;
import nl.tudelft.context.graph.Graph;
import nl.tudelft.context.graph.GraphParser;
import nl.tudelft.context.graph.Node;
import nl.tudelft.context.service.LoadGraphService;
import nl.tudelft.context.service.LoadNewickService;
import nl.tudelft.context.workspace.Workspace;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
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

        GraphParser graphParser = new GraphParser();
        graph = graphParser.getGraphMap(nodeFile, edgeFile).flat(new HashSet<>(Arrays.asList("Cat", "Dog")));

        mainController = new MainController();

        workspace = mock(Workspace.class);

        when(workspace.getGraphList()).thenReturn(Collections.singletonList(mock(LoadGraphService.class)));
        when(workspace.getNewickList()).thenReturn(Collections.singletonList(mock(LoadNewickService.class)));

    }

    /**
     * Test RuntimeException on wrong FXML file.
     */
    @Test(expected = RuntimeException.class)
    public void testWrongFXMLFile() {

        mainController.loadFXML("");

    }

    /**
     * Test view list.
     */
    @Test
    public void testTopViewList() {

        MainController mc = new MainController();
        mc.setWorkspace(workspace);

        BaseController baseController1 = new BaseController(graph, node1);
        BaseController baseController2 = new BaseController(graph, node2);

        mc.setView(baseController1);
        assertEquals(baseController1, mc.viewStack.peek());
        assertEquals(baseController1.getRoot(), mc.view.getChildren().get(0));

        mc.setView(baseController2);
        assertEquals(baseController2, mc.viewStack.peek());
        assertEquals(baseController2.getRoot(), mc.view.getChildren().get(1));

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

        mc.setView(baseController1);
        mc.setView(baseController2);

        mc.previousView();

        assertEquals(baseController1, mc.viewStack.peek());
        assertEquals(baseController1.getRoot(), mc.view.getChildren().get(0));

    }

    /**
     * Test empty view list.
     */
    @Test
    public void testEmptyViewList() {

        MainController mc = new MainController();
        mc.setWorkspace(workspace);


        BaseController baseController1 = new BaseController(graph, node1);
        BaseController baseController2 = new BaseController(graph, node2);

        mc.setView(baseController1);
        mc.setView(baseController2);

        mc.previousView();
        mc.previousView();

        assertEquals(baseController1, mc.viewStack.peek());
        assertEquals(baseController1.getRoot(), mc.view.getChildren().get(0));

    }

    /**
     * Test base view size.
     */
    @Test
    public void setBaseViewSize() {

        MainController mc = new MainController();
        mc.setWorkspace(workspace);

        BaseController baseController1 = new BaseController(graph, node1);
        BaseController baseController2 = new BaseController(graph, node2);

        mc.setView(baseController1);
        mc.setView(baseController2);
        mc.setBaseView(baseController1);

        assertEquals(1, mc.viewStack.size());

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

}
