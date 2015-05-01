package nl.tudelft.context.drawable;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableDoubleValue;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 1-5-2015
 */
public class DrawableNode {

    protected int currentIncoming = 0;
    protected SimpleDoubleProperty translateX = new SimpleDoubleProperty(0);
    protected SimpleDoubleProperty translateY = new SimpleDoubleProperty(0);

    /**
     * Increment current incoming and return.
     *
     * @return current incoming
     */
    public int incrementIncoming() {

        return ++currentIncoming;

    }

    /**
     * @return translateX property
     */
    public ObservableDoubleValue translateXProperty() {

        return translateX;

    }

    /**
     * @return translateY property
     */
    public ObservableDoubleValue translateYProperty() {

        return translateY;

    }

    /**
     * Update translate x.
     *
     * @param x new x
     */
    public void setTranslateX(double x) {

        translateX.set(x);

    }

    /**
     * Update translate y.
     *
     * @param y new y
     */
    public void setTranslateY(double y) {

        translateY.set(y);

    }

}
