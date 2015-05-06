package nl.tudelft.context.controller;

import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.layout.GridPane;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 26-4-2015
 */
@RunWith(JfxRunner.class)
public class MainControllerTest {

    protected static MainController mainController;

    /**
     * Setup Main Controller.
     */
    @BeforeClass
    public static void beforeClass() throws InterruptedException {

        mainController = new MainController();

    }

    /**
     * Check if controllers are added.
     */
    @Test
    public void testLeft() {

        assertThat(mainController.control.getChildren().get(0), instanceOf(GridPane.class));
        assertThat(mainController.control.getChildren().get(1), instanceOf(GridPane.class));

    }

    /**
     * Test RuntimeException on wrong FXML file.
     */
    @Test(expected = RuntimeException.class)
    public void testWrongFXMLFile() {

        mainController.loadFXML("");

    }

}
