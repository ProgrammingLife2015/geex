package nl.tudelft.context.model.newick.node;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Jasper Boot
 * @version 1.0
 * @since 05-06-2015
 */
public class AncestorNode extends AbstractNode {
    /**
     * Builds a new node with the corresponding name and weight.
     *
     * @param weight the weight (distance from parent) of the node
     */
    public AncestorNode(final double weight) {
        super("", weight);
    }

    @Override
    public void addChild(final AbstractNode n) {
        this.children.add(n);
    }

    @Override
    public List<AbstractNode> getChildren() {
        return children;
    }

    @Override
    public void updateSources() {

        Set<String> sources = new HashSet<>();
        children.forEach(node -> sources.addAll(node.getSources()));
        this.sources.set(sources);

    }

    @Override
    public AncestorNode getCopy() {
        return new AncestorNode(getWeight());
    }

    @Override
    public AbstractNode getSelectedNodes() {
        AbstractNode node = getCopy();
        List<AbstractNode> list = getChildren().stream()
                .filter(n -> n.getSelection().isAny())
                .map(AbstractNode::getSelectedNodes)
                .collect(Collectors.toList());
        if (list.size() == 1) {
            return list.get(0);
        } else {
            list.stream().forEach(opt -> {
                node.addChild(opt);
                opt.setParent(node);
            });
            return node;
        }
    }

    @Override
    public String getClassName() {
        return "newick-ancestor";
    }

    @Override
    public String toString() {
        return "AncestorNode<" + weight + ">";
    }
}
