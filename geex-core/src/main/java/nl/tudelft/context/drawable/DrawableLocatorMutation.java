package nl.tudelft.context.drawable;

import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import nl.tudelft.context.controller.locator.LocatorController;
import nl.tudelft.context.drawable.graph.AbstractDrawableNode;
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

        System.out.println("X: " + getXPosition(node.getRefEndPosition()));
        System.out.println(width);
        System.out.println("maxRef: " + maxRefPosition);

        setStartX(getXPosition(node.getRefStartPosition()));
        setEndX(node.getRefStartPosition() / maxRefPosition * width);
        setStartY(0);
        setEndY(43);

    }

    private double getXPosition(final int pos) {
        return pos / maxRefPosition * width;
    }

}
