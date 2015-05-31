package nl.tudelft.context.controller;


import de.saxsys.javafx.test.JfxRunner;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.MenuItem;
import nl.tudelft.context.model.newick.Newick;
import nl.tudelft.context.model.newick.Node;
import nl.tudelft.context.model.newick.selection.All;
import nl.tudelft.context.workspace.Workspace;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.*;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @author Gerben Oolbekkink <g.j.w.oolbekkink@gmail.com>
 * @version 1.0
 * @since 26-4-2015
 */
@RunWith(JfxRunner.class)
public class NewickControllerTest {

    protected static NewickController newickController;
    protected static MainController mainController;
    protected static MenuItem menuItem;

    protected static SimpleObjectProperty<Newick> newickSimpleObjectProperty = new SimpleObjectProperty<>();
    protected static BooleanProperty bp = new SimpleBooleanProperty(false);

    /**
     * Setup Load Newick Controller.
     */
    @BeforeClass
    public static void beforeClass() throws Exception {
        mainController = mock(MainController.class);
        mainController.newickLifted = bp;

        Workspace workspace = mock(Workspace.class);

        when(workspace.getGraph()).thenReturn(new SimpleObjectProperty<>());
        when(workspace.getAnnotation()).thenReturn(new SimpleObjectProperty<>());

        when(mainController.getWorkspace()).thenReturn(workspace);

        Newick newick = new Newick();
        newick.setRoot(new Node("n1", 1.23));
        newickSimpleObjectProperty.set(newick);

        menuItem = spy(new MenuItem());

        newickController = new NewickController(mainController, menuItem, newickSimpleObjectProperty);

    }

    /**
     * MainController should receive a message that the tree is loaded.
     */
    @Test
    public void testMessageTreeLoaded() {
        Newick newick = new Newick();
        newick.setRoot(new Node("a", 1));
        newickController.showTree(newick);
        verify(mainController, atLeast(1)).displayMessage(MessageController.SUCCESS_LOAD_TREE);
    }

    /**
     * Test toggle Newick.
     */
    @Test
    public void testToggleNewick() {

        bp.setValue(true);
        bp.setValue(false);

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
        Node node = new Node("n1", 1.23);
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
        newickController.activate();
        assertTrue(newickController.active);
        newickController.deactivate();
        newickController.activate();
    }

    /**
     * Nothing should break when deactivate is called.
     */
    @Test
    public void testDeactivate() {
        newickController.deactivate();
        assertFalse(newickController.active);
    }

}
