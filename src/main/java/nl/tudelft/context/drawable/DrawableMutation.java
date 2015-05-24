package nl.tudelft.context.drawable;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import nl.tudelft.context.graph.Node;

/**
 * @author Jim
 * @since 5/24/2015
 */
public class DrawableMutation extends Line {

    public DrawableMutation(final Node node) {

        initialize(node);

    }

    private void initialize(final Node node) {

        startXProperty().set(node.translateXProperty().doubleValue() + 20);
        endXProperty().set(node.translateXProperty().doubleValue() + 40);
        startYProperty().set(node.translateYProperty().doubleValue() + 100);
        endYProperty().set(node.translateYProperty().doubleValue() - 100);

        setStroke(Color.RED);

    }

}
