package nl.tudelft.context.window;

import de.saxsys.javafx.test.JfxRunner;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;

/**
 * @author René Vennik
 * @version 1.0
 * @since 18-6-2015
 */
@RunWith(JfxRunner.class)
public class WindowTest {

    private static final String TEST_TITLE = "My test title";

    Window window;
    Parent content;

    /**
     * Set up a window.
     */
    @Before
    public void setUp() {

        content = new Pane();
        Platform.runLater(() -> window = new Window(TEST_TITLE, content));

    }

    /**
     * Test if the title is correctly set.
     */
    @Test
    public void testTitle() throws InterruptedException, ExecutionException, TimeoutException {

        CompletableFuture<Boolean> wait = new CompletableFuture<>();

        Platform.runLater(() -> {
            assertEquals(TEST_TITLE, window.getTitle());
            wait.complete(true);
        });

        wait.get(5, TimeUnit.SECONDS);

    }

    /**
     * Test if window contains the right content.
     */
    @Test
    public void testContent() throws InterruptedException, ExecutionException, TimeoutException {

        CompletableFuture<Boolean> wait = new CompletableFuture<>();

        Platform.runLater(() -> {
            assertEquals(content, window.getScene().getRoot());
            wait.complete(true);
        });

        wait.get(5, TimeUnit.SECONDS);

    }

}