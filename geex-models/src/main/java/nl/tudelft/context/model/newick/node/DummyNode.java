package nl.tudelft.context.model.newick.node;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Jasper Boot
 * @version 1.0
 * @since 05-06-2015
 */
public class DummyNode extends AbstractNode {

    /**
     * The child of the node.
     */
    Optional<AbstractNode> child = Optional.empty();

    /**
     * Builds a new dummy node.
     */
    public DummyNode() {
        super("", 0);
    }

    @Override
    public void addChild(final AbstractNode n) {
        child = Optional.of(n);
    }

    @Override
    public List<AbstractNode> getChildren() {
        List<AbstractNode> children = new ArrayList<>();
        child.ifPresent(children::add);
        return children;
    }

    @Override
    public void updateSources() {
        sources.set(child.orElse(new DummyNode()).getSources());
    }

    @Override
    public DummyNode getCopy() {
        return new DummyNode();
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
