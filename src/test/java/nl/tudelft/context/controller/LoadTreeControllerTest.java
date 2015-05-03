package nl.tudelft.context.controller;

import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.Group;
import javafx.scene.control.ProgressIndicator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 3-5-2015
 */
@RunWith(JfxRunner.class)
public class LoadTreeControllerTest {

    protected final static File nwkFile = new File(LoadTreeControllerTest.class.getResource("/tree/10strains.nwk").getPath());

    protected static LoadTreeController loadTreeController;

    protected static final ProgressIndicator progressIndicator = new ProgressIndicator();
    protected static final Group sequence = new Group();

    /**
     * Setup Load Tree Controller.
     */
    @BeforeClass
    public static void beforeClass() throws Exception {

        loadTreeController = new LoadTreeController(progressIndicator, sequence);

        loadTreeController.loadTreeService.setNwkFile(nwkFile);

    }

    /**
     * Test RuntimeException on wrong FXML file.
     */
    @Test(expected = RuntimeException.class)
    public void testWrongFXMLFile() {

        loadTreeController.loadFXML("");

    }

    /**
     * Test tree loading will not result in failure.
     */
    @Test
    public void testTree() {

        loadTreeController.loadTree();

    }

}
