package nl.tudelft.context.controller;

import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import nl.tudelft.context.controller.overlay.OverlayController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Gerben Oolbekkink
 * @version 1.0
 * @since 22-5-2015
 */
@RunWith(JfxRunner.class)
public class OverlayControllerTest {

    protected MainController mainController = new MainController();
    protected OverlayController overlayController;

    /**
     * Setup overlay controller.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {

        overlayController = new OverlayController(mainController, new StackPane());

    }

    /**
     * Test RuntimeException on wrong FXML file.
     */
    @Test(expected = RuntimeException.class)
    public void testWrongFXMLFile() {

        overlayController.loadFXML("");

    }

    /**
     * Test toggle.
     */
    @Test
    public void testToggle() {

        MenuItem toggleOverlay = mainController.getMenuController().getToggleOverlay();
        assertFalse(overlayController.getVisibilityProperty().get());

        toggleOverlay.fire();
        assertTrue(overlayController.getVisibilityProperty().get());

        toggleOverlay.fire();
        assertFalse(overlayController.getVisibilityProperty().get());

    }

}