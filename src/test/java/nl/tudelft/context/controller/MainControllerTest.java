package nl.tudelft.context.controller;

import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.layout.GridPane;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 26-4-2015
 */
@RunWith(JfxRunner.class)
public class MainControllerTest {

    protected static MainController mainController;

    /**
     * Setup Main Controller.
     */
    @BeforeClass
    public static void beforeClass() throws InterruptedException {

        mainController = new MainController();

    }

    /**
     * Check if controllers are added.
     */
    @Test
    public void testLeft() {

        assertThat(mainController.control.getChildren().get(0), instanceOf(GridPane.class));
        assertThat(mainController.control.getChildren().get(1), instanceOf(GridPane.class));

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
        LoadGraphController loadGraphController = new LoadGraphController(mc);

        mc.setView(baseController.getRoot());
        assertEquals(baseController.getRoot(), mc.viewList.peek());
        assertEquals(baseController.getRoot(), mc.root.getCenter());

        mc.setView(loadGraphController.getRoot());
        assertEquals(loadGraphController.getRoot(), mc.viewList.peek());
        assertEquals(loadGraphController.getRoot(), mc.root.getCenter());

    }

    /**
     * Test previous view.
     */
    @Test
    public void testPreviousView() {

        MainController mc = new MainController();

        BaseController baseController = new BaseController("ATCG");
        LoadGraphController loadGraphController = new LoadGraphController(mc);

        mc.setView(baseController.getRoot());
        mc.setView(loadGraphController.getRoot());

        mc.previousView();

        assertEquals(baseController.getRoot(), mc.viewList.peek());
        assertEquals(baseController.getRoot(), mc.root.getCenter());

    }

    /**
     * Test empty view list.
     */
    @Test
    public void testEmptyViewList() {

        MainController mc = new MainController();

        BaseController baseController = new BaseController("ATCG");
        LoadGraphController loadGraphController = new LoadGraphController(mc);

        mc.setView(baseController.getRoot());
        mc.setView(loadGraphController.getRoot());

        mc.previousView();
        mc.previousView();

        assertEquals(baseController.getRoot(), mc.viewList.peek());
        assertEquals(baseController.getRoot(), mc.root.getCenter());

    }

    /**
     * Test base view size.
     */
    @Test
    public void setBaseViewSize() {

        MainController mc = new MainController();

        BaseController baseController = new BaseController("ATCG");
        LoadGraphController loadGraphController = new LoadGraphController(mc);

        mc.setView(baseController.getRoot());
        mc.setView(loadGraphController.getRoot());
        mc.setBaseView(baseController.getRoot());

        assertEquals(1, mc.viewList.size());

    }

    /**
     * Test that cycling succeeds when empty and when not empty
     */
    @Test
    public void testCycleBaseViews() {
        mainController.cycleViews();
        mainController.setBaseView(new BaseController("ATCG").getRoot());
        mainController.cycleViews();
    }

}
