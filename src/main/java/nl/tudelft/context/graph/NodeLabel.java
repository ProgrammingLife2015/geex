package nl.tudelft.context.graph;

import javafx.scene.shape.Rectangle;
import nl.tudelft.context.controller.GraphController;

/**
 * @author Jasper on 13-5-2015.
 * @version 1.0
 * @since 13-5-2015
 */
public class NodeLabel extends Rectangle {

    public static final int LABEL_HEIGHT = 5;

    public NodeLabel(char base, float ratio) {

        super(Math.round(ratio * GraphController.NODE_WIDTH), LABEL_HEIGHT);
        getStyleClass().add("node-label-" + base);

    }

}
