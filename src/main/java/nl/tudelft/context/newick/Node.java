package nl.tudelft.context.newick;

import nl.tudelft.context.drawable.DrawableNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 3-5-2015
 */
public class Node extends DrawableNode {
    private final String name;
    private double weight;
    private List<Node> children;

    /**
     * Builds a new node with the corresponding name and weight.
     *
     * @param name   the name of the node
     * @param weight the weight (distance from parent) of the node
     */
    public Node(String name, double weight) {
        this.name = name;
        this.weight = weight;
        children = new ArrayList<>(2);
    }

    /**
     * Adds a child to the node.
     *
     * @param n the node to add as a child
     */
    public void addChild(Node n) {
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
     * @return true if the node is an unknown ancestor; otherwise false
     */
    public boolean isUnknown() {
        return this.name.equals("");
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof Node)) {
            return false;
        }
        Node that = (Node)other;
        return getName().equals(that.getName()) &&
                getWeight() == that.getWeight();
    }

    @Override
    public String toString() {
        return "Node<" + getName() + "," + getWeight() + ">";
    }
}
