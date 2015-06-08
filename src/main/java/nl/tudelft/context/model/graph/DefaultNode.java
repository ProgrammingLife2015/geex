package nl.tudelft.context.model.graph;

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

    /**
     * Mark if this node represents a variation.
     */
    private boolean isVariation = false;

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
     * Function used to set if this node represents a variation.
     * @param bool The boolean that will be set.
     */
    public void setVariation(final boolean bool) {

        isVariation = bool;

    }

    /**
     * Returns if this node represents a variation.
     * @return boolean.
     */
    public boolean isVariation() {

        return isVariation;

    }

}
