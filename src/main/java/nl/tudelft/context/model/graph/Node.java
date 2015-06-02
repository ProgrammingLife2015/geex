package nl.tudelft.context.model.graph;

import nl.tudelft.context.controller.GraphController;
import nl.tudelft.context.controller.MainController;
import nl.tudelft.context.drawable.DefaultLabel;
import nl.tudelft.context.drawable.DrawableNode;
import nl.tudelft.context.drawable.InfoLabel;

import java.util.Set;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.1
 * @since 23-4-2015
 */
public class Node extends DefaultNode {

    /**
     * The identifier of the current Node.
     */
    int id;
    /**
     * Start position in reference genome.
     */
    int refStartPosition;
    /**
     * End position in reference genome.
     */
    int refEndPosition;
    /**
     * The Counter for the number of ACTG.
     */
    BaseCounter baseCounter;

    /**
     * Create a node.
     *
     * @param id               id
     * @param refStartPosition start position in reference genome
     * @param sources          genomes that contain this node
     * @param refEndPosition   end position in reference genome
     * @param content          DNA sequence
     */
    public Node(final int id,
                final Set<String> sources,
                final int refStartPosition,
                final int refEndPosition,
                final String content) {

        this.id = id;
        this.sources = sources;
        this.refStartPosition = refStartPosition;
        this.refEndPosition = refEndPosition;
        this.content = content;
        this.baseCounter = new BaseCounter(content);

    }

    /**
     * Getter for id.
     *
     * @return id
     */
    public int getId() {

        return id;

    }

    @Override
    public Set<String> getSources() {

        return sources;

    }

    /**
     * Getter for reference start position.
     *
     * @return reference start position
     */
    public int getRefStartPosition() {

        return refStartPosition;

    }

    /**
     * Getter for reference end position.
     *
     * @return reference end position
     */
    public int getRefEndPosition() {

        return refEndPosition;

    }

    /**
     * Getter for baseCounter.
     *
     * @return baseCounter
     */
    public BaseCounter getBaseCounter() {
        return baseCounter;
    }

    /**
     * Checks if node is equal to an other node.
     * <p>
     * It checks only on id, because of performance issues.
     * </p>
     *
     * @return if node is equal to an other node
     */
    @Override
    public boolean equals(final Object other) {
        if (other instanceof Node) {
            Node that = (Node) other;
            return id == that.id;
        }
        return false;
    }

    /**
     * Creates an hashCode.
     *
     * @return unique hashCode by id
     */
    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public DefaultLabel getLabel(final MainController mainController, final GraphController graphController,
                                 final DrawableNode drawableNode) {
        return new InfoLabel(mainController, graphController, drawableNode, this);
    }

}
