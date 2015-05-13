package nl.tudelft.context.drawable;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableDoubleValue;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 1-5-2015
 */
public class DrawableNode {

    /**
     * The current number of incoming nodes.
     */
    int currentIncoming = 0;

    /**
     * Translation in the direction of the X axis.
     */
    SimpleDoubleProperty translateX = new SimpleDoubleProperty(0);

    /**
     * Translation in the direction of the Y axis.
     */
    SimpleDoubleProperty translateY = new SimpleDoubleProperty(0);

    /**
     * Increment current incoming and return.
     *
     * @return current incoming
     */
    public final int incrementIncoming() {

        return ++currentIncoming;

    }

    /**
     * @return translateX property
     */
    public final ObservableDoubleValue translateXProperty() {

        return translateX;

    }

    /**
     * @return translateY property
     */
    public final ObservableDoubleValue translateYProperty() {

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
