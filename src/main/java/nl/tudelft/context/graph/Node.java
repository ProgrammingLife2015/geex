package nl.tudelft.context.graph;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 23-4-2015
 */
public class Node {

    protected String content;

    /**
     * Create a node.
     *
     * @param id               id
     * @param refStartPosition start position in reference genome
     * @param refEndPosition   end position in reference genome
     * @param content          DNA sequence
     */
    public Node(int id, int refStartPosition, int refEndPosition, String content) {

        this.content = content;

    }

    /**
     * Create a string representation of this node.
     *
     * @return string representation of this node
     */
    @Override
    public String toString() {

        return content;

    }

}
