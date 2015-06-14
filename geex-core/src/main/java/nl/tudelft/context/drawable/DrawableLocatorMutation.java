package nl.tudelft.context.drawable;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import nl.tudelft.context.model.graph.DefaultNode;


/**
 * @author Jim
 * @since 6/14/2015
 */
public class DrawableLocatorMutation extends Line {

    double maxRefPosition;

    double width;
    
    public DrawableLocatorMutation(final DefaultNode node, final double width, final double maxRefPosition) {

        this.maxRefPosition = maxRefPosition;
        this.width = width;

        setStroke(Paint.valueOf("blue"));

        setStartX(getXPosition(node.getRefStartPosition()));
        setEndX(node.getRefStartPosition() / maxRefPosition * width);
        setStartY(0);
        setEndY(43);

    }

    private double getXPosition(final int pos) {
        return pos / maxRefPosition * width;
    }

}
