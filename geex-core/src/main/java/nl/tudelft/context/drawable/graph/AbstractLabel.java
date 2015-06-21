package nl.tudelft.context.drawable.graph;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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
     * Current active sources.
     */
    Set<String> sources;

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

        this.sources = sources;

        if (CollectionUtils.containsAny(sources, node.getSources())) {
            getStyleClass().add("selected-label");
        } else {
            getStyleClass().clear();
        }

    }

    protected void initAnnotations() {

        final HBox annotationsHolder = new HBox();

        int resistancesAmount = node.getResistances().size();
        int codingSequencesAmount = node.getCodingSequences().size();

        if (resistancesAmount > 0) {
            final Label resistancesLabel = new Label(Integer.toString(resistancesAmount));
            resistancesLabel.getStyleClass().add("resistance-label");
            annotationsHolder.getChildren().add(resistancesLabel);
        }

        if (codingSequencesAmount > 0) {
            final Label codingSequencesLabel = new Label(Integer.toString(codingSequencesAmount));
            codingSequencesLabel.getStyleClass().add("coding-sequences-label");
            annotationsHolder.getChildren().add(codingSequencesLabel);
        }

        getChildren().add(annotationsHolder);

    }

}
