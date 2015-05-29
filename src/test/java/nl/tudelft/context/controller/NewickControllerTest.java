package nl.tudelft.context.controller;

import de.saxsys.javafx.test.JfxRunner;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Group;
import nl.tudelft.context.model.newick.Newick;
import nl.tudelft.context.model.newick.Node;
import nl.tudelft.context.model.newick.selection.All;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

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

    protected static BooleanProperty bp = new SimpleBooleanProperty(false);

    /**
     * Setup Load Newick Controller.
     */
    @BeforeClass
    public static void beforeClass() throws Exception {
        mainController = mock(MainController.class);
        mainController.newickLifted = bp;

        ReadOnlyObjectProperty<Newick> newickReadOnlyObjectProperty = new SimpleObjectProperty<>();
        newickController = new NewickController(mainController, newickReadOnlyObjectProperty);
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
        Newick newick = new Newick();
        Node node = new Node("n1", 1.23);
        newick.setRoot(node);
        newickController.loadGraph(newick);
        verifyZeroInteractions(mainController);

        node.setSelection(new All());
        newickController.loadGraph(newick);
        verify(mainController).showGraph(newickController, node.getSources());
    }

    @Test
    public void testShowTree() {
        newickController.newick = new Group();

        Newick newick = new Newick();
        Node node = new Node("n1", 1.23);
        newick.setRoot(node);

        newickController.showTree(newick);
        assertEquals(1, newickController.newick.getChildren().size());
    }

    @Test
    public void testBreadcrumbName() {
        assertEquals("Phylogenetic tree", newickController.getBreadcrumbName());
    }

}
