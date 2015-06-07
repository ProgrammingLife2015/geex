package nl.tudelft.context.model.graph;

import nl.tudelft.context.controller.DefaultGraphController;
import nl.tudelft.context.controller.MainController;
import nl.tudelft.context.drawable.DefaultLabel;
import nl.tudelft.context.drawable.DrawableNode;
import nl.tudelft.context.drawable.VariationLabel;

import java.util.Set;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 1-6-2015
 */
public abstract class DefaultNode {

    /**
     * Set of genomes that contain this Node.
     */
    Set<String> sources;

    /**
     * The content of the current Node.
     */
    String content;

    boolean isVariation = false;

    /**
     * Getter for sources.
     *
     * @return sources
     */
    public Set<String> getSources() {
        return sources;
    }

    /**
     * Getter for content.
     *
     * @return DNA sequence
     */
    public String getContent() {
        return content;
    }

    /**
     * Creates a label.
     *
     * @param mainController  Main controller for setting views
     * @param graphController Graph controller to set view on
     * @param drawableNode    Node to draw
     * @return Info label
     */
    public abstract DefaultLabel getLabel(final MainController mainController,
                                          final DefaultGraphController graphController,
                                          final DrawableNode drawableNode);

    public void setVariation(final boolean bool) {

        isVariation = true;

    }

    public boolean isVariation() {

        return isVariation;

    }

    public VariationLabel getVariationLabel(final MainController mainController,
                                            final DefaultGraphController graphController,
                                            final DrawableNode drawableNode) {
        return new VariationLabel(mainController, graphController, drawableNode, this);
    }

}
