package nl.tudelft.context.drawable;

import de.saxsys.javafx.test.JfxRunner;
import nl.tudelft.context.controller.MainController;
import nl.tudelft.context.newick.Node;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 18-05-2015
 */
@RunWith(JfxRunner.class)
public class NewickLabelTest {
    public Node node;
    public MainController mainController;

    /**
     * Set up the main controller.
     */
    @Before
    public void setup() {
        mainController = new MainController();
    }

    public NewickLabel getLabel() {
        node = new Node("Test node", 1.23);
        node.setTranslateX(2.34);
        node.setTranslateY(3.45);
        return new NewickLabel(node, mainController);
    }

    public NewickLabel getLabelUnknown() {
        node = new Node("", 2.34);
        node.setTranslateX(3.45);
        node.setTranslateY(4.56);
        return new NewickLabel(node, mainController);
    }

    /**
     * Check if the newick label set according to the given node.
     */
    @Test
    public void testName() {
        NewickLabel newickLabel = getLabel();
        assertEquals("Test node", newickLabel.getText());
    }

    @Test
    public void testLocation() {
        NewickLabel newickLabel = getLabel();
        assertEquals(node.translateXProperty().floatValue(),
                newickLabel.translateXProperty().floatValue(), 1e-6);
        assertEquals(node.translateYProperty().floatValue(),
                newickLabel.translateYProperty().floatValue(), 1e-6);
    }

    @Test
    public void testCache() {
        NewickLabel newickLabel = getLabel();
        assertTrue(newickLabel.isCache());
    }

    @Test
    public void testAncestor() {
        NewickLabel newickLabel = getLabel();
        assertEquals(1, newickLabel.getStyleClass().size());
    }

    @Test
    public void testAncestorUnknown() {
        NewickLabel newickLabel = getLabelUnknown();
        assertEquals(2, newickLabel.getStyleClass().size());
        assertEquals("ancestor", newickLabel.getStyleClass().get(1));
    }

}