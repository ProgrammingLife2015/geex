package nl.tudelft.context.drawable.graph;

import javafx.scene.layout.VBox;
import nl.tudelft.context.model.graph.DefaultNode;
import org.apache.commons.collections.CollectionUtils;

import java.util.Set;

/**
 * @author René Vennik
 * @version 1.0
 * @since 1-6-2015
 */
public abstract class AbstractLabel extends VBox {

    /**
     * The node the label belongs to.
     */
    DefaultNode node;

    public AbstractLabel(DefaultNode node) {
        this.node = node;
    }

    protected void updateSources(Set<String> sources) {

        if (CollectionUtils.containsAny(sources, node.getSources())) {
            getStyleClass().add("selected");
        } else {
            getStyleClass().remove("selected");
        }

    }

}
