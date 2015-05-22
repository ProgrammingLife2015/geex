package nl.tudelft.context.drawable;

import javafx.scene.control.Label;
import nl.tudelft.context.newick.Node;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 18-05-2015
 */
public class NewickLabel extends Label {
    /**
     * The node that the label is based on.
     */
    Node node;

    /**
     * Creates a label, based on a newick node.
     *
     * @param node           The node to base the label on.
     */
    public NewickLabel(Node node) {
        super(node.getName());

        this.node = node;

        initialize();
        setEvents();
    }

    /**
     * Initializes the label at a given position and binds a listener to the node.
     */
    public void initialize() {
        setCache(true);
        translateXProperty().bind(node.translateXProperty());
        translateYProperty().bind(node.translateYProperty());

        node.selection.addListener((observable, oldValue, newValue) -> setSelectedClass(oldValue, newValue));

        if (node.isUnknown()) {
            getStyleClass().add("ancestor");
        }
    }

    /**
     * Sets the click events on the label.
     */
    public void setEvents() {
        setOnMouseClicked(event -> {
            node.toggleSelected();
            if (node.hasParent()) {
                node.getParent().updateSelected();
            }
        });
    }

    /**
     * Sets the proper style classes according to the node selection.
     *
     * @param oldValue The old selection value.
     * @param newValue The new selection value.
     */
    public void setSelectedClass(Node.Selection oldValue, Node.Selection newValue) {
        if (!oldValue.equals(newValue)) {
            switch (newValue) {
                case NONE:
                    getStyleClass().remove("selected");
                    getStyleClass().remove("partial");
                    break;
                case PARTIAL:
                    getStyleClass().remove("selected");
                    getStyleClass().add("partial");
                    break;
                case ALL:
                    getStyleClass().add("selected");
                    getStyleClass().remove("partial");
                    break;
                default:
                    break;
            }
        }
    }
}
