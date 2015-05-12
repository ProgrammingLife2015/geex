package nl.tudelft.context.newick;

import nl.tudelft.context.drawable.DrawableNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 3-5-2015
 */
public final class Node extends DrawableNode {
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
     * Create a new Node.
     *
     * @param name The name of the Node
     * @param weight The weight of the Node
     */
    public Node(final String name, final double weight) {
        this.name = name;
        this.weight = weight;
        children = new ArrayList<>(2);
    }

    /**
     * Add a new child to the Node.
     * @param n The new child
     */
    public void addChild(final Node n) {
        this.children.add(n);
    }

    /**
     * Get the children of the Node.
     * @return Children of the Node
     */
    public List<Node> getChildren() {
        return children;
    }

    /**
     * Get the name of the Node.
     * @return Name of the Node
     */
    public String getName() {
        return name;
    }

    /**
     * Get the weight of the Node.
     * @return Weight of the Node
     */
    public double getWeight() {
        return weight;
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
        int result = 7;

        result = 37 * result + getName().hashCode();
        long c = Double.doubleToLongBits(getWeight());

        return 37 * result + (int) (c ^ (c >>> 32));
    }

    @Override
    public String toString() {
        return "Node<" + getName() + "," + getWeight() + ">";
    }
}
