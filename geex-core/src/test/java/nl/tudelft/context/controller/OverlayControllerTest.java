package nl.tudelft.context.controller;

import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.layout.StackPane;
import nl.tudelft.context.controller.overlay.OverlayController;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gerben Oolbekkink <g.j.w.oolbekkink@gmail.com>
 * @version 1.0
 * @since 22-5-2015
 */
@RunWith(JfxRunner.class)
public class OverlayControllerTest {

    protected static OverlayController overlayController;

    /**
     * Setup Overlaycontrollertest
     * @throws Exception
     */
    @BeforeClass
    public static void beforeClass() throws Exception {

        overlayController = new OverlayController(new MainController(), new StackPane());

    }

    /**
     * Test RuntimeException on wrong FXML file.
     */
    @Test(expected = RuntimeException.class)
    public void testWrongFXMLFile() {

        overlayController.loadFXML("");

    }
}