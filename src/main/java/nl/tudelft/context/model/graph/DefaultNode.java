package nl.tudelft.context.model.graph;

import nl.tudelft.context.controller.GraphController;
import nl.tudelft.context.controller.MainController;
import nl.tudelft.context.drawable.DefaultLabel;
import nl.tudelft.context.drawable.DrawableNode;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 1-6-2015
 */
public abstract class DefaultNode {

    /**
     * Getter for id.
     *
     * @return id
     */
    public abstract int getId();

    /**
     * Getter for content.
     *
     * @return DNA sequence
     */
    public abstract String getContent();

    /**
     * Creates a label.
     *
     * @param mainController  Main controller for setting views
     * @param graphController Graph controller to set view on
     * @param stackGraph      Stack graph for base controller
     * @param drawableNode    Node to draw
     * @return Info label
     */
    public abstract DefaultLabel getLabel(final MainController mainController, final GraphController graphController,
                                          final StackGraph stackGraph, final DrawableNode drawableNode);
}
