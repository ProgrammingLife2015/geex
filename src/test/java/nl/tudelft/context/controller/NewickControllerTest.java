package nl.tudelft.context.controller;

import com.sun.javafx.collections.ObservableListWrapper;
import de.saxsys.javafx.test.JfxRunner;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import nl.tudelft.context.model.newick.Node;
import nl.tudelft.context.model.newick.Tree;
import nl.tudelft.context.model.newick.selection.All;
import nl.tudelft.context.workspace.Workspace;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 26-4-2015
 */
@RunWith(JfxRunner.class)
public class NewickControllerTest {

    protected static NewickController newickController;
    protected static MainController mainController;
    protected static MenuItem menuItem;

    protected final static File nodeFile = new File(GraphControllerTest.class.getResource("/graph/node.graph").getPath());
    protected final static File edgeFile = new File(GraphControllerTest.class.getResource("/graph/edge.graph").getPath());
    protected final static File nwkFile = new File(GraphControllerTest.class.getResource("/newick/10strains.nwk").getPath());
    protected static BooleanProperty bp = new SimpleBooleanProperty(false);

    /**
     * Setup Load Newick Controller.
     */
    @BeforeClass
    public static void beforeClass() throws Exception {

        mainController = mock(MainController.class);

        mainController.viewList = FXCollections.observableList(new ArrayList<>());
        mainController.view = mock(StackPane.class);
        Workspace workspace = mock(Workspace.class);
        mainController.messageController = new MessageController();
        mainController.newickLifted = bp;

        when(workspace.getEdgeFile()).thenReturn(edgeFile);
        when(workspace.getNodeFile()).thenReturn(nodeFile);
        when(workspace.getNwkFile()).thenReturn(nwkFile);
        when(mainController.getWorkspace()).thenReturn(workspace);
        when(mainController.view.getChildren()).thenReturn(new ObservableListWrapper<>(new ArrayList<>()));

        menuItem = spy(new MenuItem());

        newickController = new NewickController(mainController, menuItem);

    }

    /**
     * MainController should receive a message that the tree is loaded.
     */
    @Test
    public void testMessageTreeLoaded() {
        Tree tree = new Tree();
        tree.setRoot(new Node("a", 1));
        newickController.showTree(tree);
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
        Tree tree = new Tree();
        Node node = new Node("n1", 1.23);
        tree.setRoot(node);
        newickController.loadGraph(tree);
        verify(mainController, never()).setView(any(NewickController.class), any(GraphController.class));
        node.setSelection(new All());
        newickController.loadGraph(tree);
        verify(mainController, times(1)).setView(any(NewickController.class), any(GraphController.class));
        newickController.loadGraph(tree);
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
    public void testActivate() {
        newickController.activate();
        Tree tree = new Tree();
        tree.setRoot(new Node("n1", 1.23));
        assertTrue(newickController.active);
        newickController.tree = tree;
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
