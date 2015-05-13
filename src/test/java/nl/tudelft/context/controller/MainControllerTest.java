package nl.tudelft.context.controller;

import de.saxsys.javafx.test.JfxRunner;
import nl.tudelft.context.service.LoadNewickService;
import nl.tudelft.context.service.LoadNewickServiceTest;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 26-4-2015
 */
@RunWith(JfxRunner.class)
public class MainControllerTest {

    protected final static File nwkFile = new File(LoadNewickServiceTest.class.getResource("/newick/10strains.nwk").getPath());

    protected static MainController mainController;

    /**
     * Setup Main Controller.
     */
    @BeforeClass
    public static void beforeClass() throws InterruptedException {

        mainController = new MainController();

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

        BaseController baseController = new BaseController("ATCG");
        NewickController newickController = new NewickController(mc, new LoadNewickService(nwkFile));

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

        BaseController baseController = new BaseController("ATCG");
        NewickController newickController = new NewickController(mc, new LoadNewickService(nwkFile));

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

        BaseController baseController = new BaseController("ATCG");
        NewickController newickController = new NewickController(mc, new LoadNewickService(nwkFile));

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

        BaseController baseController = new BaseController("ATCG");
        NewickController newickController = new NewickController(mc, new LoadNewickService(nwkFile));

        mc.setView(baseController.getRoot());
        mc.setView(newickController.getRoot());
        mc.setBaseView(baseController.getRoot());

        assertEquals(1, mc.viewList.size());

    }

}
