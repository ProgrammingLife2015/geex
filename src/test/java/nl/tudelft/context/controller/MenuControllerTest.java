package nl.tudelft.context.controller;

import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Window;
import nl.tudelft.context.workspace.Workspace;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

/**
 * @author Jim
 * @version 1.0
 * @since 5/29/2015
 */
@RunWith(JfxRunner.class)
public class MenuControllerTest {

    protected static MainController mainController;
    protected static Workspace workspace;

    @BeforeClass
    public static void beforeClass() throws Exception {

        mainController = mock(MainController.class);
        workspace = mock(Workspace.class);

        when(mainController.getWorkspace()).thenReturn(workspace);

    }

    @Test
    public void testFileMenu() {

        MenuBar mb = new MenuBar();
        new MenuController(mainController, mb);

        assertTrue(mb.getMenus().get(0).getText().equals("_File"));

        assertTrue(mb.getMenus().get(0).getItems().get(0).getText().equals("Select Workspace Folder"));
        assertTrue(mb.getMenus().get(0).getItems().get(1).getText().equals("Exit"));
        assertEquals(mb.getMenus().get(0).getItems().get(0).getAccelerator(), new KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_DOWN));
        assertEquals(mb.getMenus().get(0).isVisible(), true);

//        mb.getMenus().get(0).getItems().get(0).fire();
//        verify Workspace.chooseDirectory
//        mb.getMenus().get(0).getItems().get(1).fire();
//        verify that program exited

    }

    @Test
     public void testNavigateMenu() {

        MenuBar mb = new MenuBar();
        new MenuController(mainController, mb);

        assertTrue(mb.getMenus().get(1).getText().equals("_Navigate"));

        assertTrue(mb.getMenus().get(1).getItems().get(0).getText().equals("Previous"));
        assertEquals(mb.getMenus().get(1).getItems().get(0).getAccelerator(), new KeyCodeCombination(KeyCode.ESCAPE));
        assertTrue(mb.getMenus().get(1).getItems().get(1).getText().equals("Show Phylogenetic tree"));
        assertEquals(mb.getMenus().get(1).getItems().get(1).getAccelerator(), new KeyCodeCombination(KeyCode.T, KeyCombination.SHORTCUT_DOWN));
        assertEquals(mb.getMenus().get(1).isVisible(), true);

//        mb.getMenus().get(1).getItems().get(0).fire();
//        verify(mainController, times(1)).previousView();
//        mb.getMenus().get(1).getItems().get(1).fire();
//        verify(mainController, times(1)).toggleNewick();

    }

    @Test
    public void testHelpMenu() {

        MenuBar mb = new MenuBar();
        new MenuController(mainController, mb);

        assertTrue(mb.getMenus().get(2).getText().equals("_Help"));

        assertTrue(mb.getMenus().get(2).getItems().get(0).getText().equals("Shortcuts"));
        assertEquals(mb.getMenus().get(2).getItems().get(0).getAccelerator(), new KeyCodeCombination(KeyCode.F1));
        assertEquals(mb.getMenus().get(2).isVisible(), true);

//        mb.getMenus().get(2).getItems().get(0).fire();
//        verify(mainController, times(1)).toggleOverlay();

    }

    @Test
    public void testGenomeGraphLoader() {

        MenuBar mb = new MenuBar();
        MenuController mc = new MenuController(mainController, mb);

        assertEquals(mc.getLoadGenomeGraph().getText(), "Load Genome Graph");
        assertEquals(mc.getLoadGenomeGraph().getAccelerator(), new KeyCodeCombination(KeyCode.L, KeyCombination.SHORTCUT_DOWN));
        assertEquals(mc.getLoadGenomeGraph().disableProperty().getValue(), true);
    }

}
