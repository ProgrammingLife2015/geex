package nl.tudelft.context.controller;

import de.saxsys.javafx.test.JfxRunner;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import nl.tudelft.context.service.LoadGraphService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 26-4-2015
 */
@RunWith(JfxRunner.class)
public class GraphControllerTest {

    protected final static File nodeFile = new File(GraphControllerTest.class.getResource("/graph/node.graph").getPath());
    protected final static File edgeFile = new File(GraphControllerTest.class.getResource("/graph/edge.graph").getPath());

    protected static final int sequencesAmount = 4;

    protected static GraphController graphController;


    /**
     * Setup Load Graph Controller.
     */
    @BeforeClass
    public static void beforeClass() throws Exception {

        graphController = new GraphController(new LoadGraphService(nodeFile, edgeFile));

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
