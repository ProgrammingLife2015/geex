package nl.tudelft.context.controller;

import de.saxsys.javafx.test.JfxRunner;
import javafx.collections.ListChangeListener;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ProgressIndicator;
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
public class LoadGraphControllerTest {

    protected final static File nodeFile = new File(LoadGraphControllerTest.class.getResource("/graph/node.graph").getPath());
    protected final static File edgeFile = new File(LoadGraphControllerTest.class.getResource("/graph/edge.graph").getPath());
    protected final static File nwkFile = new File(LoadGraphControllerTest.class.getResource("/graph/10strains.nwk").getPath());

    protected static final int sequencesAmount = 4;

    protected static LoadGraphController loadGraphController;

    protected static final ProgressIndicator progressIndicator = new ProgressIndicator();
    protected static final Group sequence = new Group();



    /**
     * Setup Load Graph Controller.
     */
    @BeforeClass
    public static void beforeClass() throws Exception {

        loadGraphController = new LoadGraphController(progressIndicator, sequence);

        loadGraphController.loadGraphService.setNodeFile(nodeFile);
        loadGraphController.loadGraphService.setEdgeFile(edgeFile);

        loadGraphController.loadTreeService.setNwkFile(nwkFile);


    }

    /**
     * Test RuntimeException on wrong FXML file.
     */
    @Test(expected = RuntimeException.class)
    public void testWrongFXMLFile() {

        loadGraphController.loadFXML("");

    }

    /**
     * Test sequences added.
     */
    @Test
    public void testGraph() throws Exception {

        CompletableFuture<Boolean> sequencesAdded = new CompletableFuture<>();

        loadGraphController.sequences.getChildren().addListener((ListChangeListener<? super Node>) event -> {
            if (loadGraphController.sequences.getChildren().size() == sequencesAmount) {
                sequencesAdded.complete(true);
            }
        });

        loadGraphController.loadGraph();

        assertEquals(true, sequencesAdded.get(5000, TimeUnit.MILLISECONDS));

    }

    /**
     * Test tree loading will not result in failure.
     */
    @Test
    public void testTree() {

        loadGraphController.loadTree();

    }

}
