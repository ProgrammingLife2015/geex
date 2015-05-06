package nl.tudelft.context.graph;

import nl.tudelft.context.drawable.DrawableNode;

import java.util.Set;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.1
 * @since 23-4-2015
 */
public class Node extends DrawableNode {

    protected int id;
    protected Set<String> sources;
    protected int refStartPosition;
    protected int refEndPosition;
    protected String content;
    protected Counter counter;

    /**
     * Create a node.
     *
     * @param id               id
     * @param refStartPosition start position in reference genome
     * @param sources          genomes that contain this node
     * @param refEndPosition   end position in reference genome
     * @param content          DNA sequence
     */
    public Node(int id, Set<String> sources, int refStartPosition, int refEndPosition, String content) {

        this.id = id;
        this.sources = sources;
        this.refStartPosition = refStartPosition;
        this.refEndPosition = refEndPosition;
        this.content = content;
        this.counter = new Counter(content);

    }

    /**
     * Getter for id.
     *
     * @return id
     */
    public int getId() {

        return id;

    }

    /**
     * Getter for sources.
     *
     * @return sources
     */
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
     * Getter for content.
     *
     * @return DNA sequence
     */
    public String getContent() {

        return content;

    }


    /**
     * Getter for counter
     *
     * @return counter
     */
    public Counter getCounter() {
        return counter;
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
    public boolean equals(Object other) {

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

}
