package nl.tudelft.context.controller;

import de.saxsys.javafx.test.JfxRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 26-4-2015
 */
@RunWith(JfxRunner.class)
public class MainControllerTest {

    /**
     * Setup Main Controller.
     */
    @Test
    public void testSmoke() throws InterruptedException {

        new MainController();

        Thread.sleep(3000);

    }

}
