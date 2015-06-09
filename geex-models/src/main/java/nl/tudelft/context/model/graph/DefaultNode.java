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
     * Getter for reference start position.
     *
     * @return reference start position
     */
    public abstract int getRefStartPosition();

    /**
     * Getter for reference end position.
     *
     * @return reference end position
     */
    public abstract int getRefEndPosition();

}
