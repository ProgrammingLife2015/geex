package nl.tudelft.context.controller;

import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.text.Text;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * @author Jim Hommes
 * @version 1.0
 * @since 19-05-2015
 */
@RunWith(JfxRunner.class)
public class FooterControllerTest {

    /**
     * Test for displaying the text.
     */
    @Test
    public void testDisplayText() {
        FooterController fc = new FooterController();
        String text = "Display Test";

        fc.displayMessage(text);
        assertEquals(((Text) fc.hbox.getChildren().get(0)).getText(), text);
    }


}
