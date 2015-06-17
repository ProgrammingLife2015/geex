package nl.tudelft.context.model.newick.node;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Jasper Boot
 * @version 1.0
 * @since 05-06-2015
 */
public class StrandNode extends AbstractNode {
    /**
     * Builds a new node with the corresponding name and weight.
     *
     * @param name   the name of the node
     * @param weight the weight (distance from parent) of the node
     */
    public StrandNode(final String name, final double weight) {
        super(name, weight);
    }

    @Override
    public void addChild(final AbstractNode n) {
        // do nothing
    }

    @Override
    public List<AbstractNode> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public void updateSources() {

        Set<String> sources = new HashSet<>();
        selection.getValue().addSource(sources, name);
        this.sources.set(sources);

    }

    @Override
    public StrandNode getCopy() {
        return new StrandNode(getName(), getWeight());
    }

    @Override
    public String getClassName() {
        return "newick-strand";
    }

    @Override
    public String toString() {
        return "StrandNode<" + name + "," + weight + ">";
    }
}
