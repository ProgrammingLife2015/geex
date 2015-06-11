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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author René Vennik
 * @version 1.0
 * @since 26-4-2015
 */
@RunWith(JfxRunner.class)
public class LoadGraphServiceTest {

    protected final static File nodeFile = new File(LoadGraphServiceTest.class.getResource("/graph/node.graph").getPath());
    protected final static File edgeFile = new File(LoadGraphServiceTest.class.getResource("/graph/edge.graph").getPath());


    public static class MyParser implements IParser<Boolean> {
        public MyParser() {}

        @Override
        public Boolean parse() {
            return true;
        }

        @Override
        public IParser<Boolean> setReader(File... files) throws FileNotFoundException, UnsupportedEncodingException {
            return this;
        }
    }
    /**
     * Test if the graphFromFactory loadFXML succeeds.
     */
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
}
