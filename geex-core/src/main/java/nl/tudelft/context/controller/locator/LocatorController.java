package nl.tudelft.context.controller.locator;

import javafx.beans.property.ObjectProperty;
import nl.tudelft.context.drawable.graph.AbstractDrawableNode;
import nl.tudelft.context.drawable.graph.AbstractLabel;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 10-6-2015
 */
public class LocatorController {

    /**
     * Init the locator controller that shows the current position on the reference genome.
     *
     * @param labelMapProperty      Currently active nodes
     * @param currentLabelsProperty Nodes currently shown
     */
    public LocatorController(final ObjectProperty<Map<Integer, List<AbstractDrawableNode>>> labelMapProperty,
                             final ObjectProperty<Set<AbstractLabel>> currentLabelsProperty) {



    }

}
