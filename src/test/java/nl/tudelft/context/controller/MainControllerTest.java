package nl.tudelft.context.controller;

import de.saxsys.javafx.test.JfxRunner;
import nl.tudelft.context.graph.Graph;
import nl.tudelft.context.graph.GraphFactory;
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

    protected static MainController mainController;
    protected static Workspace workspace;

    /**
     * Setup Main Controller.
     */
    @BeforeClass
    public static void beforeClass() throws Exception {

        File nodeFile = new File(BaseControllerTest.class.getResource("/graph/node.graph").getPath());
        File edgeFile = new File(BaseControllerTest.class.getResource("/graph/edge.graph").getPath());

        GraphFactory graphFactory = new GraphFactory();
        graph = graphFactory.getGraph(nodeFile, edgeFile);

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

        BaseController baseController = new BaseController(graph, node1);
        NewickController newickController = new NewickController(mc);

        mc.setView(baseController.getRoot());
        assertEquals(baseController.getRoot(), mc.viewList.peek());
        assertEquals(baseController.getRoot(), mc.view.getChildren().get(0));

        mc.setView(newickController.getRoot());
        assertEquals(newickController.getRoot(), mc.viewList.peek());
        assertEquals(newickController.getRoot(), mc.view.getChildren().get(1));

    }

    /**
     * Test previous view.
     */
    @Test
    public void testPreviousView() {

        MainController mc = new MainController();
        mc.setWorkspace(workspace);


        BaseController baseController = new BaseController(graph, node1);
        NewickController newickController = new NewickController(mc);

        mc.setView(baseController.getRoot());
        mc.setView(newickController.getRoot());

        mc.previousView();

        assertEquals(baseController.getRoot(), mc.viewList.peek());
        assertEquals(baseController.getRoot(), mc.view.getChildren().get(0));

    }

    /**
     * Test empty view list.
     */
    @Test
    public void testEmptyViewList() {

        MainController mc = new MainController();
        mc.setWorkspace(workspace);


        BaseController baseController = new BaseController(graph, node1);
        NewickController newickController = new NewickController(mc);

        mc.setView(baseController.getRoot());
        mc.setView(newickController.getRoot());

        mc.previousView();
        mc.previousView();

        assertEquals(baseController.getRoot(), mc.viewList.peek());
        assertEquals(baseController.getRoot(), mc.view.getChildren().get(0));

    }

    /**
     * Test base view size.
     */
    @Test
    public void setBaseViewSize() {

        MainController mc = new MainController();
        mc.setWorkspace(workspace);

        BaseController baseController = new BaseController(graph, node1);
        NewickController newickController = new NewickController(mc);

        mc.setView(baseController.getRoot());
        mc.setView(newickController.getRoot());
        mc.setBaseView(baseController.getRoot());

        assertEquals(1, mc.viewList.size());

    }

}
