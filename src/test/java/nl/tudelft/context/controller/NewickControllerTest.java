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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 26-4-2015
 */
@RunWith(JfxRunner.class)
public class NewickControllerTest {

    protected final static File nodeFile = new File(NewickControllerTest.class.getResource("/graph/node.graph").getPath());
    protected final static File edgeFile = new File(NewickControllerTest.class.getResource("/graph/edge.graph").getPath());
    protected final static File nwkFile = new File(NewickControllerTest.class.getResource("/newick/10strains.nwk").getPath());

    protected static NewickController newickController;

    /**
     * Setup Load Newick Controller.
     */
    @BeforeClass
    public static void beforeClass() throws Exception {

        MainController mainController = mock(MainController.class);
        Workspace workspace = mock(Workspace.class);

        when(workspace.getGraphList()).thenReturn(Collections.singletonList(new LoadGraphService(nodeFile, edgeFile)));
        when(workspace.getNewickList()).thenReturn(Collections.singletonList(new LoadNewickService(nwkFile)));
        when(mainController.getWorkspace()).thenReturn(workspace);

        newickController = new NewickController(mainController);

    }

    /**
     * Test RuntimeException on wrong FXML file.
     */
    @Test(expected = RuntimeException.class)
    public void testWrongFXMLFile() {

        newickController.loadFXML("");

    }

}
