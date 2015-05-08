package nl.tudelft.context.controller;

import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.Group;
import javafx.scene.control.ProgressIndicator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 26-4-2015
 */
@RunWith(JfxRunner.class)
public class LoadNewickControllerTest {

    protected final static File nwkFile = new File(LoadNewickControllerTest.class.getResource("/newick/10strains.nwk").getPath());

    protected static LoadNewickController loadNewickController;

    protected static final ProgressIndicator progressIndicator = new ProgressIndicator();
    protected static Group sequences;

    /**
     * Setup Load Newick Controller.
     */
    @BeforeClass
    public static void beforeClass() throws Exception {

        loadNewickController = new LoadNewickController(progressIndicator, sequences);

        loadNewickController.loadNewickService.setNwkFile(nwkFile);

    }

    /**
     * Test RuntimeException on wrong FXML file.
     */
    @Test(expected = RuntimeException.class)
    public void testWrongFXMLFile() {

        loadNewickController.loadFXML("");

    }

    /**
     * Test tree loading will not result in failure.
     */
    @Test
    public void testTree() {

        loadNewickController.loadTree();

    }

}
