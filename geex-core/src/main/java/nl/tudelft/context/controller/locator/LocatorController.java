package nl.tudelft.context.controller.locator;

import javafx.beans.property.ObjectProperty;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import nl.tudelft.context.drawable.graph.AbstractDrawableNode;
import nl.tudelft.context.drawable.graph.AbstractLabel;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author René Vennik
 * @version 1.0
 * @since 10-6-2015
 */
public class LocatorController {

    /**
     * The locator of the graph.
     */
    Pane locator;

    /**
     * The locator indicator of the graph.
     */
    Rectangle locatorIndicator;

    /**
     * Height of the locator.
     */
    private static final int LOCATOR_HEIGHT = 43;

    /**
     * Init the locator controller that shows the current position on the reference genome.
     *
     * @param locator               The locator pane
     * @param labelMapProperty      Currently active nodes
     * @param currentLabelsProperty Nodes currently shown
     */
    public LocatorController(final Pane locator,
                             final ObjectProperty<Map<Integer, List<AbstractDrawableNode>>> labelMapProperty,
                             final ObjectProperty<Set<AbstractLabel>> currentLabelsProperty) {

        this.locator = locator;

        initIndicator();

    }

    /**
     * Init indicator.
     */
    private void initIndicator() {

        locatorIndicator = new Rectangle();
        locatorIndicator.setHeight(LOCATOR_HEIGHT);
        locatorIndicator.setTranslateY(2);
        locator.getChildren().setAll(locatorIndicator);

    }

}
