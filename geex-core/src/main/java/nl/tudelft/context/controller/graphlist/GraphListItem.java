package nl.tudelft.context.controller.graphlist;

import javafx.beans.property.ObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import nl.tudelft.context.model.graph.StackGraph;

/**
 * @author René Vennik
 * @version 1.0
 * @since 17-6-2015
 */
public class GraphListItem extends Pane {

    /**
     * @param graph               Graph it represents
     * @param activeGraphProperty Active graph property to set the active graph
     */
    public GraphListItem(final StackGraph graph, final ObjectProperty<StackGraph> activeGraphProperty) {

        getStyleClass().add("graph-item");
        setOnMouseClicked(event -> activeGraphProperty.set(graph));
        getChildren().add(new Label(graph.getName()));

    }

    /**
     * Make this item active.
     */
    public void setActive() {
        getStyleClass().add("active");
    }

}
