package nl.tudelft.context.drawable;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import nl.tudelft.context.drawable.graph.AbstractDrawableNode;


/**
 * @author Jim
 * @since 6/14/2015
 */
public class DrawableLocatorMutation extends Line {

    public DrawableLocatorMutation(final AbstractDrawableNode node, final Pane locator) {

        setStroke(Paint.valueOf("blue"));

        System.out.println("X: " + node.translateXProperty().getValue());
        System.out.println("Y: " + locator.getHeight());

        setStartX(node.translateXProperty().getValue());
        setEndX(node.translateXProperty().getValue());
        setStartY(0);
        setEndY(43);

    }

}
