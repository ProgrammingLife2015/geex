package nl.tudelft.context.service;

import de.saxsys.javafx.test.JfxRunner;
import javafx.concurrent.Worker;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

/**
 * @author René Vennik
 * @version 1.0
 * @since 26-4-2015
 */
@RunWith(JfxRunner.class)
public class LoadGraphServiceTest {

    protected final static File nodeFile = new File(LoadGraphServiceTest.class.getResource("/graph/node.graph").getPath());
    protected final static File edgeFile = new File(LoadGraphServiceTest.class.getResource("/graph/edge.graph").getPath());


    public static class MyParser implements Loadable<Boolean> {
        public MyParser() {
        }

        @Override
        public Boolean load() {
            return true;
        }

        @Override
        public Loadable<Boolean> setFiles(File... files) throws FileNotFoundException, UnsupportedEncodingException {
            return this;
        }

        @Override
        public void cancel() {

        }
    }

    public static class InfiniteParser implements Loadable<Boolean> {
        private boolean cancelled = false;

        public InfiniteParser() {
        }

        /**
         * This function is infinite, unless it is canceled.
         *
         * @return true
         */
        @Override
        public Boolean load() {
            while (!cancelled) ;

            return true;
        }

        @Override
        public Loadable<Boolean> setFiles(File... files) throws FileNotFoundException, UnsupportedEncodingException {
            return this;
        }

        @Override
        public void cancel() {
            this.cancelled = true;
        }
    }

    @Test
    public void testParse() throws Exception {
        final LoadService<Boolean> loadGraphService = new LoadService<>(MyParser.class, nodeFile, edgeFile);

        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();

        loadGraphService.stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                completableFuture.complete(true);
            }
        });

        loadGraphService.start();

        // Wait for graphFromFactory service
        Boolean b = completableFuture.get(5000, TimeUnit.MILLISECONDS);

        assertTrue(b);

    }

    @Test
    public void testInfiniteParser() throws Exception {
        final LoadService<Boolean> loadGraphService = new LoadService<>(InfiniteParser.class, nodeFile, edgeFile);

        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();

        // Stop the service when it starts running.
        loadGraphService.setOnRunning(event -> loadGraphService.cancel());

        loadGraphService.stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.CANCELLED) {
                completableFuture.complete(true);
            }
        });

        loadGraphService.start();

        // Wait for graphFromFactory service
        Boolean b = completableFuture.get(5000, TimeUnit.MILLISECONDS);

        assertTrue(b);
    }
}
