package nl.tudelft.context.service;

import de.saxsys.javafx.test.JfxRunner;
import javafx.concurrent.Worker;
import net.sourceforge.olduvai.treejuxtaposer.drawer.Tree;
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
public class LoadTreeServiceTest {

    protected final static File nwkFile = new File(LoadTreeServiceTest.class.getResource("/tree/10strains.nwk").getPath());

    /**
     * Test if the graphFromFactory loadFXML succeeds.
     */
    @Test
    public void testGraphLoadSucceeds() throws Exception {

        final LoadTreeService loadTreeService = new LoadTreeService();
        loadTreeService.setNwkFile(nwkFile);

        CompletableFuture<Tree> completableFuture = new CompletableFuture<>();

        loadTreeService.stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                completableFuture.complete(loadTreeService.getValue());
            }
        });

        loadTreeService.restart();

        // Wait for graphFromFactory service
        Tree tree = completableFuture.get(5000, TimeUnit.MILLISECONDS);

        assertNotNull(tree);

    }

}
