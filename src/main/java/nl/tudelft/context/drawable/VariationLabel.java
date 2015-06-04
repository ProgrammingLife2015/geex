package nl.tudelft.context.drawable;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Paint;
import nl.tudelft.context.controller.DefaultGraphController;
import nl.tudelft.context.controller.MainController;
import nl.tudelft.context.model.graph.Node;

/**
 * @author Jim
 * @since 6/4/2015
 */
public class VariationLabel extends InfoLabel {

    /**
     * Constructor for the InfoLabel.
     *
     * @param mainController  MainController indicating the controller
     * @param graphController GraphController to place the next view on
     * @param drawableNode    Node indicating drawable
     * @param node            Node indicating the node
     */
    public VariationLabel(MainController mainController, DefaultGraphController graphController, DrawableNode drawableNode, Node node) {
        super(mainController, graphController, drawableNode, node);

        setBackground(new Background(new BackgroundFill(Paint.valueOf("Blue"), null, null)));
    }
}
