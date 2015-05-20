package nl.tudelft.context.controller;

import de.saxsys.javafx.test.JfxRunner;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import nl.tudelft.context.service.LoadGraphService;
import nl.tudelft.context.service.LoadNewickService;
import nl.tudelft.context.workspace.Workspace;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 26-4-2015
 */
@RunWith(JfxRunner.class)
public class GraphControllerTest {

    protected final static File nodeFile = new File(GraphControllerTest.class.getResource("/graph/node.graph").getPath());
    protected final static File edgeFile = new File(GraphControllerTest.class.getResource("/graph/edge.graph").getPath());
    protected final static File nwkFile = new File(GraphControllerTest.class.getResource("/newick/10strains.nwk").getPath());

    protected static final int sequencesAmount = 4;

    protected static GraphController graphController;


    /**
     * Setup Load Graph Controller.
     */
    @BeforeClass
    public static void beforeClass() throws Exception {

        MainController mainController = mock(MainController.class);
        Workspace workspace = mock(Workspace.class);

        when(workspace.getEdgeFile()).thenReturn(edgeFile);
        when(workspace.getNodeFile()).thenReturn(nodeFile);
        when(workspace.getNwkFile()).thenReturn(nwkFile);
        when(mainController.getWorkspace()).thenReturn(workspace);

        graphController = new GraphController(mainController, new HashSet<>(Arrays.asList("Cat", "Dog")));

    }

    /**
     * Test RuntimeException on wrong FXML file.
     */
    @Test(expected = RuntimeException.class)
    public void testWrongFXMLFile() {

        graphController.loadFXML("");

    }

    /**
     * Test sequences added.
     */
    @Test
    public void testGraph() throws Exception {

        CompletableFuture<Boolean> sequencesAdded = new CompletableFuture<>();

        graphController.sequences.getChildren().addListener((ListChangeListener<? super Node>) event -> {
            if (graphController.sequences.getChildren().size() == sequencesAmount) {
                sequencesAdded.complete(true);
            }
        });

        assertEquals(true, sequencesAdded.get(5000, TimeUnit.MILLISECONDS));

    }

}
