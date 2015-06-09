package nl.tudelft.context.service;

import de.saxsys.javafx.test.JfxRunner;
import javafx.concurrent.Worker;
import nl.tudelft.context.model.newick.Newick;
import nl.tudelft.context.model.newick.NewickParser;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 1-5-2015
 */
@RunWith(JfxRunner.class)
public class LoadNewickServiceTest {

    protected final static File nwkFile = new File(LoadNewickServiceTest.class.getResource("/newick/10strains.nwk").getPath());

    /**
     * Test if the graphFromFactory loadFXML succeeds.
     */
    @Test
    public void testNewickLoadSucceeds() throws Exception {

        final LoadService<Newick> loadNewickService = new LoadService<>(NewickParser.class, nwkFile);

        CompletableFuture<Newick> completableFuture = new CompletableFuture<>();

        loadNewickService.stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                completableFuture.complete(loadNewickService.getValue());
            }
        });

        loadNewickService.restart();

        // Wait for graphFromFactory service
        Newick newick = completableFuture.get(5000, TimeUnit.MILLISECONDS);

        assertNotNull(newick);

    }

}
