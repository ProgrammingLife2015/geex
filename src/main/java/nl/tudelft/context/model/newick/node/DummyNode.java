package nl.tudelft.context.model.newick.node;

import java.util.Arrays;
import java.util.List;
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
    public List<AbstractNode> getChildren() {
        return Arrays.asList(child);
    }

    @Override
    public Set<String> getSources() {
        return child.getSources();
    }

    @Override
    public String getClassName() {
        return "newick-dummy";
    }

    @Override
    public void translate(final int minWeight, final double weightScale, final int yPos) {
        setTranslateX(weight * weightScale
                + parent.orElse(new DummyNode()).translateXProperty().doubleValue());
        setTranslateY(yPos);
    }

    @Override
    public String toString() {
        return "DummyNode";
    }
}
