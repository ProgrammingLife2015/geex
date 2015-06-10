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

    /**
     * Create a abstract label on a node.
     *
     * @param node Node the label is based upon
     */
    public AbstractLabel(final DefaultNode node) {
        this.node = node;
    }

    /**
     * Update the sources and check if node is selected.
     *
     * @param sources New selected sources
     */
    public void updateSources(final Set<String> sources) {

        if (CollectionUtils.containsAny(sources, node.getSources())) {
            getStyleClass().add("selected-label");
        } else {
            getStyleClass().clear();
        }

    }

}
