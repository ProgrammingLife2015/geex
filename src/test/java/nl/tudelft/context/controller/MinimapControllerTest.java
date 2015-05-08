package nl.tudelft.context.controller;

import de.saxsys.javafx.test.JfxRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 08-05-2015
 */
@RunWith(JfxRunner.class)
public class MinimapControllerTest {

    protected static MinimapController minimapController;

    /**
     * Test if the controller gets initialized without any errors.
     */
    @Test
    public void testGraph() throws Exception {

        MainController mainController = mock(MainController.class);
        minimapController = new MinimapController(mainController);

    }
}