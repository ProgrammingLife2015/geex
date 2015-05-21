package nl.tudelft.context.drawable;

import javafx.scene.shape.Rectangle;

/**
 * @author Jasper Nieuwdorp
 * @version 1.0
 * @since 13-5-2015
 */
public class BaseLabel extends Rectangle {

    /**
     * Set the height of a base label.
     */
    public static final int BASE_HEIGHT = 5;

    /**
     * Constructor for the BaseLabel.
     *
     * @param base  char indicating the applicable base
     * @param width the width of the label
     */
    public BaseLabel(final char base, final double width) {

        super(width, BASE_HEIGHT);
        getStyleClass().add("node-label-" + base);

    }

}
