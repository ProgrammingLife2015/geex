package nl.tudelft.context.model.newick.node;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
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
    public Set<String> getSources() {

        Set<String> sources = new HashSet<>();

        selection.getValue().addSource(sources, name);

        children.forEach(node -> sources.addAll(node.getSources()));

        return sources;
    }

    @Override
    public String getClassName() {
        return "newick-strand";
    }

    @Override
    public void translate(int minWeight, double weightScale, int yPos) {
        setTranslateX(minWeight + weight * weightScale
                + parent.orElse(new DummyNode()).translateXProperty().doubleValue());
        setTranslateY(yPos);
    }

    @Override
    public String toString() {
        return "StrandNode<" + name + "," + weight + ">";
    }
}
