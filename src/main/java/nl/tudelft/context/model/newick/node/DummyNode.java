package nl.tudelft.context.model.newick.node;

import java.util.Set;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 05-06-2015
 */
public class DummyNode extends AbstractNode {

    /**
     * The child of the node.
     */
    AbstractNode child;

    /**
     * Builds a new dummy node.
     */
    public DummyNode() {
        super("", 0);
    }

    @Override
    public void addChild(final AbstractNode n) {
        this.child = n;
    }

    @Override
    public Set<String> getSources() {
        return child.getSources();
    }
}
