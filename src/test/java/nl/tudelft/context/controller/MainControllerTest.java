package nl.tudelft.context.controller;

import de.saxsys.javafx.test.JfxRunner;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 26-4-2015
 */
@RunWith(JfxRunner.class)
public class MainControllerTest {

    protected final static String nodeFile = "/graph/node.graph";
    protected final static String edgeFile = "/graph/edge.graph";

    protected static final int rulerPoints = 3;
    protected static final int sequencesAmount = 4;

    /**
     * Setup Main Controller.
     */
    @Test(timeout=5000)
    public void test() throws Exception {

        MainController mainController = new MainController(nodeFile, edgeFile);

        CompletableFuture<Boolean> rulerChildrenAdded = new CompletableFuture<>();
        mainController.ruler.getChildren().addListener((ListChangeListener<? super Node>) event -> {
            if (mainController.ruler.getChildren().size() == rulerPoints) {
                rulerChildrenAdded.complete(true);
            }
        });

        CompletableFuture<Boolean> sequencesAdded = new CompletableFuture<>();
        mainController.sequences.getChildren().addListener((ListChangeListener<? super Node>) event -> {
            if (mainController.sequences.getChildren().size() == sequencesAmount) {
                sequencesAdded.complete(true);
            }
        });

        mainController.progressIndicator = mock(ProgressIndicator.class);
        mainController.sequences = mock(GridPane.class);

        // Verify ruler
        assertEquals(true, rulerChildrenAdded.get(5000, TimeUnit.MILLISECONDS));

        // Verify sequences
        assertEquals(true, sequencesAdded.get(5000, TimeUnit.MILLISECONDS));

        // Verify progress indicator
        verify(mainController.progressIndicator, times(1)).visibleProperty();

    }

}
