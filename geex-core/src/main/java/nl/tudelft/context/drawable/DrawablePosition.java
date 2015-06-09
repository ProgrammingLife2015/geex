package nl.tudelft.context.drawable;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * @author René Vennik
 * @version 1.0
 * @since 31-5-2015
 */
public abstract class DrawablePosition {

    /**
     * Translation in the direction of the X axis.
     */
    SimpleDoubleProperty translateX = new SimpleDoubleProperty(0);

    /**
     * Translation in the direction of the Y axis.
     */
    SimpleDoubleProperty translateY = new SimpleDoubleProperty(0);

    /**
     * @return translateX property
     */
    public final DoubleProperty translateXProperty() {
        return translateX;
    }

    /**
     * @return translateY property
     */
    public final DoubleProperty translateYProperty() {
        return translateY;
    }

    /**
     * Update translate x.
     *
     * @param x new x
     */
    public final void setTranslateX(final double x) {
        translateX.set(x);
    }

    /**
     * Update translate y.
     *
     * @param y new y
     */
    public final void setTranslateY(final double y) {
        translateY.set(y);
    }

}
