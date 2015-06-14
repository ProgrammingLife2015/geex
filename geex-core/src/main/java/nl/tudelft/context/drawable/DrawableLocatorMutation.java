package nl.tudelft.context.drawable;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import nl.tudelft.context.model.graph.DefaultNode;


/**
 * @author Jim
 * @since 6/14/2015
 */
public class DrawableLocatorMutation extends Line {

    /**
     * The maxRefPosition of the nodes.
     */
    double maxRefPosition;

    /**
     * The width of the locator bar.
     */
    double width;

    /**
     * Height of the locator.
     */
    private static final int LOCATOR_HEIGHT = 43;

    /**
     * A mutation that is drawn in the positionbar.
     *
     * @param node           The GraphNode that will be drawn in the positionbar.
     * @param width          The width of the locator bar.
     * @param maxRefPosition The maxRefPosition of the nodes.
     */
    public DrawableLocatorMutation(final DefaultNode node, final double width, final double maxRefPosition) {

        this.maxRefPosition = maxRefPosition;
        this.width = width;

        setStroke(Paint.valueOf("blue"));

        setStartX(getXPosition(node.getRefStartPosition()));
        setEndX(node.getRefStartPosition() / maxRefPosition * width);
        setStartY(0);
        setEndY(LOCATOR_HEIGHT);

    }

    /**
     * Calculates the X coordinate based on the scale.
     *
     * @param pos Position of the node.
     * @return The scaled x coordinate.
     */
    private double getXPosition(final int pos) {
        return pos / maxRefPosition * width;
    }

}
