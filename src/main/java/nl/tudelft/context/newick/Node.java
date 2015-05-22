package nl.tudelft.context.newick;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import nl.tudelft.context.drawable.DrawableNode;
import nl.tudelft.context.newick.selection.None;
import nl.tudelft.context.newick.selection.Selection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 3-5-2015
 */
public class Node extends DrawableNode {

    /**
     * The name of the Node.
     */
    private final String name;
    /**
     * The weight of the Node.
     */
    private final double weight;
    /**
     * The children of the Node.
     */
    private List<Node> children;

    /**
     * The parent node.
     */
    private Node parent;

    /**
     * The selection of the node.
     */
    private ObjectProperty<Selection> selection = new SimpleObjectProperty<>(new None());

    /**
     * Builds a new node with the corresponding name and weight.
     *
     * @param name   the name of the node
     * @param weight the weight (distance from parent) of the node
     */
    public Node(final String name, final double weight) {
        this.name = name;
        this.weight = weight;
        children = new ArrayList<>(2);
    }

    /**
     * Adds a child to the node.
     *
     * @param n the node to add as a child
     */
    public void addChild(final Node n) {
        this.children.add(n);
    }

    /**
     * Gets all the children inside this node.
     *
     * @return the children
     */
    public List<Node> getChildren() {
        return children;
    }

    /**
     * Checks if the node has a parent.
     *
     * @return true if the node has a parent; otherwise false.
     */
    public boolean hasParent() {
        return getParent() != null;
    }

    /**
     * Sets the parent of the node.
     *
     * @param parent the parent.
     */
    public void setParent(final Node parent) {
        this.parent = parent;
    }

    /**
     * Gets the parent of the node.
     *
     * @return the parent of the node.
     */
    public Node getParent() {
        return parent;
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
     * Tells whether this node is an unknown ancestor or not.
     *
     * @return true if the node is an unknown ancestor; otherwise false
     */
    public boolean isUnknown() {
        return this.name.isEmpty();
    }

    /**
     * Gets the sources for the graph from this node and its children.
     *
     * @return name of this node and it's children
     */
    public Set<String> getSources() {

        Set<String> sources = new HashSet<>();

        if (!isUnknown() && selection.get().useSources()) {
            sources.add(name);
        }

        children.forEach(node -> sources.addAll(node.getSources()));

        return sources;

    }

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
                .map(Node::getSelection)
                .reduce(Selection::merge).orElse(new None()));

        if (hasParent()) {
            getParent().updateSelected();
        }
    }

    @Override
    public boolean equals(final Object other) {
        if (other == null || !(other instanceof Node)) {
            return false;
        }
        Node that = (Node) other;
        return name.equals(that.name)
                && weight == that.weight;
    }

    @Override
    public int hashCode() {
        long c = Double.doubleToLongBits(weight);
        return 37 * name.hashCode() + (int) (c ^ (c >>> 32));
    }

    @Override
    public String toString() {
        return "Node<" + name + "," + weight + ">";
    }
}
