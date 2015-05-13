package nl.tudelft.context.controller;

import de.saxsys.javafx.test.JfxRunner;
import nl.tudelft.context.service.LoadGraphService;
import nl.tudelft.context.service.LoadNewickService;
import nl.tudelft.context.workspace.Workspace;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.Collections;

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

    protected final static File nodeFile = new File(MainControllerTest.class.getResource("/graph/node.graph").getPath());
    protected final static File edgeFile = new File(MainControllerTest.class.getResource("/graph/edge.graph").getPath());
    protected final static File nwkFile = new File(MainControllerTest.class.getResource("/newick/10strains.nwk").getPath());

    protected static MainController mainController;
    protected static Workspace workspace;

    /**
     * Setup Main Controller.
     */
    @BeforeClass
    public static void beforeClass() throws InterruptedException {

        mainController = new MainController();

        workspace = mock(Workspace.class);

        when(workspace.getGraphList()).thenReturn(Collections.singletonList(new LoadGraphService(nodeFile, edgeFile)));
        when(workspace.getNewickList()).thenReturn(Collections.singletonList(new LoadNewickService(nwkFile)));

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


        BaseController baseController = new BaseController("ATCG");
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


        BaseController baseController = new BaseController("ATCG");
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


        BaseController baseController = new BaseController("ATCG");
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

        BaseController baseController = new BaseController("ATCG");
        NewickController newickController = new NewickController(mc);

        mc.setView(baseController.getRoot());
        mc.setView(newickController.getRoot());
        mc.setBaseView(baseController.getRoot());

        assertEquals(1, mc.viewList.size());

    }

}
