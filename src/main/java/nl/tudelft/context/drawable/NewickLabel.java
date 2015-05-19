package nl.tudelft.context.drawable;

import javafx.scene.control.Label;
import nl.tudelft.context.controller.GraphController;
import nl.tudelft.context.controller.MainController;
import nl.tudelft.context.newick.Node;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 18-05-2015
 */
public class NewickLabel extends Label {
    /**
     * Creates a label, based on a newick node.
     *
     * @param node           The node to base the label on.
     * @param mainController The MainController indicating the controller.
     */
    public NewickLabel(Node node, MainController mainController) {
        super(node.getName());
        setCache(true);
        translateXProperty().bind(node.translateXProperty());
        translateYProperty().bind(node.translateYProperty());
        if (node.isUnknown()) {
            getStyleClass().add("ancestor");
        }
        setOnMouseClicked(event -> mainController.setView(new GraphController(mainController, node.getSources())));
    }
}
