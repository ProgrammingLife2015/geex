package nl.tudelft.context.newick;

import nl.tudelft.context.drawable.DrawableNode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 3-5-2015
 */
public final class Node extends DrawableNode implements Serializable {
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
     * Number used for a good hashCode value.
     */
    public static final int HASH_CONS = 37;
    /**
     * Number used for a good hashCode representation of longs.
     */
    public static final int LONG_CONS = 32;

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
    public boolean equals(final Object other) {
        if (other == null || !(other instanceof Node)) {
            return false;
        }
        Node that = (Node) other;
        return getName().equals(that.getName())
                && getWeight() == that.getWeight();
    }

    @Override
    public int hashCode() {
        int result = HASH_CONS;

        result = HASH_CONS * result + getName().hashCode();
        long c = Double.doubleToLongBits(getWeight());

        return HASH_CONS * result + (int) (c ^ (c >>> LONG_CONS));
    }

    @Override
    public String toString() {
        return "Node<" + getName() + "," + getWeight() + ">";
    }
}
