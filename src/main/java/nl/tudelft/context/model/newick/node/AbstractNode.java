package nl.tudelft.context.model.newick.node;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import nl.tudelft.context.drawable.DrawablePosition;
import nl.tudelft.context.model.newick.selection.None;
import nl.tudelft.context.model.newick.selection.Selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 05-06-2015
 */
public abstract class AbstractNode extends DrawablePosition {

    /**
     * The name of the node.
     */
    final String name;
    /**
     * The weight of the node.
     */
    final double weight;
    /**
     * The children of the node.
     */
    List<AbstractNode> children;

    /**
     * The parent node.
     */
    Optional<AbstractNode> parent = Optional.empty();

    /**
     * The selection of the node.
     */
    ObjectProperty<Selection> selection = new SimpleObjectProperty<>(new None());

    /**
     * Builds a new node with the corresponding name and weight.
     *
     * @param name   the name of the node
     * @param weight the weight (distance from parent) of the node
     */
    public AbstractNode(final String name, final double weight) {
        this.name = name;
        this.weight = weight;
        children = new ArrayList<>(2);
    }

    /**
     * Adds a child to the node.
     *
     * @param n the node to add as a child
     */
    public void addChild(final AbstractNode n) {
        this.children.add(n);
    }

    /**
     * Gets all the children inside this node.
     *
     * @return the children
     */
    public List<AbstractNode> getChildren() {
        return children;
    }

    /**
     * Checks if the node has a parent.
     *
     * @return true if the node has a parent; otherwise false.
     */
    public boolean hasParent() {
        return parent.isPresent();
    }

    /**
     * Sets the parent of the node.
     *
     * @param parent the parent.
     */
    public void setParent(final AbstractNode parent) {
        this.parent = Optional.of(parent);
    }

    /**
     * Gets the parent of the node.
     *
     * @return the parent of the node.
     */
    public AbstractNode getParent() {
        return parent.get();
    }

    /**
     * Gets the name of this node.
     *
     * @return the name of this node
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the weight of this node.
     *
     * @return the weight of this node.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Gets the sources for the graph from this node and its children.
     *
     * @return name of this node and it's children
     */
    public abstract Set<String> getSources();

    /**
     * Toggles the selection of the node. If the selection was ALL, the the new selection will be NONE; otherwise the
     * new selection will be ALL.
     */
    public void toggleSelected() {
        setSelection(selection.get().toggle());
    }

    /**
     * Sets the new selection of the node. It recursively sets the selection of its children also.
     *
     * @param selection The new selection of the node and its children.
     */
    public void setSelection(final Selection selection) {
        this.selection.setValue(selection);
        getChildren().forEach(node -> node.setSelection(selection));
    }

    /**
     * Gets the selection property of the node.
     *
     * @return the selection property.
     */
    public ObjectProperty<Selection> getSelectionProperty() {
        return this.selection;
    }

    /**
     * Gets the selection of the node.
     *
     * @return the selection.
     */
    public Selection getSelection() {
        return selection.getValue();
    }

    /**
     * Sets the selection of the node, based on the selection of its children;
     * <p>
     * All the children's selection is ALL: ALL
     * All the children's selection is NONE: NONE
     * Otherwise: PARTIAL
     * <p>
     * If the node has a parent, it also calls this method on its parent.
     */
    public void updateSelected() {
        selection.setValue(getChildren().stream()
                .map(AbstractNode::getSelection)
                .reduce(Selection::merge).orElse(new None()));

        parent.ifPresent(AbstractNode::updateSelected);
    }

    /**
     * Creates a clone of the current node.
     *
     * @return A clone of the current node
     */
    public abstract AbstractNode clone();

    /**
     * Should return a copy of all nodes that are (partially) selected.
     *
     * @return A copy of the nodes that are selected
     */
    public Optional<AbstractNode> getSelectedNodes() {
        Optional<AbstractNode> optNode = Optional.empty();
        if (!getSelection().equals(new None())) {
            AbstractNode node = clone();
            getChildren().stream()
                    .map(AbstractNode::getSelectedNodes)
                    .filter(Optional::isPresent)
                    .forEach(opt -> {
                        node.addChild(opt.get());
                        opt.get().setParent(node);
                    });
            optNode = Optional.of(node);
        }
        return optNode;
    }

    /**
     * Returns the class name that belongs to the node.
     *
     * @return The class name
     */
    public abstract String getClassName();

    /**
     * Translates the position of the node, according to the given parameters.
     *
     * @param minWeight   The minimum horizontal distance to its parent
     * @param weightScale A scalar to multiply the weight with
     * @param yPos        The y-position of the node
     */
    public abstract void translate(final int minWeight, final double weightScale, final int yPos);

    @Override
    public String toString() {
        return "Node<" + name + "," + weight + ">";
    }
}
