package nl.tudelft.context.drawable;

import nl.tudelft.context.model.newick.Newick;
import nl.tudelft.context.model.newick.node.AbstractNode;
import nl.tudelft.context.model.newick.node.DummyNode;

/**
 * @author Jasper Boot
 * @version 1.0
 * @since 05-06-2015
 */
public class DrawableNewick extends Newick {

    /**
     * The Newick tree that is drawn.
     */
    final Newick newick;
    /**
     * The indentation for the first ancestor.
     */
    public static final int START_INDENT = 25;
    /**
     * The vertical distances between nodes.
     */
    public static final int ROW_HEIGHT = 25;
    /**
     * The weights are scaled with this factor to make relative weights noticeable.
     */
    public static final double WEIGHT_SCALE = 5e4;
    /**
     * Child nodes are at least MIN_WEIGHT pixels moved from their parent.
     */
    public static final int MIN_WEIGHT = 30;
    /**
     * The current row of the tree, for drawing purposes.
     */
    int currentRow = 0;

    /**
     * Create a wrapper around the Newick to draw the Newick tree.
     *
     * @param newick The Newick tree to draw
     */
    public DrawableNewick(final Newick newick) {
        this.newick = newick;

        newick.vertexSet().stream()
                .forEach(this::addVertex);

        DummyNode dummy = new DummyNode();
        addVertex(dummy);
        addEdge(dummy, newick.getRoot());
        newick.getRoot().setTranslateX(START_INDENT);

        newick.getRoot().getChildren()
                .forEach(this::constructTree);
    }

    /**
     * Create a visual tree structure for the node and its children.
     *
     * @param node The node
     */
    public void constructTree(final AbstractNode node) {
        addEdge(node.getParent(), node);
        translateNode(node);
        if (node.getChildren().isEmpty()) {
            currentRow++;
        }
        node.getChildren()
                .forEach(this::constructTree);
    }

    /**
     * Translates the positions of the node and its children.
     *
     * @param node The root node to start with
     */
    public void translateNode(final AbstractNode node) {
        node.translate(MIN_WEIGHT, WEIGHT_SCALE, currentRow * ROW_HEIGHT);
    }

    /**
     * Gets the Newick tree.
     *
     * @return The Newick tree
     */
    public Newick getNewick() {
        return newick;
    }
}
