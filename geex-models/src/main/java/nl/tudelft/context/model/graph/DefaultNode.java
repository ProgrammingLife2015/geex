package nl.tudelft.context.model.graph;

import java.util.Set;

/**
 * @author René Vennik
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
     * Boolean that tells if this node is a variation.
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

    public void setVariation(final boolean bool) {
        isVariation = bool;
    }

    public boolean getVariation() {
        return isVariation;
    }

}
