package nl.tudelft.context.service;

import de.saxsys.javafx.test.JfxRunner;
import javafx.concurrent.Worker;
import nl.tudelft.context.graph.Graph;
import nl.tudelft.context.graph.GraphMap;
import nl.tudelft.context.graph.GraphParser;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 26-4-2015
 */
@RunWith(JfxRunner.class)
public class LoadGraphServiceTest {

    protected final static File nodeFile = new File(LoadGraphServiceTest.class.getResource("/graph/node.graph").getPath());
    protected final static File edgeFile = new File(LoadGraphServiceTest.class.getResource("/graph/edge.graph").getPath());

    protected static GraphMap graphFromFactory;

    /**
     * Set up comparing graphFromFactory.
     */
    @BeforeClass
    public static void beforeClass() throws FileNotFoundException, UnsupportedEncodingException {

        GraphParser graphParser = new GraphParser();
        graphFromFactory = graphParser.getGraphMap(nodeFile, edgeFile);

    }

    /**
     * Test if the graphFromFactory loadFXML succeeds.
     */
    @Test
    public void testGraphLoadSucceeds() throws Exception {

        final LoadGraphService loadGraphService = new LoadGraphService(nodeFile, edgeFile);

        CompletableFuture<Graph> completableFuture = new CompletableFuture<>();

        loadGraphService.stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                completableFuture.complete(loadGraphService.getValue().flat(new HashSet<>(Arrays.asList("Dog", "Cat"))));
            }
        });

        loadGraphService.restart();

        // Wait for graphFromFactory service
        Graph graph = completableFuture.get(5000, TimeUnit.MILLISECONDS);

        assertNotNull(graph);

    }

}
