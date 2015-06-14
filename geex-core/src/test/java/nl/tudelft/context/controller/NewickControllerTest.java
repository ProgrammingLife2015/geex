package nl.tudelft.context.controller;


import de.saxsys.javafx.test.JfxRunner;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import nl.tudelft.context.model.newick.node.AbstractNode;
import nl.tudelft.context.model.newick.node.StrandNode;
import nl.tudelft.context.model.newick.Newick;
import nl.tudelft.context.model.newick.selection.All;
import nl.tudelft.context.workspace.Workspace;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.*;

/**
 * @author René Vennik
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 26-4-2015
 */
@RunWith(JfxRunner.class)
public class NewickControllerTest {

    protected static NewickController newickController;
    protected static MainController mainController;
    protected static MenuItem menuItem;

    protected static SimpleObjectProperty<Newick> newickSimpleObjectProperty = new SimpleObjectProperty<>();

    /**
     * Setup Load Newick Controller.
     */
    @BeforeClass
    public static void beforeClass() throws Exception {
        mainController = mock(MainController.class);
        when(mainController.getMenuController()).thenReturn(new MenuController(mainController, new MenuBar()));

        Workspace workspace = mock(Workspace.class);
        MessageController messageController = mock(MessageController.class);

        when(workspace.getGraph()).thenReturn(new SimpleObjectProperty<>());
        when(workspace.getAnnotation()).thenReturn(new SimpleObjectProperty<>());
        when(workspace.getNewick()).thenReturn(new SimpleObjectProperty<>());
        when(workspace.getResistance()).thenReturn(new SimpleObjectProperty<>());

        when(mainController.getWorkspace()).thenReturn(workspace);
        when(mainController.getMessageController()).thenReturn(messageController);

        Newick newick = new Newick();
        AbstractNode node = new StrandNode("n1", 1.23);
        newick.setRoot(node);
        newick.addVertex(node);
        newickSimpleObjectProperty.set(newick);

        menuItem = spy(new MenuItem());

        newickController = new NewickController(mainController, newickSimpleObjectProperty);

    }

    /**
     * MainController should receive a message that the tree is loaded.
     */
    @Test
    public void testMessageTreeLoaded() {
        Newick newick = new Newick();
        AbstractNode node = new StrandNode("a", 1);
        newick.setRoot(node);
        newick.addVertex(node);
        newickController.showTree(newick);
        verify(mainController, atLeast(1)).getMessageController();
    }

    /**
     * Test RuntimeException on wrong FXML file.
     */
    @Test(expected = RuntimeException.class)
    public void testWrongFXMLFile() {

        newickController.loadFXML("");

    }

    /**
     * View should only change to GraphController when at least one node is selected.
     */
    @Test
    public void testLoadGraph() {
        Newick newick = new Newick();
        AbstractNode node = new StrandNode("n1", 1.23);
        newick.setRoot(node);
        newickController.loadGraph(newick);
        verify(mainController, never()).showGraph(newickController, node.getSources());

        node.setSelection(new All());

        newickController.loadGraph(newick);
        verify(mainController, times(1)).setView(any(NewickController.class), any(GraphController.class));
        newickController.loadGraph(newick);
        verify(mainController, times(1)).toView(any(GraphController.class));
    }

    /**
     * Test whether the breadcrumb name is ok.
     */
    @Test
    public void testBreadcrumbName() {
        assertEquals("Phylogenetic tree", newickController.getBreadcrumbName());
    }

    /**
     * Nothing should break when activate is called.
     */
    @Test
    public void testActivate() throws InterruptedException {
        newickController.setActivated(true);
        assertTrue(newickController.activeProperty.get());
        newickController.setActivated(false);
        newickController.setActivated(true);
    }

    /**
     * Nothing should break when deactivate is called.
     */
    @Test
    public void testDeactivate() {
        newickController.setActivated(false);
        assertFalse(newickController.activeProperty.get());
    }

}
