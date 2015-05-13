package nl.tudelft.context.drawable;

import javafx.scene.shape.Rectangle;

/**
 * @author Jasper on 13-5-2015.
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
     * @param base char indicating the applicable base.
     * @param ratio the ratio of the representation of base in the total.
     */
    public  BaseLabel(final char base, final float ratio) {

        super(ratio * InfoLabel.LABEL_WIDTH, BASE_HEIGHT);
        getStyleClass().add("node-label-" + base);

    }

}
