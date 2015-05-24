package nl.tudelft.context.controller;

import com.sun.javafx.collections.ObservableListWrapper;
import de.saxsys.javafx.test.JfxRunner;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.scene.layout.StackPane;
import nl.tudelft.context.newick.Node;
import nl.tudelft.context.newick.Tree;
import nl.tudelft.context.newick.selection.All;
import nl.tudelft.context.workspace.Workspace;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 26-4-2015
 */
@RunWith(JfxRunner.class)
public class NewickControllerTest {

    protected static NewickController newickController;
    protected static MainController mainController;

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

        when(workspace.getNwkFile()).thenReturn(nwkFile);
        when(mainController.getWorkspace()).thenReturn(workspace);
        when(mainController.view.getChildren()).thenReturn(new ObservableListWrapper<>(new ArrayList<>()));

        newickController = new NewickController(mainController);

    }

    /**
     * Test toggle Newick.
     */
    @Test
    public void toggleNewick() {

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
     * Nothing should break when no nodes, or some nodes are selected.
     */
    @Test
    public void testLoadGraph() {
        Tree tree = new Tree();
        Node node = new Node("n1", 1.23);
        tree.setRoot(node);
        newickController.loadGraph(tree);
        node.setSelection(new All());
        newickController.loadGraph(tree);
    }

    @Test
    public void testBreadcrumbName() {
        assertEquals("Phylogenetic tree", newickController.getBreadcrumbName());
    }

}
